package microservice.recensioneservice.repo;

import lombok.*;
import microservice.recensioneservice.model.Recensione;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;

@Repository
public interface RecensioneRepository extends CrudRepository<Recensione, Long>{

}
