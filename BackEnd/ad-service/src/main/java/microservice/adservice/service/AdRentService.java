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

    AdRent saveAdRent(AdRent adRent);

    List<AdRent> getAllAdsRent();

    Optional<AdRent> getAdRentById(long id);

    ResponseEntity<String> deleteAdRent(long id);

    ResponseEntity<String> deleteAllAdsRent();

    ResponseEntity<AdRent> updateAdRent(long id, @RequestBody AdRent adRent);
    List<Bike> getBikesToRent();
}
