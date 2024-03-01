package microservice.bikeservice.controller;


import microservice.bikeservice.model.Bike;
import microservice.bikeservice.service.BikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bike")
@RequiredArgsConstructor
@Slf4j
public class BikeController {

    private final BikeService bikeService;
/*
    @PostMapping(value = "")
    public Bike postBike(@RequestBody Bike bike) {
        System.out.println("New Bike:"+ bike);
        Bike _Bike = bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getMeasure(), bike.getImg()));
        System.out.println("returned Bike:"+ bike);
        return _Bike;
    }
    */
@PostMapping(value = "")
public ResponseEntity<Bike> postBike(@RequestParam("img") MultipartFile img,
                                     @RequestParam("brand") String brand,
                                     @RequestParam("model") String model,
                                     @RequestParam("size") String size,
                                     @RequestParam("type") String type,
                                     @RequestParam("color") String color,
                                     @RequestParam("measure") String measure) {
    // Processa l'immagine e altri dati come necessario
    // Ad esempio, salvali su disco o nel database
    try {
        byte[] imageBytes = img.getBytes();
        Bike bike = new Bike(brand, model, size, type, color, measure, imageBytes);
        Bike savedBike = bikeService.saveBike(bike);
        return ResponseEntity.ok(savedBike);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    @GetMapping("/{id}")
    public Optional<Bike> getBike(@PathVariable("id") long id) {

        System.out.println("Get bike...");
        Optional<Bike> bike;
        bike = bikeService.getBikeById(id);
        return bike;
    }

    @GetMapping("")
    public List<Bike> getAllBikes() {

        System.out.println("Get all bikes...");
        List<Bike> bike = new ArrayList<>();
        bike = bikeService.getAllBikes();
        return bike;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBike(@PathVariable("id") long id) {

        System.out.println("Delete bike with ID = " + id + "...");
        ResponseEntity<String> response = bikeService.deleteBike(id);
        return response;
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAllSBikes() {

        System.out.println("Delete All bikes...");
        ResponseEntity<String> response = bikeService.deleteAllBikes();
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bike> updateBike(@PathVariable("id") long id, @RequestBody Bike bike) {

        System.out.println("Update bike with ID = " + id + "...");
        ResponseEntity<Bike> response = bikeService.updateBike(id, bike);
        return response;
    }
}
