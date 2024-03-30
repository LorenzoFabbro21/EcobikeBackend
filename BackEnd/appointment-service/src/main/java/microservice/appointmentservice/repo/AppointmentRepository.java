package microservice.appointmentservice.repo;

import microservice.appointmentservice.dto.BikeUser;
import microservice.appointmentservice.model.Appointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT b FROM Appointment b WHERE " + " b.idUser = ?1")
    public List<Appointment> getAllAppointmentsByUser (long id);

    


    @Query("SELECT b FROM Appointment b WHERE " + " b.idAnnouncement = ?1")
    public List<Appointment> getAllAppointmentsByAnnouncement (long id);


}
