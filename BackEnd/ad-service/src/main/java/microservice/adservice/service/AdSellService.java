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

        List<AdSell> getAllAdsSell();

        List<AdSell> getAllAdsSellForUser(long id);

        Optional<AdSell> getAdSellById(long id);

        ResponseEntity<?> deleteAdSell(long id);

        ResponseEntity<?> deleteAllAdsSell();

        ResponseEntity<AdSell> updateAdSell(long id, @RequestBody AdSell adSell);
        List<Bike> getBikesToSell();
        List<Bike> getAllBikeToSellByUser(long id);

        List<AdSell> getAllAdSellByUser(long id);
}
