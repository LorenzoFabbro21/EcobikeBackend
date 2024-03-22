package microservice.userservice.service;

import microservice.userservice.model.Dealer;
import microservice.userservice.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface DealerService {

        Dealer saveDealer(Dealer dealer);

        List<Dealer> getAllDealers();

        Optional<Dealer> getDealerById(long id);

        ResponseEntity<String> deleteDealer(long id);

        ResponseEntity<String> deleteAllDealers();

        ResponseEntity<Dealer> updateDealer( long id, @RequestBody Dealer dealer);

        Optional<Dealer> getDealerByMail(String mail);

        ResponseEntity<String> verifyParams(String email, String password);
}
