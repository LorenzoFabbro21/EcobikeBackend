package microservice.userservice.service;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.model.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.dto.Booking;
import microservice.userservice.model.Private;
import microservice.userservice.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateServiceImpl implements PrivateService {

    private final PrivateRepository repository;
    private final DealerRepository repositoryDealer;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public ResponseEntity<String> savePrivate(Private userObj) {
        if(userObj == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        if (!userObj.getGoogleCheck()) {
            String response = validateRequest(userObj);
            if (!response.equals("ok"))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // check if account exists
        Optional<Private> userReturned = repository.findByMail(userObj.getMail());
        Optional<Dealer> dealerReturned = repositoryDealer.findByMail(userObj.getMail());
        if (userReturned.isPresent()) {
            if (userReturned.get().getGoogleCheck() && !userObj.getGoogleCheck()) {
                updateUser(userReturned.get(), userObj);
                return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully");
            }
            else
                return ResponseEntity.badRequest().body("Client email is already present in the database");
        }
        else if (dealerReturned.isPresent()) {
            if (userReturned.get().getGoogleCheck() && !userObj.getGoogleCheck()) {
                updateUser(userReturned.get(), userObj);
                return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully");
            }
            else
                return ResponseEntity.badRequest().body("Client email is already present in the database");
        }
        else {
            repository.save(userObj);
            return ResponseEntity.status(HttpStatus.OK).body("Client successfully created");
        }
    }


    @Override
    @Transactional
    public ResponseEntity<List<Private>> getAllPrivates() {
        try {
            List<Private> privates = new ArrayList<>();
            repository.findAll().forEach(privates::add);
            return ResponseEntity.status(HttpStatus.OK).body(privates);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain al privates");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Optional<Private>> getPrivateById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain private by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deletePrivate(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Private has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete private by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteAllPrivates() {
        try {
            repository.deleteAll();
            return new ResponseEntity<>("All Privates have been deleted!", HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Private> updatePrivate(Long id, Private userprivate) {
        if(id == null || userprivate == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Private> privatedata = repository.findById(id);

            if(privatedata.isPresent()) {
                Private Private = privatedata.get();
                Private.setName(userprivate.getName());
                Private.setLastName(userprivate.getLastName());
                Private.setMail(userprivate.getMail());
                Private.setPassword(userprivate.getPassword());
                Private.setPhoneNumber(userprivate.getPhoneNumber());
                repository.save(Private);
                return new ResponseEntity<>(repository.save(Private), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Optional<Private>> getPrivateByMail(String mail) {
        if(mail == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findByMail(mail));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain private by mail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> verifyParams(String email, String password) {
        if(email == null || password == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Private> user = repository.findByMail(email);
            if (user.isPresent()) {
                if (user.get().getGoogleCheck()) // if is a Google account getPassword will do an NPE
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This email is associated with a google account, please login with google account");
                else {
                    if (user.get().getPassword().equals(password))
                        return ResponseEntity.status(HttpStatus.OK).body("User verified");
                    else
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User password not valid");
                }
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not exists");
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to verify params");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
        if (emailValidation(userPrivate.getMail()) && userPrivate.getMail() != null && !userPrivate.getMail().isEmpty())
            return "Email non valida";
        if (userPrivate.getPassword() == null || userPrivate.getPassword().isEmpty())
            return "Password non valida";
        if (userPrivate.getName() == null || userPrivate.getName().isEmpty())
            return "First name not valid";
        if (userPrivate.getLastName() == null || userPrivate.getLastName().isEmpty())
            return "Last name not valid";
        if (userPrivate.getPhoneNumber().length() < 9)
            return "Phone number not valid";
        return "ok";
    }

    private void updateUser(Private private_user, Private userObj) {
        private_user.setPassword(userObj.getPassword());
        private_user.setPhoneNumber(userObj.getPhoneNumber());
        repository.save(private_user);
    }

    private void updateUser(Dealer dealer, Dealer userObj) {
        dealer.setPassword(userObj.getPassword());
        dealer.setPhoneNumber(userObj.getPhoneNumber());
        repositoryDealer.save(dealer);
    }


    public ResponseEntity<List<Booking>> getAllBookings(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Private> privateData = repository.findById(id);
            List<Booking> bookings;
            if (privateData.isPresent()) {
                Private _private = privateData.get();
                ResponseEntity<List<Booking>> response;

                bookings = restTemplate.exchange(
                        "http://booking-service/api/booking/user/" + _private.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Booking>>() {}
                ).getBody();
                return ResponseEntity.status(HttpStatus.OK).body(bookings);
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<Appointment>> getAllAppointments(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Private> privateData = repository.findById(id);
            List<Appointment> appointments;
            if (privateData.isPresent()) {
                Private _private = privateData.get();
                ResponseEntity<List<Appointment>> response;

                appointments = restTemplate.exchange(
                        "http://appointment-service/api/appointment/user/" + _private.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Appointment>>() {}
                ).getBody();
                return ResponseEntity.status(HttpStatus.OK).body(appointments);
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all appointment");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}