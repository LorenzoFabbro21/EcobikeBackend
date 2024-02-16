package microservice.bikeservice.repo;

import microservice.bikeservice.model.Bike;
import org.springframework.data.repository.CrudRepository;

public interface BikeRepository extends CrudRepository<Bike, Long> {
}
