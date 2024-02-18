package microservice.adservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import microservice.adservice.model.AdSell;
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
public class AdSellServiceImpl implements AdSellService {

    private final AdSellRepository repository;

    @Override
    public AdSell saveAdSell(AdSell adSell) {
        return repository.save(adSell);
    }

    @Override
    public List<AdSell> getAllAdsSell() {
        List<AdSell> adsSell = new ArrayList<>();
        repository.findAll().forEach(adsSell::add);
        return adsSell;
    }

    @Override
    public Optional<AdSell> getAdSellById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteAdSell(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("AdSell has been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAllAdsSell() {
        repository.deleteAll();
        return new ResponseEntity<>("All adsSell have been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdSell> updateAdSell(long id, AdSell adSell) {
        Optional<AdSell> adSellData = repository.findById(id);

        if(adSellData.isPresent()) {
            AdSell AdSell = adSellData.get();
            AdSell.setPrice(adSell.getPrice());
            AdSell.setIdBike(adSell.getIdBike());
            repository.save(AdSell);
            return new ResponseEntity<>(repository.save(AdSell), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}