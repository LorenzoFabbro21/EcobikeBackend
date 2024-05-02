package microservice.bookingservice.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.bookingservice.dto.BikeUser;
import microservice.bookingservice.model.Booking;
import microservice.bookingservice.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postBooking(@RequestBody Booking booking) {
        if(booking == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return  bookingService.saveBooking(new Booking(booking.getId(), booking.getIdPrivate(), booking.getIdAnnouncement(),booking.getStartdate(), booking.getEnddate()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get a booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Booking>> getBooking(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get Booking...");
            ResponseEntity<Optional<Booking>> b = bookingService.getBookingById(id);
            if (b.getBody() != null)
                return b;
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain booking by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bookings not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<Booking>> getAllBooking() {
        try {
            System.out.println("Get all Bookings...");
            return bookingService.getAllBookings();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all bookings");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Get all bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bookings not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/notprotected")
    public ResponseEntity<List<Booking>> getAllBookingNotProtected() {
        try {
            System.out.println("Get all Bookings...");
            return bookingService.getAllBookings();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all bookings");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Get all bookings by idPrivate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bookings not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{idPrivate}")
    public ResponseEntity<List<Booking>> getAllBookingByPrivate(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Bookings by private...");
            return bookingService.getAllBookingsByPrivate(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all bookings by private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete a booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete Booking with ID = " + id + "...");
            return bookingService.deleteBooking(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete booking by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Operation(summary = "Delete all bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bookings not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllBookings() {
        try {
            System.out.println("Delete all Bookings...");
            return bookingService.deleteAllBookings();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Update a booking by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") Long id, @RequestBody Booking booking) {
        if(id == null || booking == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update Booking with ID = " + id + "...");
            return bookingService.updateBooking(id,booking);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all Bike rented by idPrivate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes rented found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Bikes rented not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{idPrivate}/bikes")
    public ResponseEntity<List<BikeUser>> getAllBikesRented(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Bikes rented by idPrivate...");
            return bookingService.getAllBikesRented(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all bikes rented");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Get personal rent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personal rent found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Personal rent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/personal/user/{idPrivate}/bikes")
    public ResponseEntity<List<BikeUser>> getPersonalRent(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get personal rent...");
            return bookingService.getPersonalRent(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain personal rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}