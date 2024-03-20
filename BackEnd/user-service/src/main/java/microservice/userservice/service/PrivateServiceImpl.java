package microservice.userservice.service;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.dto.Appointment;
import microservice.userservice.dto.Booking;
import microservice.userservice.model.Private;
import microservice.userservice.repo.PrivateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateServiceImpl implements PrivateService {

    private final PrivateRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public Private savePrivate(Private user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public List<Private> getAllPrivates() {
        List<Private> privates = new ArrayList<>();
        repository.findAll().forEach(privates::add);
        return privates;
    }

    @Override
    @Transactional
    public Optional<Private> getPrivateById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deletePrivate(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Private has been deleted!", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteAllPrivates() {
       repository.deleteAll();
       return new ResponseEntity<>("All Privates have been deleted!", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Private> updatePrivate(long id, Private userprivate) {
        Optional<Private> privatedata = repository.findById(id);

        if(privatedata.isPresent()) {
            Private Private = privatedata.get();
            Private.setNome(userprivate.getNome());
            Private.setCognome(userprivate.getCognome());
            Private.setMail(userprivate.getMail());
            Private.setPassword(userprivate.getPassword());
            Private.setTelefono(userprivate.getTelefono());
            repository.save(Private);
            return new ResponseEntity<>(repository.save(Private), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Booking> getAllBookings(long id) {
        Optional<Private> privateData = repository.findById(id);
        List<Booking> bookings;
        if (privateData.isPresent()) {
            Private _private = privateData.get();
            ResponseEntity<List<Booking>> response;

            bookings = restTemplate.exchange(
                    "http://booking-service/api/booking/user/" + _private.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Booking>>() {}
            ).getBody();


            return bookings;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Appointment> getAllAppointments(long id) {
        Optional<Private> privateData = repository.findById(id);
        List<Appointment> appointments;
        if (privateData.isPresent()) {
            Private _private = privateData.get();
            ResponseEntity<List<Appointment>> response;

            appointments = restTemplate.exchange(
                    "http://appointment-service/api/appointment/user/" + _private.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Appointment>>() {}
            ).getBody();


            return appointments;
        }
        else {
            return null;
        }
    }
}
