package microservice.bookingservice.repo;

import microservice.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository <Booking, Long> {
}
