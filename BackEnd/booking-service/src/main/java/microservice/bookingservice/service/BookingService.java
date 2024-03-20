package microservice.bookingservice.service;

import microservice.bookingservice.dto.Adrent;
import microservice.bookingservice.dto.BikeUser;
import microservice.bookingservice.model.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking saveBooking(Booking booking);

    List<Booking> getAllBookings();

    Optional<Booking> getBookingById(long id);

    ResponseEntity<String> deleteBooking(long id);

    ResponseEntity<String> deleteAllBookings();

    ResponseEntity<Booking> updateBooking(long id, @RequestBody Booking booking);

    List<Booking> getAllBookingsByPrivate(long id);

    List<BikeUser> getAllBikesRented(long id);
}
