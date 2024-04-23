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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @Operation(summary = "Create a bike")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike created"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping(value = "/sell")
    public ResponseEntity<?> postBikeSell(@RequestBody BikeAdSellParam param) {
        System.out.println("New Bike:"+ param);
        Bike bike = param.getBike();
        AdSell adSell = param.getAdSell();
        System.out.println(adSell);

        ResponseEntity<Map<String, String>> response = bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getMeasure(), bike.getImg()));

        Map<String, String> responseBody = response.getBody();

        String id = responseBody.get("id");
        System.out.println("idBike dopo save: " + id);
        adSell.setIdBike(Integer.parseInt(id));


        rabbitMQSender.sendAddBikeAdSell(adSell);
        System.out.println("print dopo sender");
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "Sell successfully created");
        return new ResponseEntity<Map>(body, HttpStatus.OK);
    }

    @PostMapping(value = "/rent")
    public ResponseEntity<?> postBikeRent(@RequestBody BikeAdRentParam param) {
        System.out.println("New Bike:"+ param);
        Bike bike = param.getBike();
        AdRent adRent = param.getAdRent();
        System.out.println(adRent);

        ResponseEntity<Map<String, String>> response = bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getMeasure(), bike.getImg()));

        Map<String, String> responseBody = response.getBody();

        String id = responseBody.get("id");
        System.out.println("idBike dopo save: " + id);
        adRent.setIdBike(Integer.parseInt(id));


        rabbitMQSender.sendAddBikeAdRent(adRent);
        System.out.println("print dopo sender");
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "Rent successfully created");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }



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


    @Operation(summary = "Get all bikes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bikes not found")
    })
    @GetMapping("")
    public List<Bike> getAllBikes() {

        System.out.println("Get all bikes...");
        List<Bike> bike = new ArrayList<>();
        bike = bikeService.getAllBikes();
        return bike;
    }

    @Operation(summary = "Get all bikes by filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes filtered found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
    })
    @GetMapping("/filter")
    public List<Bike> getBikesFilter(@RequestParam(name = "brand",required = false) String brand,
                                     @RequestParam(name="color",required = false) String color,
                                     @RequestParam(name="size",required = false) String size) {

        List<Bike> bikes = new ArrayList<>();
        bikes = bikeService.findFilterBike(brand,color,size);
        return bikes;
    }

    @Operation(summary = "Delete a bike by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bike not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBike(@PathVariable("id") long id) {

        System.out.println("Delete bike with ID = " + id + "...");
        return bikeService.deleteBike(id);
    }

    @Operation(summary = "Delete all bikes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bikes not found")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllBikes() {

        System.out.println("Delete All bikes...");
        return  bikeService.deleteAllBikes();

    }

    @Operation(summary = "Update a bike by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike updated"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bike not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Bike> updateBike(@PathVariable("id") long id, @RequestBody Bike bike) {

        System.out.println("Update bike with ID = " + id + "...");
        ResponseEntity<Bike> response = bikeService.updateBike(id, bike);
        return response;
    }
}
