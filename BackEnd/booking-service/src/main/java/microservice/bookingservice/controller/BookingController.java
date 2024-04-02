package microservice.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.bookingservice.dto.BikeUser;
import microservice.bookingservice.model.Booking;
import microservice.bookingservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping(value = "")
    public ResponseEntity<?> postBooking(@RequestBody Booking booking) {
        return  bookingService.saveBooking(new Booking(booking.getId(), booking.getIdPrivate(), booking.getIdAnnouncement(),booking.getStartdate(), booking.getEnddate()));
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable("id") long id) {
        System.out.println("Get Booking...");
        Booking booking = new Booking();
        if (bookingService.getBookingById(id).isPresent())
            return booking;
        else
            return null;
    }

    @GetMapping("")
    public List<Booking> getAllBooking() {
        System.out.println("Get all Bookings...");
        List<Booking> bookings = new ArrayList<>();
        bookingService.getAllBookings().forEach(bookings::add);
        return bookings;
    }

    @GetMapping("/user/{idPrivate}")
    public List<Booking> getAllBookingByPrivate(@PathVariable("idPrivate") long id) {
        System.out.println("Get all Bookings by idPrivate...");
        List<Booking> bookings = new ArrayList<>();
        bookingService.getAllBookingsByPrivate(id).forEach(bookings::add);
        return bookings;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") long id) {
        System.out.println("Delete Booking with ID = " + id + "...");
        return bookingService.deleteBooking(id);
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllBookings() {
        System.out.println("Delete all Bookings...");
        return bookingService.deleteAllBookings();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") long id, @RequestBody Booking booking) {
        System.out.println("Update Booking with ID = " + id + "...");
        ResponseEntity<Booking> response = bookingService.updateBooking(id,booking);
        return response;
    }

    @GetMapping("/user/{idPrivate}/bikes")
    public List<BikeUser> getAllBikesRented(@PathVariable("idPrivate") long id) {
        System.out.println("Get all Bikes rented by idPrivate...");
        return bookingService.getAllBikesRented(id);
    }
}
