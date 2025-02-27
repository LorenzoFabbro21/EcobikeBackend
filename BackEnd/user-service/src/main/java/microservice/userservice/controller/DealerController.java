package microservice.userservice.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.model.Dealer;
import microservice.userservice.service.DealerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dealer")
@RequiredArgsConstructor
@Slf4j
public class DealerController {

    private final DealerService dealerService;

    @Operation(summary = "Create a new dealer", description = "Create a new dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "")
    public ResponseEntity<Dealer> postDealer(@RequestBody Dealer dealer) {
        if(dealer == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return dealerService.saveDealer(new Dealer(dealer.getName(), dealer.getLastName(), dealer.getMail(), dealer.getPassword(), dealer.getPhoneNumber(),dealer.getGoogleCheck()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Get dealer", description = "Get a dealer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Dealer>> getDealer(@PathVariable Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get dealer...");
            return dealerService.getDealerById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Get dealer", description = "Get dealer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/email/{mail}")
    public ResponseEntity<Optional<Dealer>> getDealerByMail(@PathVariable String mail) {
        if(mail == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get dealer by mail...");
            return dealerService.getDealerByMail(mail);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain dealer by mail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all dealer", description = "Get all dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the dealers"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealers not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<Dealer>> getAllDealer() {
        try {
            System.out.println("Get all dealers...");
            return dealerService.getAllDealers();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete dealer", description = "Delete dealer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDealer(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete Dealer with ID = " + id + "...");
            return dealerService.deleteDealer(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete dealer by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Delete all dealers", description = "Delete all dealers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealers deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllDealers() {
        try {
            System.out.println("Delete All Dealers...");
            return dealerService.deleteAllDealers();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all dealers");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update a dealer", description = "Update a dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Dealer> updateDealer(@PathVariable("id") Long id, @RequestBody Dealer dealer) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update Dealer with ID = " + id + "...");
            return dealerService.updateDealer(id, dealer);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String password) {
        try {
            return dealerService.verifyParams(email, password);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to verify user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @Operation(summary = "Get all appointments", description = "Get all appointments by a idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{idDealer}/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointmentsByDealer(@PathVariable("idDealer") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Appointements by idDealer...");
            return dealerService.getAllAppointments(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all appointment by dealer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}