package microservice.userservice.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.model.Dealer;
import microservice.userservice.service.DealerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dealer")
@RequiredArgsConstructor
@Slf4j
public class DealerController {

    private final DealerService dealerService;

    @PostMapping(value = "")
    public Dealer postDealer(@RequestBody Dealer dealer) {

        Dealer _Dealer = dealerService.saveDealer(new Dealer(dealer.getNome(), dealer.getCognome(), dealer.getMail(), dealer.getPassword(), dealer.getTelefono()));
        return _Dealer;
    }

    @GetMapping("/{id}")
    public Optional<Dealer> getDealer(@PathVariable long id) {
        System.out.println("Get dealer...");
        Optional<Dealer> delaer;
        delaer = dealerService.getDealerById(id);
        return delaer;
    }

    @GetMapping("")
    public List<Dealer> getAllDealer() {
        System.out.println("Get all dealers...");
        List<Dealer> dealer = new ArrayList<>();
        dealer= dealerService.getAllDealers();
        return dealer;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDealer(@PathVariable("id") long id) {
        System.out.println("Delete Dealer with ID = " + id + "...");
        ResponseEntity<String> response = dealerService.deleteDealer(id);
        return response;
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllDealers() {
        System.out.println("Delete All Dealers...");

        ResponseEntity<String> response = dealerService.deleteAllDealers();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dealer> updateDealer(@PathVariable("id") long id, @RequestBody Dealer dealer) {
        System.out.println("Update Dealer with ID = " + id + "...");
        ResponseEntity<Dealer> response = dealerService.updateDealer(id, dealer);
        return response;
    }

    @GetMapping("/{idDealer}/appointments")
    public List<Appointment> getAllAppointmentsByDealer(@PathVariable("idDealer") long id) {
        System.out.println("Get all Appointements by idDealer...");
        List<Appointment> appointments = new ArrayList<>();
        appointments = dealerService.getAllAppointments(id);
        return appointments;
    }
}




