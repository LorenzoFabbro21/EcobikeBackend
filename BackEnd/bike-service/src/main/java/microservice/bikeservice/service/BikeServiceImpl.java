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
    public ResponseEntity<Map<String, String>> saveBike(Bike bike) {
        if(bike == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            String response = validateRequest(bike);
            if (!response.equals("ok")) {
                Map<String, String> errorBody = new HashMap<>();
                errorBody.put("error", response);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
            }

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

    private String validateRequest(Bike bike) {
        String response = validateBikeParams(bike);
        if (!response.equals("ok"))
            return response;
        return "ok";
    }

    private String validateBikeParams(Bike bike) {
        if (bike.getBrand() == null || bike.getBrand().isEmpty())
            return "Marca inserita non valida";
        if (bike.getModel() == null || bike.getModel().isEmpty())
            return "Modello inserito non valido";
        if (bike.getColor() == null || bike.getColor().isEmpty())
            return "Colore inserito non valido";
        if (bike.getSize() == null || bike.getSize().isEmpty())
            return "Taglia inserita non valida";
        if (bike.getType() == null || bike.getType().isEmpty())
            return "Tipo inserito non valido";
        if (bike.getMeasure() == null || bike.getMeasure().isEmpty())
            return "Misure inserite non valide";
        return "ok";
    }

    @Override
    public ResponseEntity<List<Bike>> getAllBikes() {
        try {
            List<Bike> bikes = new ArrayList<>();
            repository.findAll().forEach(bikes::add);
            return ResponseEntity.status(HttpStatus.OK).body(bikes);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Optional<Bike>> getBikeById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<Bike>> getBikeByBrand(String brand) {
        if(brand == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findBikeByBrand(brand));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteBike(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Bike has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteAllBikes() {
        try {
            repository.deleteAll();
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "All bikes have been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Bike> updateBike(Long id, Bike bike) {
        if(id == null || bike == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
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
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<Bike>> findFilterBike(String brand, String color, String size) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findFilterBike(brand, color, size));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}