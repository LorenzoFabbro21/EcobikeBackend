package microservice.adservice.repo;

import microservice.adservice.model.AdRent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRentRepository extends CrudRepository<AdRent, Long> {
}
