package microservice.recensioneservice.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.recensioneservice.model.Recensione;
import microservice.recensioneservice.repo.RecensioneRepository;
import microservice.recensioneservice.service.RecensioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Slf4j
public class RecensioneController  {

    private final RecensioneService reviewService;

    @PostMapping(value = "")
    public Recensione postReview(@RequestBody Recensione review) {
        Recensione _review = reviewService.saveReview(new Recensione(review.getText(), review.getScore(), review.getIdUser()));
        return _review;
    }

    @GetMapping("/{id}")
    public Recensione getReview(@PathVariable("id") long id) {
        System.out.println("Get review...");
        Recensione review = new Recensione();
        if (reviewService.getReviewById(id).isPresent())
            return review;
        else
            return null;
    }

    @GetMapping("")
    public List<Recensione> getAllReview() {
        System.out.println("Get all reviews...");
        List<Recensione> reviews = new ArrayList<>();
        reviews = reviewService.getAllReview();;
        return reviews;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") long id) {
        System.out.println("Delete review with ID = " + id + "...");
        ResponseEntity<String> response = reviewService.deleteReview(id);
        return response;
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllReviews() {
        System.out.println("Delete all reviews...");
        ResponseEntity<String> response = reviewService.deleteAllReview();
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recensione> updateNegozio(@PathVariable("id") long id, @RequestBody Recensione review) {
        System.out.println("Update shop with ID = " + id + "...");
        ResponseEntity<Recensione> response = reviewService.updateReview(id,review);
        return response;
    }
}