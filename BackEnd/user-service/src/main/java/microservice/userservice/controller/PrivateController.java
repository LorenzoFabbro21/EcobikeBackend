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
import java.util.*;
import java.nio.charset.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/private")
@RequiredArgsConstructor
@Slf4j
public class PrivateController {

    private final PrivateService privateService;

    @Operation(summary = "Create a new private", description = "Create a new private")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postPrivate(@RequestBody Private user) {
        if(user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return privateService.savePrivate(new Private(user.getName(), user.getLastName(), user.getMail(), user.getPassword(), user.getPhoneNumber(),user.getGoogleCheck()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Operation(summary = "Get a private", description = "Create a private by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Private>> getPrivate(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get Private...");
            return privateService.getPrivateById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all privates", description = "Get all privates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Privates found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<Private>> getAllPrivate() {
        try {
            System.out.println("Get all Privates...");
            return privateService.getAllPrivates();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get a private", description = "Get a private by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/email/{mail}")
    public ResponseEntity<Optional<Private>> getPrivateByMail(@PathVariable String mail) {
        if(mail == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get private by mail...");
            return privateService.getPrivateByMail(mail);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain private by mail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete a private", description = "Delete a private by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrivate(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete Private with ID = " + id + "...");
            return privateService.deletePrivate(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete private by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Delete all privates", description = "Delete all privates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Privates deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllPrivates() {
        try {
            System.out.println("Delete All Privates...");
            return privateService.deleteAllPrivates();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all privates");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update a private", description = "Update a private by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Private updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Private> updatePrivates(@PathVariable("id") long id, @RequestBody Private userprivate) {
        try {
            System.out.println("Update Private with ID = " + id + "...");
            return privateService.updatePrivate(id, userprivate);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String password) {
        try {
            return privateService.verifyParams(email, password);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to verify user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all bookings", description = "Get all bookings by a idDealer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{idPrivate}/booking")
    public ResponseEntity<List<Booking>> getAllBookingByPrivate(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Bookings by idPrivate...");
            return privateService.getAllBookings(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all booking by private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Get all appointments", description = "Get all appointments by a idPrivate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Private not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{idPrivate}/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointmentsByPrivate(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Appointements by idPrivate...");
            return privateService.getAllAppointments(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all appointment by private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}