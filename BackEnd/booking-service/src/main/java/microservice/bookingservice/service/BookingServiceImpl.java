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
        if(booking == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            String response = validateRequest(booking);
            if (!response.equals("ok"))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

            Booking bookingCreated = repository.save(booking);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Rent successfully created");
            body.put("id", String.valueOf(bookingCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }


    private String validateRequest(Booking booking) {
        String response = validateBookingParams(booking);
        if (!response.equals("ok"))
            return response;
        return "ok";
    }

    private String validateBookingParams(Booking booking) {
        if (booking.getStartdate() == null)
            return "Data di inizio inserita non valida";
        if (booking.getEnddate() == null)
            return "Data di fine inserita non valida";
        return "ok";
    }


    @Override
    @Transactional
    public ResponseEntity<List<Booking>> getAllBookings() {
        try {
            List<Booking> bookings = new ArrayList<>();
            repository.findAll().forEach(bookings::add);
            return ResponseEntity.status(HttpStatus.OK).body(bookings);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Optional<Booking>> getBookingById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(repository.findById(id));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain booking by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBooking(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Booking has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete booking by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAllBookings() {
        try {
            repository.deleteAll();
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "All bookings have been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Booking> updateBooking(Long id, Booking booking) {
        if(id == null || booking == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
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
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update booking");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<Booking>> getAllBookingsByPrivate(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<Booking> bookings = repository.getAllBookingsByPrivate(id);
            return ResponseEntity.status(HttpStatus.OK).body(bookings);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain booking by private");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<BikeUser>> getAllBikesRented(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<BikeUser> bikeUser = new ArrayList<>();
            List<Adrent> adrents = restTemplate.exchange(
                    "http://ad-service:8084/api/adrent/user/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Adrent>>() {}
            ).getBody();
            for (Adrent a: adrents) {
                List<Booking> bookings = repository.getAllBookingsByAnnouncement(a.getId());
                Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + a.getIdBike(), Bike.class);
                for(Booking b: bookings){
                    User user = restTemplate.getForObject("http://user-service:8081/api/private/" + b.getIdPrivate(), User.class);
                    BikeUser obj = new BikeUser(user, bike, b, a);
                    bikeUser.add(obj);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(bikeUser);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all bikes rented");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @Override
    @Transactional
    public ResponseEntity<List<BikeUser>> getPersonalRent(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<BikeUser> bikeUser = new ArrayList<>();
            List<Booking> listBookings = repository.getPersonalRent(id);
            for (Booking b : listBookings) {
                Adrent a = restTemplate.getForObject("http://ad-service:8084/api/adrent/" + b.getIdAnnouncement(), Adrent.class);
                Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + a.getIdBike(), Bike.class);
                User u = restTemplate.getForObject("http://user-service:8081/api/private/" + b.getIdPrivate(), User.class);
                BikeUser obj = new BikeUser(u, bike, b, a);
                bikeUser.add(obj);
            }
            return ResponseEntity.status(HttpStatus.OK).body(bikeUser);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed obtain personal rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}