package microservice.adservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.adservice.dto.*;
import microservice.adservice.model.AdRent;
import microservice.adservice.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.*;

@RestController
@RequestMapping("/api/adrent")
@RequiredArgsConstructor
@Slf4j
public class AdRentController {

    private final AdRentService adRentService;

    @Operation(summary = "Create a new rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rent created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get a rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rent found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Rent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get all rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Rentals not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<AdRent>> getAllAdsRent() {
        try {
            System.out.println("Get all adsRent...");
            return adRentService.getAllAdsRent();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all rents");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Get all rentals not yours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Rent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get all bikes to rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes to rent found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bikes to rent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get all bikes to rent by idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes to rent found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bikes to rent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get all rentals by idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rentals found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Rentals not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Delete a rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rent deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Rent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdRent(@PathVariable("id") Long id) {
        if (id == null)
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


    @Operation(summary = "Delete all rents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rentals deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Rentals not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Update a rent by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rent updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Rent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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