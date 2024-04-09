package microservice.appointmentservice.service;

import microservice.appointmentservice.dto.BikeUser;
import microservice.appointmentservice.model.Appointment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    ResponseEntity<?> saveAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(long id);

    ResponseEntity<?> deleteAppointment(long id);

    ResponseEntity<?> deleteAllAppointments();

    ResponseEntity<Appointment> updateAppointment(long id, @RequestBody Appointment appointment);

    public List<Appointment> getAllAppointmentsByUser (long id);

    List<BikeUser> getAllBikesSold(long id);

    List<BikeUser> getPersonalBuy(long id);

}
