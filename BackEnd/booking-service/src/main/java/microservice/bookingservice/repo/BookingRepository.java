package microservice.bookingservice.repo;

import microservice.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository <Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE " + " b.idPrivate = ?1")
    public List<Booking> getAllBookingsByPrivate (long id);

    @Query("SELECT b FROM Booking b WHERE " + " b.idAnnouncement = ?1")
    public List<Booking> getAllBookingsByAnnouncement (long id);

    @Query("SELECT b FROM Booking b WHERE " + " b.idPrivate = ?1")
    public List<Booking> getPersonalRent (long id);
}
