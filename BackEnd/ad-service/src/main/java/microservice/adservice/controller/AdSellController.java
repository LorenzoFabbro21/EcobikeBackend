package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.dto.*;
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
    public AdSell postAdSell(@RequestBody AdSell adSell) {

        AdSell _AdSell = adSellService.saveAdSell(new AdSell(adSell.getPrice(), adSell.getIdBike()));
        return _AdSell;
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

    @GetMapping("/bikes")
    public List<Bike> getBikesToRent() {
        System.out.println("Get bikes to sell...");
        System.out.println("Bici="+ adSellService.getBikesToSell());
        return adSellService.getBikesToSell();

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdSell(@PathVariable("id") long id) {
        System.out.println("Delete adSell with ID = " + id + "...");
        ResponseEntity<String> response = adSellService.deleteAdSell(id);
        return response;
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllAdsSell() {
        System.out.println("Delete All adsSell...");

        ResponseEntity<String> response = adSellService.deleteAllAdsSell();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdSell> updateAdSell(@PathVariable("id") long id, @RequestBody AdSell adSell) {
        System.out.println("Update adSell with ID = " + id + "...");
        ResponseEntity<AdSell> response = adSellService.updateAdSell(id, adSell);
        return response;
    }
}




