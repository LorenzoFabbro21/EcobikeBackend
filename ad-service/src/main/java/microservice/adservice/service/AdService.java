package microservice.adservice.service;

import microservice.adservice.model.Ad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface AdService {

        Ad saveAd(Ad ad);

        List<Ad> getAllAds();

        Optional<Ad> getAdById(long id);

        ResponseEntity<String> deleteAd(long id);

        ResponseEntity<String> deleteAllAds();

        ResponseEntity<Ad> updateAd(long id, @RequestBody Ad ad);
}
