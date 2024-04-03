package microservice.adservice.repo;

import microservice.adservice.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRentRepository extends JpaRepository<AdRent, Long> {
    @Query("SELECT b FROM AdRent b WHERE " + " b.idUser = ?1")
    public List<AdRent> getAllRentByUser (long id);

    @Query("SELECT b FROM AdRent b WHERE " + " b.idUser != ?1")
    public List<AdRent> getAllAdRentForUser (long id);
}