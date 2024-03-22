package microservice.appointmentservice.repo;

import microservice.appointmentservice.model.Appointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
