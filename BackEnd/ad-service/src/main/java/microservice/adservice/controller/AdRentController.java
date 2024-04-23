package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.dto.*;
import microservice.adservice.model.AdRent;
import microservice.adservice.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/adrent")
@RequiredArgsConstructor
@Slf4j
public class AdRentController {

    private final AdRentService adRentService;

    @PostMapping(value = "")
    public ResponseEntity<?> postAdRent(@RequestBody AdRent adRent) {
        if(adRent == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return adRentService.saveAdRent(new AdRent(adRent.getPrice(), adRent.getIdBike(), adRent.getIdUser()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AdRent>> getAdRent(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get adRent...");
            return adRentService.getAdRentById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<AdRent>> getAllAdsRent() {
        try {
            System.out.println("Get all adsRent...");
            return adRentService.getAllAdsRent();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all/user/{id}")
    public ResponseEntity<List<AdRent>> getAllAdsRentForUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all adsRent for User...");
            return adRentService.getAllAdsRentForUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all adrent for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getBikesToRent() {
        try {
            System.out.println("Get bikes to rent...");
            return adRentService.getBikesToRent();
        } catch (Exception e) {
        }
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", "Failed to obtain bike to rent");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping("/user/{id}/bikes")
    public ResponseEntity<List<Bike>> getBikesUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Bike to rent by user...");
            return adRentService.getBikesUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain bikeuser");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AdRent>> getAllAdsRentByUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Ads to rent by user...");
            return adRentService.getAllAdRentsByUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ads rent by user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdRent(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete adRent with ID = " + id + "...");
            return adRentService.deleteAdRent(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAdsRent() {
        try {
            System.out.println("Delete All adsRent...");
            return adRentService.deleteAllAdsRent();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all ads rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdRent> updateAdRent(@PathVariable("id") Long id, @RequestBody AdRent adRent) {
        if(id == null || adRent == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update adRent with ID = " + id + "...");
            return adRentService.updateAdRent(id, adRent);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}