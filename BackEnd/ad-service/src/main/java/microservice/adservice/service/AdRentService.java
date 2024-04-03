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

    List<AdRent> getAllAdsRent();

    List<AdRent> getAllAdsRentForUser(long id);

    Optional<AdRent> getAdRentById(long id);

    ResponseEntity<?> deleteAdRent(long id);

    ResponseEntity<?> deleteAllAdsRent();

    ResponseEntity<AdRent> updateAdRent(long id, @RequestBody AdRent adRent);

    List<Bike> getBikesToRent();

    List<Bike> getBikesUser(long id);

    List<AdRent> getAllAdRentsByUser(long id);
}
