package microservice.userservice.service;

import microservice.userservice.dto.Appointment;
import microservice.userservice.dto.Booking;
import microservice.userservice.model.Private;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface PrivateService {

        ResponseEntity<?> savePrivate(Private user);

        ResponseEntity<List<Private>> getAllPrivates();

        ResponseEntity<Optional<Private>> getPrivateById(Long id);

        ResponseEntity<?> deletePrivate(Long id);

        ResponseEntity<String> deleteAllPrivates();

        ResponseEntity<Private> updatePrivate(Long id, @RequestBody Private userprivate);

        ResponseEntity<Optional<Private>> getPrivateByMail(String mail);

        ResponseEntity<List<Booking>> getAllBookings(Long id);
        ResponseEntity<List<Appointment>> getAllAppointments(Long id);
        ResponseEntity<String> verifyParams(String email, String password);
}
