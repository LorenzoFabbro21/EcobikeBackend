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

        AdSell saveAdSell(AdSell adSell);

        List<AdSell> getAllAdsSell();

        Optional<AdSell> getAdSellById(long id);

        ResponseEntity<String> deleteAdSell(long id);

        ResponseEntity<String> deleteAllAdsSell();

        ResponseEntity<AdSell> updateAdSell(long id, @RequestBody AdSell adSell);
        List<Bike> getBikesToSell();
}
