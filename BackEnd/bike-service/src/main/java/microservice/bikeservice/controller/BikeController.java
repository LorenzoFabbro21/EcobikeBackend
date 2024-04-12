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

@CrossOrigin(origins = "http://localhost:32000")
@RestController
@RequestMapping("/api/bike")
@RequiredArgsConstructor
@Slf4j
public class BikeController {

    private final BikeService bikeService;

    @PostMapping(value = "")
    public ResponseEntity<?> postBike(@RequestBody Bike bike) {
        System.out.println("New Bike:"+ bike);
        return bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getMeasure(), bike.getImg()));
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

    @GetMapping("/filter")
    public List<Bike> getBikesFilter(@RequestParam(name = "brand",required = false) String brand,
                                     @RequestParam(name="color",required = false) String color,
                                     @RequestParam(name="size",required = false) String size) {

        List<Bike> bikes = new ArrayList<>();
        bikes = bikeService.findFilterBike(brand,color,size);
        return bikes;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBike(@PathVariable("id") long id) {

        System.out.println("Delete bike with ID = " + id + "...");
        return bikeService.deleteBike(id);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAllSBikes() {

        System.out.println("Delete All bikes...");
        return  bikeService.deleteAllBikes();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Bike> updateBike(@PathVariable("id") long id, @RequestBody Bike bike) {

        System.out.println("Update bike with ID = " + id + "...");
        ResponseEntity<Bike> response = bikeService.updateBike(id, bike);
        return response;
    }
}
