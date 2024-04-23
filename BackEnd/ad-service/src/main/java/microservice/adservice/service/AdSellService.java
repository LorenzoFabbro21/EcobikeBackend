package microservice.adservice.service;

import microservice.adservice.dto.*;
import microservice.adservice.model.AdSell;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
@Component
public interface AdSellService {

        ResponseEntity<?> saveAdSell(AdSell adSell);
        ResponseEntity<List<AdSell>> getAllAdsSell();
        ResponseEntity<List<AdSell>> getAllAdsSellNotSold();

        ResponseEntity<List<AdSell>> getAllAdsSellForUser(Long id);

        ResponseEntity<Optional<AdSell>> getAdSellById(Long id);

        ResponseEntity<?> deleteAdSell(Long id);

        ResponseEntity<?> deleteAllAdsSell();

        ResponseEntity<AdSell> updateAdSell(Long id, @RequestBody AdSell adSell);
        ResponseEntity<List<Bike>> getBikesToSell();
        ResponseEntity<List<Bike>> getAllBikeToSellByUser(Long id);

        ResponseEntity<List<AdSell>> getAllAdSellByUser(Long id);
}
