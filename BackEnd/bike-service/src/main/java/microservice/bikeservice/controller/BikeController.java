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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Create a bike")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike created"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postBike(@RequestBody Bike bike) {
        System.out.println("New Bike:"+ bike);
        return bikeService.saveBike(new Bike(bike.getBrand(), bike.getModel(), bike.getSize(), bike.getType(), bike.getColor(), bike.getMeasure(), bike.getImg()));
    }
    @Operation(summary = "Get a bike by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bike found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bike not found")
    })
    @GetMapping("/{id}")
    public Optional<Bike> getBike(@PathVariable("id") long id) {

        System.out.println("Get bike...");
        Optional<Bike> bike;
        bike = bikeService.getBikeById(id);
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
