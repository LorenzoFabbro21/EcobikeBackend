package microservice.userservice.repo;

import microservice.userservice.model.Dealer;
import microservice.userservice.model.Private;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;

@Repository
public interface PrivateRepository extends CrudRepository<Private, Long> {

}