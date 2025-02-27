package microservice.reviewservice.service;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.reviewservice.dto.*;
import microservice.reviewservice.model.Review;
import microservice.reviewservice.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseEntity<?> saveReview(Review review) {
        if(review == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            String response = validateRequest(review);
            if (!response.equals("ok"))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

            Review reviewCreated = repository.save(review);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Review successfully created");
            body.put("id", String.valueOf(reviewCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post review");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }


    private String validateRequest(Review review) {
        String response = validateReviewParams(review);
        if (!response.equals("ok"))
            return response;
        return "ok";
    }

    private String validateReviewParams(Review review) {
        if (review.getText() == null || review.getText().isEmpty())
            return "Testo della recensione  inserito non valido";
        if (review.getScore() <= 0 || review.getScore() > 5)
            return "Punteggio inserito non valido";
        return "ok";
    }

    @Override
    public ResponseEntity<List<Review>> getAllReview() {
        try {
            List<Review> reviews = new ArrayList<>();
            repository.findAll().forEach(reviews::add);
            return ResponseEntity.status(HttpStatus.OK).body(reviews);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Optional<Review>> getReviewById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain review by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteReview(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Review has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete review");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteAllReview() {
        try {
            repository.deleteAll();
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "All reviews have been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all reviews");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Review> updateReview(Long id, Review review) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Review> ReviewData = repository.findById(id);

            if (ReviewData.isPresent()) {
                Review _review = ReviewData.get();
                _review.setText(review.getText());
                _review.setScore(review.getScore());
                _review.setIdUser(review.getIdUser());
                repository.save(_review);
                return new ResponseEntity<>(repository.save(_review), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update review");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<User> getUserFromReview(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Review> ReviewData = repository.findById(id);
            if (ReviewData.isPresent()) {
                Review _review = ReviewData.get();
                User dealer = restTemplate.getForObject("http://user-service:8081/api/dealer/" +_review.getIdUser(), User.class);
                if (dealer == null) {
                    User privateUser = restTemplate.getForObject("http://user-service:8081/api/private/" + _review.getIdUser(), User.class);
                    if (privateUser == null)
                        return ResponseEntity.status(HttpStatus.OK).body(null);
                    else
                        return ResponseEntity.status(HttpStatus.OK).body(privateUser);
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(dealer);
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain user from review");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Override
    public ResponseEntity<List<Review>> getAllReviewByidShop(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.getAllReviewByidShop(id));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all review by id shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}