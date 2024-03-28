package microservice.recensioneservice.repo;

import lombok.*;
import microservice.recensioneservice.model.Recensione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long>{

    @Query("SELECT r FROM Recensione r WHERE " + " r.idShop = ?1")
    public List<Recensione> getAllReviewByidShop (long id);
}
