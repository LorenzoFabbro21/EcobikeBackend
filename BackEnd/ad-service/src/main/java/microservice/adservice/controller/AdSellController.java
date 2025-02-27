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

    @Operation(summary = "Create a new sale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postAdSell(@RequestBody AdSell adSell) {
        if(adSell == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return adSellService.saveAdSell(new AdSell(adSell.getPrice(), adSell.getIdBike(), adSell.getIdUser()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get a sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "sale not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<AdSell>> getAdSell(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get adSell...");
            return adSellService.getAdSellById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain ad sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sales found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "sales not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<AdSell>> getAllAdsSell() {
        try {
            System.out.println("Get all adsSell...");
            return adSellService.getAllAdsSell();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Get all sales not sold")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sales found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "sales not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/notSold")
    public ResponseEntity<List<AdSell>> getAllAdsSellNotSold() {
        try {
            System.out.println("Get all adsSell not sold...");
            return adSellService.getAllAdsSellNotSold();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all sell not sold");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all sales not yours")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sales found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "sales not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all/user/{id}")
    public ResponseEntity<List<AdSell>> getAllAdsSellForUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all adsSell for User...");
            return adSellService.getAllAdsSellForUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all adsell for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all bikes to sell")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "bikes to sell found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bikes to sell not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getBikesToSell() {
        try {
            System.out.println("Get bikes to sell...");
            return adSellService.getBikesToSell();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain bikes to sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Delete a sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "sale not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdSell(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete adSell with ID = " + id + "...");
            return adSellService.deleteAdSell(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete ad sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Delete all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sales deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "sales not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAdsSell() {
        try {
            System.out.println("Delete All adsSell...");
            return adSellService.deleteAllAdsSell();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all ads sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update a sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sale updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "sale not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdSell> updateAdSell(@PathVariable("id") Long id, @RequestBody AdSell adSell) {
        if(id == null || adSell == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update adSell with ID = " + id + "...");
            return adSellService.updateAdSell(id, adSell);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update ad sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all adSell by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AdSell found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "AdSell not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<AdSell>> getAllAdsSellByUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Ads to sell by user...");
            return adSellService.getAllAdSellByUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ads sell by user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Get all bikes to sell by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes to sell found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bikes to sell not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("user/{id}/bikes")
    public ResponseEntity<List<Bike>> getAllBikeToSellByUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Ads to sell by user...");
            return adSellService.getAllBikeToSellByUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ads sell by user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}