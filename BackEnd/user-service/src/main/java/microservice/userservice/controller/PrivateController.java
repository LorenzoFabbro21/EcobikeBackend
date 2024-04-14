package microservice.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/private")
@RequiredArgsConstructor
@Slf4j
public class PrivateController {

    private final PrivateService privateService;

    @Operation(summary = "Create a new private", description = "Create a new private")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping(value = "")
    public ResponseEntity<String> postPrivate(@RequestBody Private user) {
        ResponseEntity<String> resp = privateService.savePrivate(new Private(user.getName(), user.getLastName(), user.getMail(), user.getPassword(), user.getPhoneNumber(),user.getGoogleCheck()));
        return resp;

    }

    @Operation(summary = "Get a private", description = "Create a private by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found")
    })
    @GetMapping("/{id}")
    public Optional<Private> getPrivate(@PathVariable("id") long id) {
        System.out.println("Get Private...");
        Optional<Private> userprivate;
        userprivate = privateService.getPrivateById(id);
        return userprivate;
    }

    @Operation(summary = "Get all privates", description = "Get all privates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Privates found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    @GetMapping("")
    public List<Private> getAllPrivate() {
        System.out.println("Get all Privates...");
        List<Private> userprivate;
        userprivate= privateService.getAllPrivates();
        return userprivate;
    }

    @Operation(summary = "Get a private", description = "Get a private by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found")
    })
    @GetMapping("/email/{mail}")
    public Optional<Private> getPrivateByMail(@PathVariable String mail) {
        System.out.println("Get private by mail...");
        Optional<Private> private_user;
        private_user = privateService.getPrivateByMail(mail);
        return private_user;
    }

    @Operation(summary = "Delete a private", description = "Delete a private by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrivate(@PathVariable("id") long id) {
        System.out.println("Delete Private with ID = " + id + "...");
        return privateService.deletePrivate(id);
    }
    @Operation(summary = "Delete all privates", description = "Delete all privates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Privates deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllPrivates() {
        System.out.println("Delete All Privates...");
        return privateService.deleteAllPrivates();

    }

    @Operation(summary = "Update a private", description = "Update a private by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Private> updatePrivates(@PathVariable("id") long id, @RequestBody Private userprivate) {
        System.out.println("Update Private with ID = " + id + "...");
        return privateService.updatePrivate(id, userprivate);

    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String password) {

        return privateService.verifyParams(email, password);
    }

    @Operation(summary = "Get all bookings", description = "Get all bookings by a idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found")
    })
    @GetMapping("/{idPrivate}/booking")
    public List<Booking> getAllBookingByPrivate(@PathVariable("idPrivate") long id) {
        System.out.println("Get all Bookings by idPrivate...");
        List<Booking> bookings = new ArrayList<>();
        bookings= privateService.getAllBookings(id);
        return bookings;
        
    }


    @Operation(summary = "Get all appointments", description = "Get all appointments by a idPrivate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found")
    })
    @GetMapping("/{idPrivate}/appointments")
    public List<Appointment> getAllAppointmentsByPrivate(@PathVariable("idPrivate") long id) {
        System.out.println("Get all Appointements by idPrivate...");
        List<Appointment> appointments = new ArrayList<>();
        appointments = privateService.getAllAppointments(id);
        return appointments;
    }
}
