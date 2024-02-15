package microservice.bookingservice.service;

import microservice.bookingservice.model.Booking;
import microservice.bookingservice.repo.BookingRepository;
import microservice.gestoreOrdiniservice.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingServiceImpl implements BookingService{

    BookingRepository repository;
    @Override
    public Booking saveBooking(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        repository.findAll().forEach(bookings::add);
        return bookings;
    }

    @Override
    public Optional<Booking> getBookingById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteBooking(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Booking has been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAllBookings() {
        repository.deleteAll();
        return new ResponseEntity<>("All bookings have been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Booking> updateBooking(long id, Booking booking) {
        Optional<Booking> BookingData = repository.findById(id);

        if (BookingData.isPresent()) {
            Booking _booking = BookingData.get();
            _booking.setEnddate(booking.getEnddate());
            _booking.setStartdate(booking.getStartdate());
            repository.save(_booking);
            return new ResponseEntity<>(repository.save(_booking), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
