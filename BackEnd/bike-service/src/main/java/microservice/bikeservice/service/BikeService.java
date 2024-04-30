package microservice.bikeservice.service;

import microservice.bikeservice.model.Bike;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BikeService {

    ResponseEntity<?> saveBike(Bike bike);

    ResponseEntity<List<Bike>> getAllBikes();

    ResponseEntity<Optional<Bike>> getBikeById(Long id);

    ResponseEntity<List<Bike>> getBikeByType(String type);

    ResponseEntity<?> deleteBike(Long id);

    ResponseEntity<?> deleteAllBikes();

    ResponseEntity<Bike> updateBike(Long id, @RequestBody Bike bike);

    ResponseEntity<List<Bike>> findFilterBike (String brand, String color, String size);
}
