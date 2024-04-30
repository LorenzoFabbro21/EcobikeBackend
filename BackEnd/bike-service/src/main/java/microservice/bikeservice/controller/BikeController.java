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
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bike")
@RequiredArgsConstructor
@Slf4j
public class BikeController {

    private final BikeService bikeService;

    private final RabbitMQSender rabbitMQSender;

    @PostMapping(value = "/sell")
    public ResponseEntity<?> postBikeSell(@RequestBody BikeAdSellParam param) {
        if(param == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Bike bike = param.getBike();
            AdSell adSell = param.getAdSell();
            if (adSell.getPrice()<=0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Prezzo non valido");
            }
            ResponseEntity<?> response = bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getInfo(), bike.getImg()));

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, String> responseBody = (Map<String, String>) response.getBody();
                String id = responseBody.get("id");
                adSell.setIdBike(Integer.parseInt(id));

                // Invia l'annuncio di vendita alla coda RabbitMQ
                rabbitMQSender.sendAddBikeAdSell(adSell);

                // Prepara la risposta da inviare al client
                Map<String, String> body = new HashMap<>();
                body.put("messageResponse", "Sell successfully created");
                return ResponseEntity.ok(body);
            } else {
                return response;
            }
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/rent")
    public ResponseEntity<?> postBikeRent(@RequestBody BikeAdRentParam param) {
        if(param == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Bike bike = param.getBike();
            AdRent adRent = param.getAdRent();
            if (adRent.getPrice()<=0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Prezzo non valido");
            }
            ResponseEntity<?> response = bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getInfo(), bike.getImg()));

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, String> responseBody = (Map<String, String>) response.getBody();
                String id = responseBody.get("id");
                adRent.setIdBike(Integer.parseInt(id));

                // Invia l'annuncio di vendita alla coda RabbitMQ
                rabbitMQSender.sendAddBikeAdRent(adRent);

                // Prepara la risposta da inviare al client
                Map<String, String> body = new HashMap<>();
                body.put("messageResponse", "Rent successfully created");
                return ResponseEntity.ok(body);
            } else {
                return response;
            }
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bike>> getBike(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get bike...");
            return bikeService.getBikeById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Bike>> getSimilarBike(@PathVariable String brand) {
        if(brand == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get similar bike to " + brand + "...");
            return bikeService.getBikeByBrand(brand);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("")
    public ResponseEntity<List<Bike>> getAllBikes() {
        try {
            System.out.println("Get all bikes...");
            return bikeService.getAllBikes();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Bike>> getBikesFilter(@RequestParam(name = "brand",required = false) String brand,
                                     @RequestParam(name="color",required = false) String color,
                                     @RequestParam(name="size",required = false) String size) {
        try {
            return bikeService.findFilterBike(brand, color, size);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBike(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete bike with ID = " + id + "...");
            return bikeService.deleteBike(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAllSBikes() {
        try {
            System.out.println("Delete All bikes...");
            return  bikeService.deleteAllBikes();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bike> updateBike(@PathVariable("id") Long id, @RequestBody Bike bike) {
        if(id == null || bike == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update bike with ID = " + id + "...");
            return bikeService.updateBike(id, bike);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
