package microservice.recensioneservice.repo;

import lombok.*;
import microservice.recensioneservice.model.Recensione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long>{

}
