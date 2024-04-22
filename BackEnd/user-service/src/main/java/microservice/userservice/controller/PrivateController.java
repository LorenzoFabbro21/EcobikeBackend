package microservice.userservice.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.model.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.dto.Booking;
import microservice.userservice.model.Private;
import microservice.userservice.service.PrivateService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.*;
import java.util.ArrayList;
import java.nio.charset.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/private")
@RequiredArgsConstructor
@Slf4j
public class PrivateController {

    private final PrivateService privateService;

    @PostMapping(value = "")
    public ResponseEntity<?> postPrivate(@RequestBody Private user) {
        System.out.println("ARRIVO CHIAMATA");
        ResponseEntity<?> resp = privateService.savePrivate(new Private(user.getName(), user.getLastName(), user.getMail(), user.getPassword(), user.getPhoneNumber(),user.getGoogleCheck()));

        return resp;

    }

    @GetMapping("/{id}")
    public Optional<Private> getPrivate(@PathVariable("id") long id) {
        System.out.println("Get Private...");
        Optional<Private> userprivate;
        userprivate = privateService.getPrivateById(id);
        return userprivate;
    }

    @GetMapping("")
    public List<Private> getAllPrivate() {
        System.out.println("Get all Privates...");
        List<Private> userprivate;
        userprivate= privateService.getAllPrivates();
        return userprivate;
    }

    @GetMapping("/email/{mail}")
    public Optional<Private> getPrivateByMail(@PathVariable String mail) {
        System.out.println("Get private by mail...");
        Optional<Private> private_user;
        private_user = privateService.getPrivateByMail(mail);
        return private_user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrivate(@PathVariable("id") long id) {
        System.out.println("Delete Private with ID = " + id + "...");
        return privateService.deletePrivate(id);
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllPrivates() {
        System.out.println("Delete All Privates...");
        return privateService.deleteAllPrivates();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Private> updatePrivates(@PathVariable("id") long id, @RequestBody Private userprivate) {
        System.out.println("Update Private with ID = " + id + "...");
        return privateService.updatePrivate(id, userprivate);

    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String password) {

        return privateService.verifyParams(email, password);
    }
   
    @GetMapping("/{idPrivate}/booking")
    public List<Booking> getAllBookingByPrivate(@PathVariable("idPrivate") long id) {
        System.out.println("Get all Bookings by idPrivate...");
        List<Booking> bookings = new ArrayList<>();
        bookings= privateService.getAllBookings(id);
        return bookings;
        
    }
    

    @GetMapping("/{idPrivate}/appointments")
    public List<Appointment> getAllAppointmentsByPrivate(@PathVariable("idPrivate") long id) {
        System.out.println("Get all Appointements by idPrivate...");
        List<Appointment> appointments = new ArrayList<>();
        appointments = privateService.getAllAppointments(id);
        return appointments;
    }
}
