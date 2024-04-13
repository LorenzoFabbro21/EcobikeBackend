package microservice.bikeservice.controller;


import microservice.bikeservice.dto.AdRent;
import microservice.bikeservice.dto.AdSell;
import microservice.bikeservice.model.Bike;
import microservice.bikeservice.model.BikeAdRentParam;
import microservice.bikeservice.model.BikeAdSellParam;
import microservice.bikeservice.rabbitMQ.RabbitMQSender;
import microservice.bikeservice.service.BikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private final RabbitMQSender rabbitMQSender;

    @PostMapping(value = "/sell")
    public ResponseEntity<?> postBikeSell(@RequestBody BikeAdSellParam param) {
        System.out.println("New Bike:"+ param);
        Bike bike = param.getBike();
        AdSell adSell = param.getAdSell();
        System.out.println(adSell);

        ResponseEntity<Map<String, String>> response = bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getMeasure(), bike.getImg()));

        Map<String, String> responseBody = response.getBody();

        String id = responseBody.get("id");
        System.out.println("idBike sopo save: " + id);
        adSell.setIdBike(Integer.parseInt(id));


        rabbitMQSender.sendAddBikeAd(adSell);
        System.out.println("print dopo sender");
        return ResponseEntity.status(HttpStatus.OK).body("Bike and AdSell successfully created");
    }


    /*@PostMapping(value = "/rent")
    public ResponseEntity<?> postBikeRent(@RequestBody BikeAdRentParam param) {
        System.out.println("New Bike:"+ param);
        Bike bike = param.getBike();
        AdRent adRent = param.getAdRent();

        ResponseEntity<?> idBike = bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getMeasure(), bike.getImg()));
        System.out.println("idBike sopo save: " + idBike);
        long id = Integer.parseInt(idBike.getBody().toString());
        adRent.setIdBike(id);


        rabbitMQSender.sendAddBikeAd(adRent);
        return ResponseEntity.status(HttpStatus.OK).body("Bike and AdSell successfully created");
    }*/

    @GetMapping("/{id}")
    public Optional<Bike> getBike(@PathVariable("id") long id) {

        System.out.println("Get bike...");
        Optional<Bike> bike;
        bike = bikeService.getBikeById(id);
        return bike;
    }


    @GetMapping("/brand/{brand}")
    public List<Bike> getSimilarBike(@PathVariable String brand) {

        System.out.println("Get similar bike to " + brand + "...");
        List<Bike> bike;
        bike = bikeService.getBikeByBrand(brand);
        System.out.println("return bike");
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
