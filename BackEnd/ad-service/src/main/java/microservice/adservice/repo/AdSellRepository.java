package microservice.adservice.repo;

import microservice.adservice.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface AdSellRepository extends JpaRepository<AdSell, Long> {
}
