package microservice.adservice.repo;

import microservice.adservice.model.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRentRepository extends CrudRepository<AdRent, Long> {
}