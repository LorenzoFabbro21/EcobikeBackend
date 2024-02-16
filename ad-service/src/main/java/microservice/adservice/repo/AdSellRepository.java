package microservice.adservice.repo;

import microservice.adservice.model.Ad;
import microservice.adservice.model.AdSell;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdSellRepository extends CrudRepository<AdSell, Long> {
}
