package microservice.shopservice.repo;

import microservice.shopservice.model.Shop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByIdUser(long id);

    @Query("SELECT s FROM Shop s WHERE " + " s.idUser != ?1")
    public List<Shop> getAllShopForUser (long id);
}
