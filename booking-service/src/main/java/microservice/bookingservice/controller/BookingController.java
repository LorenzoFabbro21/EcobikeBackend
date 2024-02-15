package microservice.bookingservice.controller;

import microservice.bookingservice.model.Booking;
import microservice.bookingservice.service.BookingService;
import microservice.gestoreOrdiniservice.model.Order;
import microservice.gestoreOrdiniservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class BookingController {
    private BookingService bookingService;

    @PostMapping(value = "/booking/create")
    public Booking postBooking(@RequestBody Booking booking) {
        Booking _booking = bookingService.saveBooking(new Booking(booking.getId(), booking.getIdPrivate(), booking.getIdAnnouncement(),booking.getStartdate(), booking.getEnddate()));
        return _booking;
    }

    @GetMapping("/booking/{id}")
    public Booking getBooking(@PathVariable("id") long id) {
        System.out.println("Get Booking...");
        Booking booking = new Booking();
        if (bookingService.getBookingById(id).isPresent())
            return booking;
        else
            return null;
    }

    @GetMapping("/booking")
    public List<Booking> getAllBooking() {
        System.out.println("Get all Bookings...");
        List<Booking> bookings = new ArrayList<>();
        bookingService.getAllBookings().forEach(bookings::add);
        return bookings;
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") long id) {
        System.out.println("Delete Booking with ID = " + id + "...");
        ResponseEntity<String> response = bookingService.deleteBooking(id);
        return response;
    }
    @DeleteMapping("/booking")
    public ResponseEntity<String> deleteAllBookings() {
        System.out.println("Delete all Bookings...");
        ResponseEntity<String> response = bookingService.deleteAllBookings();
        return response;
    }

    @PutMapping("/booking/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") long id, @RequestBody Booking booking) {
        System.out.println("Update Booking with ID = " + id + "...");
        ResponseEntity<Booking> response = bookingService.updateBooking(id,booking);
        return response;
    }
}
