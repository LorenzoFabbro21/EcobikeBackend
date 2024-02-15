package microservice.adservice.repo;

import microservice.adservice.model.Ad;
import org.springframework.data.repository.CrudRepository;

public interface AdRepository extends CrudRepository<Ad, Long> {
}
