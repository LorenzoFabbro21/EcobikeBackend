package microservice.bikeservice.service;

import microservice.bikeservice.model.Bike;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface BikeService {

    Bike saveBike(Bike bike);

    List<Bike> getAllBikes();

    Optional<Bike> getBikeById(long id);

    ResponseEntity<String> deleteBike(long id);

    ResponseEntity<String> deleteAllBikes();

    ResponseEntity<Bike> updateBike(long id, @RequestBody Bike bike);
}
