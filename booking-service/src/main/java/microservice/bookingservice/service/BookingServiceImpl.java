package microservice.bookingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.bookingservice.model.Booking;
import microservice.bookingservice.repo.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService{

    private final BookingRepository repository;
    @Override
    @Transactional
    public Booking saveBooking(Booking booking) {
        return repository.save(booking);
    }

    @Override
    @Transactional
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        repository.findAll().forEach(bookings::add);
        return bookings;
    }

    @Override
    @Transactional
    public Optional<Booking> getBookingById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteBooking(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Booking has been deleted!", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteAllBookings() {
        repository.deleteAll();
        return new ResponseEntity<>("All bookings have been deleted!", HttpStatus.OK);
    }

    @Override
    @Transactional
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
