package microservice.bikeservice.repo;

import microservice.bikeservice.model.Bike;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    @Query("SELECT b FROM Bike b WHERE " + "(?1 IS NULL OR b.brand = ?1) " + "AND (?2 IS NULL OR b.color = ?2) " + "AND (?3 IS NULL OR b.size = ?3)")
    public List<Bike> findFilterBike ( String brand,String color,String size);
}
