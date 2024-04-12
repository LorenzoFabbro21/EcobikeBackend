package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.dto.*;
import microservice.adservice.model.AdRent;
import microservice.adservice.model.AdSell;
import microservice.adservice.service.AdSellService;
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
        return adSellService.saveAdSell(new AdSell(adSell.getPrice(), adSell.getIdBike(), adSell.getIdUser()));
    }

    @GetMapping("/{id}")
    public Optional<AdSell> getAdSell(@PathVariable("id") long id) {
        System.out.println("Get adSell...");
        Optional<AdSell> adSell;
        adSell = adSellService.getAdSellById(id);
        return adSell;
    }

    @GetMapping("")
    public List<AdSell> getAllAdsSell() {
        System.out.println("Get all adsSell...");
        List<AdSell> adSell = new ArrayList<>();
        adSell = adSellService.getAllAdsSell();
        return adSell;
    }

    @GetMapping("/notSold")
    public List<AdSell> getAllAdsSellNotSold() {
        System.out.println("Get all adsSell...");
        List<AdSell> adSell = new ArrayList<>();
        adSell = adSellService.getAllAdsSellNotSold();
        return adSell;
    }

    @GetMapping("/all/user/{id}")
    public List<AdSell> getAllAdsSellForUser(@PathVariable("id") long id) {
        System.out.println("Get all adsSell for User...");
        List<AdSell> adSell = new ArrayList<>();
        adSell = adSellService.getAllAdsSellForUser(id);
        return adSell;
    }

    @GetMapping("/bikes")
    public List<Bike> getBikesToSell() {
        System.out.println("Get bikes to sell...");
        System.out.println("Bici="+ adSellService.getBikesToSell());
        return adSellService.getBikesToSell();

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdSell(@PathVariable("id") long id) {
        System.out.println("Delete adSell with ID = " + id + "...");
        return adSellService.deleteAdSell(id);
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAdsSell() {
        System.out.println("Delete All adsSell...");
        return adSellService.deleteAllAdsSell();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdSell> updateAdSell(@PathVariable("id") long id, @RequestBody AdSell adSell) {
        System.out.println("Update adSell with ID = " + id + "...");
        ResponseEntity<AdSell> response = adSellService.updateAdSell(id, adSell);
        return response;
    }
    @GetMapping("/user/{id}")
    public List<AdSell> getAllAdsRentByUser(@PathVariable("id") long id) {
        System.out.println("Get all Ads to rent by user...");
        return adSellService.getAllAdSellByUser(id);
    }
    @GetMapping("user/{id}/bikes")
    public List<Bike> getAllBikeToSellByUser(@PathVariable("id") long id) {
        System.out.println("Get all Bike to Sell by user...");
        return adSellService.getAllBikeToSellByUser(id);
    }

}




