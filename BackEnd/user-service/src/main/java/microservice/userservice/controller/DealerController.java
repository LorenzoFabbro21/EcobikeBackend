package microservice.userservice.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.model.Dealer;
import microservice.userservice.service.DealerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dealer")
@RequiredArgsConstructor
@Slf4j
public class DealerController {

    private final DealerService dealerService;

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