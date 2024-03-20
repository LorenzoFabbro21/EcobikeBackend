package microservice.userservice.service;

import microservice.userservice.dto.Appointment;
import microservice.userservice.dto.Booking;
import microservice.userservice.model.Private;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface PrivateService {

        Private savePrivate(Private user);

        List<Private> getAllPrivates();

        Optional<Private> getPrivateById(long id);

        ResponseEntity<String> deletePrivate(long id);

        ResponseEntity<String> deleteAllPrivates();

        ResponseEntity<Private> updatePrivate(long id, @RequestBody Private userprivate);

        List<Booking> getAllBookings(long id);
        List<Appointment> getAllAppointments(long id);
}
