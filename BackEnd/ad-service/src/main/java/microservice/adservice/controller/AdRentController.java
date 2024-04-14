package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.dto.*;
import microservice.adservice.model.AdRent;
import microservice.adservice.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/adrent")
@RequiredArgsConstructor
@Slf4j
public class AdRentController {

    private final AdRentService adRentService;

    @Operation(summary = "Create a new rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rent created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postAdRent(@RequestBody AdRent adRent) {

        return  adRentService.saveAdRent(new AdRent(adRent.getPrice(), adRent.getIdBike(), adRent.getIdUser()));
    }

    @Operation(summary = "Get a rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rent found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Rent not found")
    })
    @GetMapping("/{id}")
    public Optional<AdRent> getAdRent(@PathVariable("id") long id) {
        System.out.println("Get adRent...");
        Optional<AdRent> adRent;
        adRent = adRentService.getAdRentById(id);
        return adRent;
    }

    @Operation(summary = "Get all rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Rentals not found")
    })
    @GetMapping("")
    public List<AdRent> getAllAdsRent() {
        System.out.println("Get all adsRent...");
        List<AdRent> adRent = new ArrayList<>();
        adRent = adRentService.getAllAdsRent();
        return adRent;
    }


    @Operation(summary = "Get all rentals not yours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Rent not found")
    })
    @GetMapping("/all/user/{id}")
    public List<AdRent> getAllAdsRentForUser(@PathVariable("id") long id) {
        System.out.println("Get all adsRent for User...");
        List<AdRent> adRent = new ArrayList<>();
        adRent = adRentService.getAllAdsRentForUser(id);
        return adRent;
    }

    @Operation(summary = "Get all bikes to rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes to rent found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bikes to rent not found")
    })
    @GetMapping("/bikes")
    public List<Bike> getBikesToRent() {
        System.out.println("Get bikes to rent...");
        System.out.println("Bikes="+adRentService.getBikesToRent());
        return adRentService.getBikesToRent();
    }

    @Operation(summary = "Get all bikes to rent by idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes to rent found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bikes to rent not found")
    })
    @GetMapping("/user/{id}/bikes")
    public List<Bike> getBikesUser(@PathVariable("id") long id) {
        System.out.println("Get all Bike to rent by user...");
        return adRentService.getBikesUser(id);
    }

    @Operation(summary = "Get all rentals by idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rentals found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Rentals not found")
    })
    @GetMapping("/user/{id}")
    public List<AdRent> getAllAdsRentByUser(@PathVariable("id") long id) {
        System.out.println("Get all Ads to rent by user...");
        return adRentService.getAllAdRentsByUser(id);
    }

    @Operation(summary = "Delete a rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rent deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Rent not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdRent(@PathVariable("id") long id) {
        System.out.println("Delete adRent with ID = " + id + "...");
        return  adRentService.deleteAdRent(id);
    }
    @Operation(summary = "Delete all rents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rentals deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Rentals not found")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAdsRent() {
        System.out.println("Delete All adsRent...");
        return  adRentService.deleteAllAdsRent();
    }

    @Operation(summary = "Update a rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rent updated"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Rent not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdRent> updateAdRent(@PathVariable("id") long id, @RequestBody AdRent adRent) {
        System.out.println("Update adRent with ID = " + id + "...");
        ResponseEntity<AdRent> response = adRentService.updateAdRent(id, adRent);
        return response;
    }
}
