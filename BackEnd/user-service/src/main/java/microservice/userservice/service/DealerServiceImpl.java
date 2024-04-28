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


import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealerServiceImpl implements DealerService {

    private final DealerRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<Dealer> saveDealer(Dealer dealer) {
        if(dealer == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(dealer));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<Dealer>> getAllDealers() {
        try {
            List<Dealer> dealers = new ArrayList<>();
            repository.findAll().forEach(dealers::add);
            return ResponseEntity.status(HttpStatus.OK).body(dealers);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all dealers");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Optional<Dealer>> getDealerById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain dealer by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> deleteDealer(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            return new ResponseEntity<>("Dealer has been deleted!", HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> deleteAllDealers() {
        try {
            repository.deleteAll();
            return new ResponseEntity<>("All Dealers have been deleted!", HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Dealer> updateDealer(Long id, Dealer dealer) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
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
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Override
    public ResponseEntity<List<Appointment>> getAllAppointments(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
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
                return ResponseEntity.status(HttpStatus.OK).body(appointments);
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all appointments");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Optional<Dealer>> getDealerByMail(String mail) {
        if(mail == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findByMail(mail));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain dealer by mail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<String> verifyParams(String email, String password) {
        Optional<Dealer> user = repository.findByMail(email);
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
    }
}