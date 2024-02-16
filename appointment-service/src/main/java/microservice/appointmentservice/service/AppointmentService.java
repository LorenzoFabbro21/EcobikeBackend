package microservice.appointmentservice.service;

import microservice.appointmentservice.model.Appointment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(long id);

    ResponseEntity<String> deleteAppointment(long id);

    ResponseEntity<String> deleteAllAppointments();

    ResponseEntity<Appointment> updateAppointment(long id, @RequestBody Appointment appointment);
}
