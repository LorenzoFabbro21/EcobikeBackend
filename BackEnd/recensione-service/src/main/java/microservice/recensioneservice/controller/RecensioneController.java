package microservice.recensioneservice.controller;

import java.awt.desktop.*;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.recensioneservice.dto.*;
import microservice.recensioneservice.model.Recensione;
import microservice.recensioneservice.service.RecensioneService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
@CrossOrigin(origins = "*")
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Slf4j
public class RecensioneController  {

    private final RecensioneService reviewService;

    @Operation(summary = "Create a new Review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postReview(@RequestBody Recensione review) {
        return reviewService.saveReview(new Recensione(review.getText(), review.getScore(), review.getIdUser(), review.getIdShop()));
    }
    @Operation(summary = "Get a Review by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @GetMapping("/{id}")
    public Optional<Recensione> getReview(@PathVariable("id") long id) {
        System.out.println("Get review...");
        Optional<Recensione> review;
        review = reviewService.getReviewById(id);
        return review;
    }

    @Operation(summary = "Get all Reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Reviews not found")
    })
    @GetMapping("")
    public List<Recensione> getAllReview() {
        System.out.println("Get all reviews...");

        List<Recensione> reviews;
        reviews = reviewService.getAllReview();
        System.out.println("Recensioni=" + reviews);
        return reviews;
    }

    @Operation(summary = "Get User by idReview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}/user")
    public User getUserFromShop(@PathVariable("id") long id) {
        System.out.println("Get user from review...");
        User user = new User();
        user = reviewService.getUserFromReview(id);
        return user;
    }

    @Operation(summary = "Delete a Review by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") long id) {
        System.out.println("Delete review with ID = " + id + "...");
        return  reviewService.deleteReview(id);
    }
    @Operation(summary = "Delete all Reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Reviews not found")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllReviews() {
        System.out.println("Delete all reviews...");
        return reviewService.deleteAllReview();

    }

    @Operation(summary = "Update review by idShop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Recensione> updateNegozio(@PathVariable("id") long id, @RequestBody Recensione review) {
        System.out.println("Update shop with ID = " + id + "...");
        return reviewService.updateReview(id,review);
    }

    @Operation(summary = "Get all Reviews by idShop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Reviews not found")
    })
    @GetMapping("/idShop/{idShop}")
    public List<Recensione> getAllReviewByidShop(@PathVariable("idShop") long id) {
        return reviewService.getAllReviewByidShop(id);
    }

}