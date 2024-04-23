package microservice.adservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale created"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postAdSell(@RequestBody AdSell adSell) {
        return adSellService.saveAdSell(new AdSell(adSell.getPrice(), adSell.getIdBike(), adSell.getIdUser()));
    }

    @Operation(summary = "Get a sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "sale not found")
    })
    @GetMapping("/{id}")
    public Optional<AdSell> getAdSell(@PathVariable("id") long id) {
        System.out.println("Get adSell...");
        Optional<AdSell> adSell;
        adSell = adSellService.getAdSellById(id);
        return adSell;
    }

    @Operation(summary = "Get all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sales found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "sales not found")
    })
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

    @Operation(summary = "Get all sales not yours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sales found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "sales not found")
    })
    @GetMapping("/all/user/{id}")
    public List<AdSell> getAllAdsSellForUser(@PathVariable("id") long id) {
        System.out.println("Get all adsSell for User...");
        List<AdSell> adSell = new ArrayList<>();
        adSell = adSellService.getAllAdsSellForUser(id);
        return adSell;
    }

    @Operation(summary = "Get all bikes to sell")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "bikes to sell found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bikes to sell not found")
    })
    @GetMapping("/bikes")
    public List<Bike> getBikesToSell() {
        System.out.println("Get bikes to sell...");
        System.out.println("Bici="+ adSellService.getBikesToSell());
        return adSellService.getBikesToSell();

    }
    @Operation(summary = "Delete a sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "sale not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdSell(@PathVariable("id") long id) {
        System.out.println("Delete adSell with ID = " + id + "...");
        return adSellService.deleteAdSell(id);
    }
    @Operation(summary = "Delete all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sales deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "sales not found")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAdsSell() {
        System.out.println("Delete All adsSell...");
        return adSellService.deleteAllAdsSell();
    }

    @Operation(summary = "Update a sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale updated"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "sale not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdSell> updateAdSell(@PathVariable("id") long id, @RequestBody AdSell adSell) {
        System.out.println("Update adSell with ID = " + id + "...");
        ResponseEntity<AdSell> response = adSellService.updateAdSell(id, adSell);
        return response;
    }

    @Operation(summary = "Get all adSell by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AdSell found"),
            @ApiResponse(responseCode = "400", description = "Invalid AdSell request"),
            @ApiResponse(responseCode = "404", description = "AdSell not found")
    })
    @GetMapping("/user/{id}")
    public List<AdSell> getAllAdsSellByUser(@PathVariable("id") long id) {
        System.out.println("Get all Ads to rent by user...");
        return adSellService.getAllAdSellByUser(id);
    }
    @Operation(summary = "Get all bikes to sell by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes to sell found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bikes to sell not found")
    })
    @GetMapping("user/{id}/bikes")
    public List<Bike> getAllBikeToSellByUser(@PathVariable("id") long id) {
        System.out.println("Get all Bike to Sell by user...");
        return adSellService.getAllBikeToSellByUser(id);
    }

}




