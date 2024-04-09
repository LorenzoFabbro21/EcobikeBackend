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

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService{

    private final BookingRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public ResponseEntity<?> saveBooking(Booking booking) {
        try {
            Booking bookingCreated = repository.save(booking);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Rent successfully created");
            body.put("id", String.valueOf(bookingCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {

            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
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
    public ResponseEntity<?> deleteBooking(long id) {
        repository.deleteById(id);
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "Booking has been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAllBookings() {
        repository.deleteAll();
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "All bookings have been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);
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





    @Override
    @Transactional
    public List<BikeUser> getPersonalRent(long id) {
        List<BikeUser> bikeUser = new ArrayList<>();
        System.out.println("ididididididididid: " + id);
        List<Booking> listBookings = repository.getPersonalRent(id);
        for (Booking b : listBookings) {
            Adrent a = restTemplate.getForObject("http://ad-service/api/adrent/" + b.getIdAnnouncement(), Adrent.class);
            Bike bike = restTemplate.getForObject("http://bike-service/api/bike/" + a.getIdBike(), Bike.class);
            User u = restTemplate.getForObject("http://user-service/api/private/" + b.getIdPrivate(), User.class);
            BikeUser obj = new BikeUser(u, bike, b, a);
            bikeUser.add(obj);
        }

        return bikeUser;
    }


}
