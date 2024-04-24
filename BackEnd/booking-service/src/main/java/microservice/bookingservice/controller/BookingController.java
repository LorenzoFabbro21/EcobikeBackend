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

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping(value = "")
    public ResponseEntity<?> postBooking(@RequestBody Booking booking) {
        if(booking == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return  bookingService.saveBooking(new Booking(booking.getId(), booking.getIdPrivate(), booking.getIdAnnouncement(),booking.getStartdate(), booking.getEnddate()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Booking>> getAllBooking() {
        try {
            System.out.println("Get all Bookings...");
            return bookingService.getAllBookings();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/notprotected")
    public ResponseEntity<List<Booking>> getAllBookingNotProtected() {
        try {
            System.out.println("Get all Bookings...");
            return bookingService.getAllBookings();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{idPrivate}")
    public ResponseEntity<List<Booking>> getAllBookingByPrivate(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Bookings by private...");
            return bookingService.getAllBookingsByPrivate(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete Booking with ID = " + id + "...");
            return bookingService.deleteBooking(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllBookings() {
        try {
            System.out.println("Delete all Bookings...");
            return bookingService.deleteAllBookings();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") Long id, @RequestBody Booking booking) {
        if(id == null || booking == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update Booking with ID = " + id + "...");
            return bookingService.updateBooking(id,booking);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{idPrivate}/bikes")
    public ResponseEntity<List<BikeUser>> getAllBikesRented(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Bikes rented by idPrivate...");
            return bookingService.getAllBikesRented(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/personal/user/{idPrivate}/bikes")
    public ResponseEntity<List<BikeUser>> getPersonalRent(@PathVariable("idPrivate") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get personal rent...");
            return bookingService.getPersonalRent(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}