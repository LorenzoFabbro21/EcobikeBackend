package microservice.reviewservice.service;

import microservice.reviewservice.dto.*;
import microservice.reviewservice.model.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
public interface ReviewService {

    ResponseEntity<?> saveReview(Review review);

    ResponseEntity<List<Review>> getAllReview();

    ResponseEntity<Optional<Review>> getReviewById(Long id);

    ResponseEntity<?> deleteReview(Long id);

    ResponseEntity<?> deleteAllReview();

    ResponseEntity<Review> updateReview(Long id, @RequestBody Review review);
    ResponseEntity<User> getUserFromReview(Long id);
    ResponseEntity<List<Review>> getAllReviewByidShop(Long id);
}
