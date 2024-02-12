package microservice.userservice.service;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.model.Dealer;
import microservice.userservice.model.User;
import microservice.userservice.repo.DealerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class DealerServiceImpl implements DealerService {

    private final DealerRepository repository;

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
            Dealer.setNome(dealer.getNome());
            Dealer.setCognome(dealer.getCognome());
            Dealer.setMail(dealer.getMail());
            Dealer.setPassword(dealer.getPassword());
            Dealer.setTelefono(dealer.getTelefono());
            repository.save(Dealer);
            return new ResponseEntity<>(repository.save(Dealer), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
