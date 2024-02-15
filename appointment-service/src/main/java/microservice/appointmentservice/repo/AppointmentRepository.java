package microservice.appointmentservice.repo;

import microservice.appointmentservice.model.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
