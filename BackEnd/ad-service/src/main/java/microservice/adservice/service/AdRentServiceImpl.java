package microservice.adservice.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import microservice.adservice.dto.*;
import microservice.adservice.model.AdRent;
import microservice.adservice.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdRentServiceImpl implements AdRentService {


    private final AdRentRepository repository;

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    public List<Bike> getBikesToRent() {
        List<AdRent> adsRent = new ArrayList<>();
        adsRent = this.getAllAdsRent();
        if ( !adsRent.isEmpty())
        {
            List<Bike> bikes = new ArrayList<>();
            for ( AdRent elem : adsRent) {
                Bike bike = restTemplate.getForObject("http://bike-service/api/bike/" + elem.getIdBike(), Bike.class);
                bikes.add(bike);
            }
            return bikes;
        }
        else {
            return null;
        }
    }

    public List<AdRent> getAllAdRentsByUser(long id){
        return repository.getAllRentByUser(id);
    }
    public List<Bike> getBikesUser(long id) {

        List<AdRent> adsRent = this.getAllAdRentsByUser(id);

        if ( !adsRent.isEmpty())
        {
            List<Bike> bikes = new ArrayList<>();
            for ( AdRent elem : adsRent) {
                Bike bike = restTemplate.getForObject("http://bike-service/api/bike/" + elem.getIdBike(), Bike.class);
                bikes.add(bike);
            }
            return bikes;
        }
        else {
            return null;
        }
    }


}
