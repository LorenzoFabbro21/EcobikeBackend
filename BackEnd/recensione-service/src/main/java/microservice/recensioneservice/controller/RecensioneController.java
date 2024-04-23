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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Slf4j
public class RecensioneController  {

    private final RecensioneService reviewService;

    @PostMapping(value = "")
    public ResponseEntity<?> postReview(@RequestBody Recensione review) {
        return reviewService.saveReview(new Recensione(review.getText(), review.getScore(), review.getIdUser(), review.getIdShop()));
    }

    @GetMapping("/{id}")
    public Optional<Recensione> getReview(@PathVariable("id") long id) {
        System.out.println("Get review...");
        Optional<Recensione> review;
        review = reviewService.getReviewById(id);
        return review;
    }

    @GetMapping("")
    public List<Recensione> getAllReview() {
        System.out.println("Get all reviews...");

        List<Recensione> reviews;
        reviews = reviewService.getAllReview();
        System.out.println("Recensioni=" + reviews);
        return reviews;
    }

    @GetMapping("/{id}/user")
    public User getUserFromShop(@PathVariable("id") long id) {
        System.out.println("Get user from review...");
        User user = new User();
        user = reviewService.getUserFromReview(id);
        return user;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") long id) {
        System.out.println("Delete review with ID = " + id + "...");
        return  reviewService.deleteReview(id);
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllReviews() {
        System.out.println("Delete all reviews...");
        return reviewService.deleteAllReview();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Recensione> updateNegozio(@PathVariable("id") long id, @RequestBody Recensione review) {
        System.out.println("Update shop with ID = " + id + "...");
        return reviewService.updateReview(id,review);
    }

    @GetMapping("/idShop/{idShop}")
    public List<Recensione> getAllReviewByidShop(@PathVariable("idShop") long id) {
        return reviewService.getAllReviewByidShop(id);
    }

}