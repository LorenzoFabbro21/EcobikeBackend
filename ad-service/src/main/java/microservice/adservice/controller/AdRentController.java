package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.model.AdRent;
import microservice.adservice.service.AdRentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adrent")
@RequiredArgsConstructor
@Slf4j
public class AdRentController {

    private final AdRentService adRentService;

    @PostMapping(value = "")
    public AdRent postAdRent(@RequestBody AdRent adRent) {

        AdRent _AdRent = adRentService.saveAdRent(new AdRent(adRent.getPrice(), adRent.getIdBike()));
        return _AdRent;
    }

    @GetMapping("/{id}")
    public Optional<AdRent> getAdRent(@PathVariable("id") long id) {
        System.out.println("Get adRent...");
        Optional<AdRent> adRent;
        adRent = adRentService.getAdRentById(id);
        return adRent;
    }

    @GetMapping("")
    public List<AdRent> getAllAdsRent() {
        System.out.println("Get all adsRent...");
        List<AdRent> adRent = new ArrayList<>();
        adRent = adRentService.getAllAdsRent();
        return adRent;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdRent(@PathVariable("id") long id) {
        System.out.println("Delete adRent with ID = " + id + "...");
        ResponseEntity<String> response = adRentService.deleteAdRent(id);
        return response;
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllAdsRent() {
        System.out.println("Delete All adsRent...");

        ResponseEntity<String> response = adRentService.deleteAllAdsRent();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdRent> updateAdRent(@PathVariable("id") long id, @RequestBody AdRent adRent) {
        System.out.println("Update adRent with ID = " + id + "...");
        ResponseEntity<AdRent> response = adRentService.updateAdRent(id, adRent);
        return response;
    }
}
