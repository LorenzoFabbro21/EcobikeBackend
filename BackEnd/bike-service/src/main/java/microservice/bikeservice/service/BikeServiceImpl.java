package microservice.bikeservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.bikeservice.model.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import microservice.bikeservice.repo.BikeRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeServiceImpl implements BikeService{

    private final BikeRepository repository;


    @Override
    public ResponseEntity<?> saveBike(Bike bike) {
        try {
            Bike bikeCreated = repository.save(bike);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Bike has been created!");
            body.put("id", String.valueOf(bikeCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {

            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Override
    public List<Bike> getAllBikes() {
        List<Bike> bikes = new ArrayList<>();
        repository.findAll().forEach(bikes::add);
        return bikes;
    }

    @Override
    public Optional<Bike> getBikeById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<?> deleteBike(long id) {
        repository.deleteById(id);
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "Bike has been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @Override
    public ResponseEntity<?> deleteAllBikes() {
        repository.deleteAll();
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "All bikes have been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<Bike> updateBike(long id, Bike bike) {
        Optional<Bike> bikeData = repository.findById(id);

        if(bikeData.isPresent()) {
            Bike Bike = bikeData.get();
            Bike.setBrand(bike.getBrand());
            Bike.setModel(bike.getModel());
            Bike.setSize(bike.getSize());
            Bike.setType(bike.getType());
            Bike.setColor(bike.getColor());
            Bike.setMeasure(bike.getMeasure());
            Bike.setImg(bike.getImg());
            repository.save(Bike);
            return new ResponseEntity<>(repository.save(Bike), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public List<Bike> findFilterBike(String brand, String color, String size) {
        List<Bike> obj = repository.findFilterBike(brand,color,size);
        return obj;
    }
}
