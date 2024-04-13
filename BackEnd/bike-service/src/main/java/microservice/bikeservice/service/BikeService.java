package microservice.bikeservice.service;

import microservice.bikeservice.model.Bike;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BikeService {

    ResponseEntity<Map<String, String>> saveBike(Bike bike);

    List<Bike> getAllBikes();

    Optional<Bike> getBikeById(long id);

    List<Bike> getBikeByBrand(String brand);

    ResponseEntity<?> deleteBike(long id);

    ResponseEntity<?> deleteAllBikes();

    ResponseEntity<Bike> updateBike(long id, @RequestBody Bike bike);

    List<Bike> findFilterBike ( String brand,String color, String size);
}
