package microservice.adservice.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import microservice.adservice.model.AdRent;
import microservice.adservice.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdRentServiceImpl implements AdRentService {


    private final AdRentRepository repository;

    @Override
    public AdRent saveAdRent(AdRent adRent) {
        return repository.save(adRent);
    }

    @Override
    public List<AdRent> getAllAdsRent() {
        List<AdRent> adsRent = new ArrayList<>();
        repository.findAll().forEach(adsRent::add);
        return adsRent;
    }

    @Override
    public Optional<AdRent> getAdRentById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteAdRent(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("AdRent has been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAllAdsRent() {
        repository.deleteAll();
        return new ResponseEntity<>("All adsRent have been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdRent> updateAdRent(long id, AdRent adRent) {
        Optional<AdRent> adRentData = repository.findById(id);

        if(adRentData.isPresent()) {
            AdRent AdRent = adRentData.get();
            AdRent.setPrice(adRent.getPrice());
            AdRent.setIdBike(adRent.getIdBike());
            repository.save(AdRent);
            return new ResponseEntity<>(repository.save(AdRent), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
