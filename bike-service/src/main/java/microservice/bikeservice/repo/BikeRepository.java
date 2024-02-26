package microservice.bikeservice.repo;

import microservice.bikeservice.model.Bike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;

@Repository
public interface BikeRepository extends CrudRepository<Bike, Long> {
}
