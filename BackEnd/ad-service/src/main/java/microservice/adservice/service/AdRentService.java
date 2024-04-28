package microservice.adservice.service;

import microservice.adservice.dto.*;
import microservice.adservice.model.Ad;
import microservice.adservice.model.AdRent;
import microservice.adservice.model.AdSell;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
@Component
public interface AdRentService {

    ResponseEntity<?> saveAdRent(AdRent adRent);

    ResponseEntity<List<AdRent>> getAllAdsRent();

    ResponseEntity<List<AdRent>> getAllAdsRentForUser(Long id);

    ResponseEntity<Optional<AdRent>> getAdRentById(Long id);

    ResponseEntity<?> deleteAdRent(Long id);

    ResponseEntity<?> deleteAllAdsRent();

    ResponseEntity<AdRent> updateAdRent(Long id, @RequestBody AdRent adRent);

    ResponseEntity<List<Bike>> getBikesToRent();

    ResponseEntity<List<Bike>> getBikesUser(Long id);

    ResponseEntity<List<AdRent>> getAllAdRentsByUser(Long id);
}
