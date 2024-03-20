package microservice.bookingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.bookingservice.dto.Adrent;
import microservice.bookingservice.dto.Bike;
import microservice.bookingservice.dto.BikeUser;
import microservice.bookingservice.dto.User;
import microservice.bookingservice.model.Booking;
import microservice.bookingservice.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService{

    private final BookingRepository repository;

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    @Transactional
    public List<Booking> getAllBookingsByPrivate(long id) {
        List<Booking> bookings = repository.getAllBookingsByPrivate(id);
        return bookings;
    }

    @Override
    public List<BikeUser> getAllBikesRented(long id) {
        List<BikeUser> bikeUser = new ArrayList<>();
        List<Adrent> adrents = restTemplate.exchange(
                "http://ad-service/api/adrent/user/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Adrent>>() {}
        ).getBody();
        for (Adrent a: adrents) {
            List<Booking> bookings = repository.getAllBookingsByAnnouncement(a.getId());
            Bike bike = restTemplate.getForObject("http://bike-service/api/bike/" + a.getIdBike(), Bike.class);
            for(Booking b: bookings){
                User user = restTemplate.getForObject("http://user-service/api/private/" + b.getIdPrivate(), User.class);
                BikeUser obj = new BikeUser(user, bike, b, a);
                bikeUser.add(obj);
            }
        }
        return bikeUser;
    }
}
