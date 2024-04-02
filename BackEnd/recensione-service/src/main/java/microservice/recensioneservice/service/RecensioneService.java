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

    List<Recensione> getAllReview();

    Optional<Recensione> getReviewById(long id);

    ResponseEntity<?> deleteReview(long id);

    ResponseEntity<?> deleteAllReview();

    ResponseEntity<Recensione> updateReview(long id, @RequestBody Recensione review);
    User getUserFromReview(long id);
    List<Recensione> getAllReviewByidShop(long id);
}
