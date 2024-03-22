package microservice.adservice.repo;

import microservice.adservice.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRentRepository extends JpaRepository<AdRent, Long> {
}