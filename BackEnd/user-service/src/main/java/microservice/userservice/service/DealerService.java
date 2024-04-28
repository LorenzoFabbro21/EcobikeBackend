package microservice.userservice.service;

import microservice.userservice.dto.Appointment;
import microservice.userservice.model.Dealer;
import microservice.userservice.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface DealerService {

        ResponseEntity<Dealer> saveDealer(Dealer dealer);

        ResponseEntity<List<Dealer>> getAllDealers();

        ResponseEntity<Optional<Dealer>> getDealerById(Long id);

        ResponseEntity<String> deleteDealer(Long id);

        ResponseEntity<String> deleteAllDealers();

        ResponseEntity<Dealer> updateDealer(Long id, @RequestBody Dealer dealer);

        ResponseEntity<Optional<Dealer>> getDealerByMail(String mail);

        ResponseEntity<List<Appointment>> getAllAppointments(Long id);

        ResponseEntity<String> verifyParams(String email, String password);
}
