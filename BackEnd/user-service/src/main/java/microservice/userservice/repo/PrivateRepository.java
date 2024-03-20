package microservice.userservice.repo;

import microservice.userservice.dto.Booking;
import microservice.userservice.model.Dealer;
import microservice.userservice.model.Private;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface PrivateRepository extends CrudRepository<Private, Long> {

}