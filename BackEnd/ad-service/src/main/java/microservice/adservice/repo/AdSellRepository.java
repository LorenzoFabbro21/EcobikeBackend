package microservice.adservice.repo;

import microservice.adservice.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdSellRepository extends CrudRepository<AdSell, Long> {
    @Query("SELECT b FROM AdSell b WHERE " + " b.idUser = ?1")
    public List<AdSell> getAllAdSellByUser (long id);
}
