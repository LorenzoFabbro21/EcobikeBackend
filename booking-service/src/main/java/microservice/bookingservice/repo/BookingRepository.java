package microservice.bookingservice.repo;

import microservice.bookingservice.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository <Booking, Long> {
}
