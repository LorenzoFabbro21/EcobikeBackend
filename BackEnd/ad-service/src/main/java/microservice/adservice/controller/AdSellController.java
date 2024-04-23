package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.dto.*;
import microservice.adservice.model.AdRent;
import microservice.adservice.model.AdSell;
import microservice.adservice.service.AdSellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/adsell")
@RequiredArgsConstructor
@Slf4j
public class AdSellController {

    private final AdSellService adSellService;

    @PostMapping(value = "")
    public ResponseEntity<?> postAdSell(@RequestBody AdSell adSell) {
        if(adSell == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return adSellService.saveAdSell(new AdSell(adSell.getPrice(), adSell.getIdBike(), adSell.getIdUser()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AdSell>> getAdSell(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get adSell...");
            return adSellService.getAdSellById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<AdSell>> getAllAdsSell() {
        try {
            System.out.println("Get all adsSell...");
            return adSellService.getAllAdsSell();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/notSold")
    public ResponseEntity<List<AdSell>> getAllAdsSellNotSold() {
        try {
            System.out.println("Get all adsSell not sold...");
            return adSellService.getAllAdsSellNotSold();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all/user/{id}")
    public ResponseEntity<List<AdSell>> getAllAdsSellForUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all adsSell for User...");
            return adSellService.getAllAdsSellForUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all adrent for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getBikesToSell() {
        try {
            System.out.println("Get bikes to sell...");
            return adSellService.getBikesToSell();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all adrent for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdSell(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete adSell with ID = " + id + "...");
            return adSellService.deleteAdSell(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAdsSell() {
        try {
            System.out.println("Delete All adsSell...");
            return adSellService.deleteAllAdsSell();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all ads rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdSell> updateAdSell(@PathVariable("id") Long id, @RequestBody AdSell adSell) {
        if(id == null || adSell == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update adSell with ID = " + id + "...");
            return adSellService.updateAdSell(id, adSell);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<AdSell>> getAllAdsRentByUser(@PathVariable("id") long id) {
        System.out.println("Get all Ads to rent by user...");
        return adSellService.getAllAdSellByUser(id);
    }
    @GetMapping("user/{id}/bikes")
    public ResponseEntity<List<Bike>> getAllBikeToSellByUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Ads to rent by user...");
            return adSellService.getAllBikeToSellByUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ads rent by user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}