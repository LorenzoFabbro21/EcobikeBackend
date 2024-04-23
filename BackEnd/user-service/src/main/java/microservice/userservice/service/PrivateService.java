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

        List<Private> getAllPrivates();

        Optional<Private> getPrivateById(long id);

        ResponseEntity<?> deletePrivate(long id);

        ResponseEntity<String> deleteAllPrivates();

        ResponseEntity<Private> updatePrivate(long id, @RequestBody Private userprivate);

        Optional<Private> getPrivateByMail(String mail);

        List<Booking> getAllBookings(long id);
        List<Appointment> getAllAppointments(long id);
        ResponseEntity<String> verifyParams(String email, String password);
}
