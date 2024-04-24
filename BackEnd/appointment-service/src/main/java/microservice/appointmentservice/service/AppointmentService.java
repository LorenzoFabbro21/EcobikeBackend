package microservice.appointmentservice.service;

import microservice.appointmentservice.dto.BikeUser;
import microservice.appointmentservice.model.Appointment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    ResponseEntity<?> saveAppointment(Appointment appointment);

    ResponseEntity<List<Appointment>> getAllAppointments();

    ResponseEntity<Optional<Appointment>> getAppointmentById(Long id);

    ResponseEntity<?> deleteAppointment(Long id);

    ResponseEntity<?> deleteAllAppointments();

    ResponseEntity<Appointment> updateAppointment(Long id, @RequestBody Appointment appointment);

    ResponseEntity<List<Appointment>> getAllAppointmentsByUser (Long id);

    ResponseEntity<List<BikeUser>> getAllBikesSold(Long id);

    ResponseEntity<List<BikeUser>> getPersonalBuy(Long id);

}
