package microservice.adservice.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import microservice.adservice.dto.*;
import microservice.adservice.model.*;
import microservice.adservice.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdRentServiceImpl implements AdRentService {


    private final AdRentRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> saveAdRent(AdRent adRent) {
        if(adRent == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            AdRent adRentCreated = repository.save(adRent);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Rent successfully created");
            body.put("id", String.valueOf(adRentCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Override
    public ResponseEntity<List<AdRent>> getAllAdsRent() {
        try {
            List<AdRent> adsRent = new ArrayList<>();
            repository.findAll().forEach(adsRent::add);
            return ResponseEntity.status(HttpStatus.OK).body(adsRent);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Override
    public ResponseEntity<List<AdRent>> getAllAdsRentForUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<AdRent> adsRent = new ArrayList<>();
            repository.getAllAdRentForUser(id).forEach(adsRent::add);
            return ResponseEntity.status(HttpStatus.OK).body(adsRent);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ad rent for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Override
    public ResponseEntity<Optional<AdRent>> getAdRentById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<AdRent> ad = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ad);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain ad rent by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteAdRent(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "AdRent has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Override
    public ResponseEntity<?> deleteAllAdsRent() {
        try {
            repository.deleteAll();
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "All adsRent have been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all ads rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Override
    public ResponseEntity<AdRent> updateAdRent(Long id, AdRent adRent) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
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
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<Bike>> getBikesToRent() {
        try {
            ResponseEntity<List<AdRent>> adsRent = this.getAllAdsRent();
            List<AdRent> l = adsRent.getBody();
            if (!l.isEmpty()) {
                List<Bike> bikes = new ArrayList<>();
                for ( AdRent elem : l) {
                    Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + elem.getIdBike(), Bike.class);
                    bikes.add(bike);
                }
                return ResponseEntity.status(HttpStatus.OK).body(bikes);
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain bike to rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<AdRent>> getAllAdRentsByUser(Long id){
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<AdRent> adsRent = new ArrayList<>();
            repository.getAllRentByUser(id).forEach(adsRent::add);
            return ResponseEntity.status(HttpStatus.OK).body(adsRent);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain rent by user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    public ResponseEntity<List<Bike>> getBikesUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            ResponseEntity<List<AdRent>> adsRent = this.getAllAdRentsByUser(id);
            List<AdRent> l = adsRent.getBody();
            if (!l.isEmpty()) {
                List<Bike> bikes = new ArrayList<>();
                for ( AdRent elem : l) {
                    Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + elem.getIdBike(), Bike.class);
                    bikes.add(bike);
                }
                return ResponseEntity.status(HttpStatus.OK).body(bikes);
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain bike user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}