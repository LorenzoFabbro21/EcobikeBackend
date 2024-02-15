package microservice.adservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import microservice.adservice.model.Ad;
import microservice.adservice.repo.AdRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository repository;

    @Override
    public Ad saveAd(Ad ad) {
        return repository.save(ad);
    }

    @Override
    public List<Ad> getAllAds() {
        List<Ad> ads = new ArrayList<>();
        repository.findAll().forEach(ads::add);
        return ads;
    }

    @Override
    public Optional<Ad> getAdById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteAd(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Ad has been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAllAds() {
       repository.deleteAll();
       return new ResponseEntity<>("All ads have been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Ad> updateAd(long id, Ad ad) {
        Optional<Ad> adData = repository.findById(id);

        if(adData.isPresent()) {
            Ad Ad = adData.get();
            Ad.setPrice(ad.getPrice());
            Ad.setIdBike(ad.getIdBike());
            repository.save(Ad);
            return new ResponseEntity<>(repository.save(Ad), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
