package microservice.shopservice.repo;

import microservice.shopservice.model.Shop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByIdUser(long id);
}
