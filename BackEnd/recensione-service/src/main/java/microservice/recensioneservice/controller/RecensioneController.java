package microservice.recensioneservice.controller;

import java.awt.desktop.*;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.recensioneservice.dto.*;
import microservice.recensioneservice.model.Recensione;
import microservice.recensioneservice.service.RecensioneService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Slf4j
public class RecensioneController  {

    private final RecensioneService reviewService;

    @PostMapping(value = "")
    public ResponseEntity<?> postReview(@RequestBody Recensione review) {
        if(review == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return reviewService.saveReview(new Recensione(review.getText(), review.getScore(), review.getIdUser(), review.getIdShop()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create review");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Recensione>> getReview(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get review...");
            return reviewService.getReviewById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain review");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Recensione>> getAllReview() {
        try {
            System.out.println("Get all reviews...");
            List<Recensione> reviews;
            return reviewService.getAllReview();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review by id shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<User> getUserFromShop(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get user from review...");
            return reviewService.getUserFromReview(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review by id shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete review with ID = " + id + "...");
            return reviewService.deleteReview(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review by id shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllReviews() {
        try {
            System.out.println("Delete all reviews...");
            return reviewService.deleteAllReview();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review by id shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recensione> updateNegozio(@PathVariable("id") Long id, @RequestBody Recensione review) {
        if(id == null || review == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update shop with ID = " + id + "...");
            return reviewService.updateReview(id,review);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review by id shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/idShop/{idShop}")
    public ResponseEntity<List<Recensione>> getAllReviewByIdShop(@PathVariable("idShop") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return reviewService.getAllReviewByidShop(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review by id shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}