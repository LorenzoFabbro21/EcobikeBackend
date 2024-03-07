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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeServiceImpl implements BikeService{

    private final BikeRepository repository;


    @Override
    public Bike saveBike(Bike bike) {
        return repository.save(bike);
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
    public ResponseEntity<String> deleteBike(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Bike has been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAllBikes() {
        repository.deleteAll();
        return new ResponseEntity<>("All bikes have been deleted!", HttpStatus.OK);
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
