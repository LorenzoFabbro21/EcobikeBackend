package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.model.Ad;
import microservice.adservice.service.AdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ad")
@RequiredArgsConstructor
@Slf4j
public class AdController {

    private final AdService adService;

    @PostMapping(value = "")
    public Ad postAd(@RequestBody Ad ad) {

        Ad _Ad = adService.saveAd(new Ad(ad.getPrice(), ad.getIdBike()));
        return _Ad;
    }

    @GetMapping("/{id}")
    public Ad getAd(@PathVariable("id") long id) {
        System.out.println("Get ad...");
        Ad ad = new Ad();
        if (adService.getAdById(id).isPresent()) {
            return ad;
        }
        else return null;
    }

    @GetMapping("")
    public List<Ad> getAllAds() {
        System.out.println("Get all ads...");
        List<Ad> ad = new ArrayList<>();
        ad= adService.getAllAds();
        return ad;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAd(@PathVariable("id") long id) {
        System.out.println("Delete ad with ID = " + id + "...");
        ResponseEntity<String> response = adService.deleteAd(id);
        return response;
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllAds() {
        System.out.println("Delete All Ads...");

        ResponseEntity<String> response = adService.deleteAllAds();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ad> updateAd(@PathVariable("id") long id, @RequestBody Ad ad) {
        System.out.println("Update ad with ID = " + id + "...");
        ResponseEntity<Ad> response = adService.updateAd(id, ad);
        return response;
    }
}




