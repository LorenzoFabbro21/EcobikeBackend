package microservice.userservice.service;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.model.*;
import microservice.userservice.dto.Booking;
import microservice.userservice.model.Dealer;
import microservice.userservice.model.Private;
import microservice.userservice.repo.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealerServiceImpl implements DealerService {

    private final DealerRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Dealer saveDealer(Dealer dealer) {
        return repository.save(dealer);
    }

    @Override
    public List<Dealer> getAllDealers() {
        List<Dealer> dealers = new ArrayList<>();
        repository.findAll().forEach(dealers::add);
        return dealers;
    }

    @Override
    public Optional<Dealer> getDealerById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteDealer(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Dealer has been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAllDealers() {
       repository.deleteAll();
       return new ResponseEntity<>("All Dealers have been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Dealer> updateDealer(long id, Dealer dealer) {
        Optional<Dealer> dealerdata = repository.findById(id);

        if(dealerdata.isPresent()) {
            Dealer Dealer = dealerdata.get();
            Dealer.setName(dealer.getName());
            Dealer.setLastName(dealer.getLastName());
            Dealer.setMail(dealer.getMail());
            Dealer.setPassword(dealer.getPassword());
            Dealer.setPhoneNumber(dealer.getPhoneNumber());
            repository.save(Dealer);
            return new ResponseEntity<>(repository.save(Dealer), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Override
    public List<Appointment> getAllAppointments(long id) {
        Optional<Dealer> dealerData = repository.findById(id);
        List<Appointment> appointments;
        if (dealerData.isPresent()) {
            Dealer _dealer = dealerData.get();
            ResponseEntity<List<Appointment>> response;

            appointments = restTemplate.exchange(
                    "http://appointment-service:8086/api/appointment/user/" + _dealer.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Appointment>>() {}
            ).getBody();


            return appointments;
        }
        else {
            return null;
        }
    }

    @Override
    public Optional<Dealer> getDealerByMail(String mail) {
        return repository.findByMail(mail);
    }

    @Override
    public ResponseEntity<String> verifyParams(String email, String password) {
        Optional<Dealer> user = repository.findByMail(email);
        if (user.isPresent()) {
            if (user.get().getGoogleCheck()) { // if is a Google account getPassword will do an NPE
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This email is associated with a google account, please login with google account");
            } else {
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

}
