package microservice.bookingservice.service;

import microservice.bookingservice.dto.Adrent;
import microservice.bookingservice.dto.BikeUser;
import microservice.bookingservice.model.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    ResponseEntity<?> saveBooking(Booking booking);

    ResponseEntity<List<Booking>> getAllBookings();

    ResponseEntity<Optional<Booking>> getBookingById(Long id);

    ResponseEntity<?> deleteBooking(Long id);

    ResponseEntity<?> deleteAllBookings();

    ResponseEntity<Booking> updateBooking(Long id, @RequestBody Booking booking);

    ResponseEntity<List<Booking>> getAllBookingsByPrivate(Long id);

    ResponseEntity<List<BikeUser>> getAllBikesRented(Long id);

    ResponseEntity<List<BikeUser>> getPersonalRent(Long id);
}
