package microservice.userservice.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.model.Dealer;
import microservice.userservice.service.DealerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dealer")
@RequiredArgsConstructor
@Slf4j
public class DealerController {

    private final DealerService dealerService;

    @Operation(summary = "Create a new dealer", description = "Create a new dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    @PostMapping(value = "")
    public Dealer postDealer(@RequestBody Dealer dealer) {

        Dealer _Dealer = dealerService.saveDealer(new Dealer(dealer.getName(), dealer.getLastName(), dealer.getMail(), dealer.getPassword(), dealer.getPhoneNumber(),dealer.getGoogleCheck()));
        return _Dealer;
    }
    @Operation(summary = "Get dealer", description = "Get a dealer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found")
    })
    @GetMapping("/{id}")
    public Optional<Dealer> getDealer(@PathVariable long id) {
        System.out.println("Get dealer...");
        Optional<Dealer> delaer;
        delaer = dealerService.getDealerById(id);
        return delaer;
    }


    @Operation(summary = "Get dealer", description = "Get dealer by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found")
    })
    @GetMapping("/email/{mail}")
    public Optional<Dealer> getDealerByMail(@PathVariable String mail) {
        System.out.println("Get dealer by mail...");
        Optional<Dealer> delaer;
        delaer = dealerService.getDealerByMail(mail);
        return delaer;
    }

    @Operation(summary = "Get all dealer", description = "Get all dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the dealers"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealers not found")
    })
    @GetMapping("")
    public List<Dealer> getAllDealer() {
        System.out.println("Get all dealers...");
        List<Dealer> dealer = new ArrayList<>();
        dealer= dealerService.getAllDealers();
        return dealer;
    }

    @Operation(summary = "Delete dealer", description = "Delete dealer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDealer(@PathVariable("id") long id) {
        System.out.println("Delete Dealer with ID = " + id + "...");
        ResponseEntity<String> response = dealerService.deleteDealer(id);
        return response;
    }
    @Operation(summary = "Delete all dealers", description = "Delete all dealers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealers deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllDealers() {
        System.out.println("Delete All Dealers...");

        ResponseEntity<String> response = dealerService.deleteAllDealers();

        return response;
    }

    @Operation(summary = "Update a dealer", description = "Update a dealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dealer updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Dealer> updateDealer(@PathVariable("id") long id, @RequestBody Dealer dealer) {
        System.out.println("Update Dealer with ID = " + id + "...");
        ResponseEntity<Dealer> response = dealerService.updateDealer(id, dealer);
        return response;
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String password) {

        return dealerService.verifyParams(email, password);
    }



    @Operation(summary = "Get all appointments", description = "Get all appointments by a idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Dealer not found")
    })
    @GetMapping("/{idDealer}/appointments")
    public List<Appointment> getAllAppointmentsByDealer(@PathVariable("idDealer") long id) {
        System.out.println("Get all Appointements by idDealer...");
        List<Appointment> appointments = new ArrayList<>();
        appointments = dealerService.getAllAppointments(id);
        return appointments;
    }
}




