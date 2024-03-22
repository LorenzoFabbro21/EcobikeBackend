package microservice.userservice.service;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.model.*;
import microservice.userservice.repo.PrivateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateServiceImpl implements PrivateService {

    private final PrivateRepository repository;

    @Override
    @Transactional
    public ResponseEntity<String> savePrivate(Private userObj) {
        if (!userObj.getGoogleCheck()) {
            String response = validateRequest(userObj);
            if (!response.equals("ok")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        // check if account exists
        Optional<Private> userReturned = repository.findByMail(userObj.getMail());
        if (userReturned.isPresent()) {
            if (userReturned.get().getGoogleCheck() && !userObj.getGoogleCheck()) {
                updateUser(userReturned.get(), userObj);
                return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully");
            }
            else {
                return ResponseEntity.badRequest().body("Client email is already present in the database");
            }

        } else {
            repository.save(userObj);
            return ResponseEntity.status(HttpStatus.OK).body("Client successfully created");
        }

    }


    @Override
    @Transactional
    public List<Private> getAllPrivates() {
        List<Private> privates = new ArrayList<>();
        repository.findAll().forEach(privates::add);
        return privates;
    }

    @Override
    @Transactional
    public Optional<Private> getPrivateById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deletePrivate(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Private has been deleted!", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteAllPrivates() {
       repository.deleteAll();
       return new ResponseEntity<>("All Privates have been deleted!", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Private> updatePrivate(long id, Private userprivate) {
        Optional<Private> privatedata = repository.findById(id);

        if(privatedata.isPresent()) {
            Private Private = privatedata.get();
            Private.setNome(userprivate.getNome());
            Private.setCognome(userprivate.getCognome());
            Private.setMail(userprivate.getMail());
            Private.setPassword(userprivate.getPassword());
            Private.setTelefono(userprivate.getTelefono());
            repository.save(Private);
            return new ResponseEntity<>(repository.save(Private), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public Optional<Private> getPrivateByMail(String mail) {
        return repository.findByMail(mail);
    }

    @Override
    public ResponseEntity<String> verifyParams(String email, String password) {
        Optional<Private> user = repository.findByMail(email);
        if (user.isPresent()) {
            System.out.println("VALORE1="+ user.get().getGoogleCheck());
            if (user.get().getGoogleCheck()) { // if is a Google account getPassword will do an NPE
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This email is associated with a google account, please login with google account");
            } else {
                System.out.println("VALORE2="+ user.get().getPassword());
                if (user.get().getPassword().equals(password)) {
                    return ResponseEntity.status(HttpStatus.OK).body("User verified");
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User password not valid");
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not exists");
        }
    }


    private boolean emailValidation(String emailAddress) {
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        return !Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }

    private String validateRequest(Private userPrivate) {
        // user params
        String response = validateUserParams(userPrivate);
        if (!response.equals("ok")) {
            return response;
        }

        return "ok";
    }

    private String validateUserParams(Private userPrivate) {
        System.out.println(userPrivate.getMail());
        if (emailValidation(userPrivate.getMail()) && userPrivate.getMail() != null && !userPrivate.getMail().isEmpty()) {
            return "Email non valida";
        }
        if (userPrivate.getPassword() == null || userPrivate.getPassword().isEmpty()) {
            return "Password non valida";
        }
        if (userPrivate.getNome() == null || userPrivate.getNome().isEmpty()) {
            return "First name not valid";
        }

        if (userPrivate.getCognome() == null || userPrivate.getCognome().isEmpty()) {
            return "Last name not valid";
        }
        if (userPrivate.getTelefono().length() < 9) {
            return "Phone number not valid";
        }

        return "ok";
    }

    private void updateUser(Private private_user, Private userObj) {
        private_user.setPassword(userObj.getPassword());
        private_user.setTelefono(userObj.getTelefono());
        repository.save(private_user);
    }
}
