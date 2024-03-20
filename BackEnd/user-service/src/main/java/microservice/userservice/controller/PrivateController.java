package microservice.userservice.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.dto.Booking;
import microservice.userservice.model.Private;
import microservice.userservice.service.PrivateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/private")
@RequiredArgsConstructor
@Slf4j
public class PrivateController {

    private final PrivateService privateService;

    @PostMapping(value = "")
    public Private postPrivate(@RequestBody Private user) {

        Private _private = privateService.savePrivate(new Private(user.getNome(), user.getCognome(), user.getMail(), user.getPassword(), user.getTelefono()));
        return _private;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrivate(@PathVariable("id") long id) {
        System.out.println("Delete Private with ID = " + id + "...");
        return  privateService.deletePrivate(id);
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllPrivates() {
        System.out.println("Delete All Privates...");
        return privateService.deleteAllPrivates();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Private> updatePrivates(@PathVariable("id") long id, @RequestBody Private userprivate) {
        System.out.println("Update Dealer with ID = " + id + "...");
        return privateService.updatePrivate(id, userprivate);

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
