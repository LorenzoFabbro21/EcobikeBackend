package microservice.recensioneservice.service;

import lombok.*;
import microservice.recensioneservice.dto.*;
import microservice.recensioneservice.model.Recensione;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
public interface RecensioneService {

    ResponseEntity<?> saveReview(Recensione review);

    ResponseEntity<List<Recensione>> getAllReview();

    ResponseEntity<Optional<Recensione>> getReviewById(Long id);

    ResponseEntity<?> deleteReview(Long id);

    ResponseEntity<?> deleteAllReview();

    ResponseEntity<Recensione> updateReview(Long id, @RequestBody Recensione review);
    ResponseEntity<User> getUserFromReview(Long id);
    ResponseEntity<List<Recensione>> getAllReviewByidShop(Long id);
}
