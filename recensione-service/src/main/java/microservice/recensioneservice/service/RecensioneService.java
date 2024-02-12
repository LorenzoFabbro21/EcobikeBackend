package microservice.recensioneservice.service;

import lombok.*;
import microservice.recensioneservice.model.Recensione;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
public interface RecensioneService {

    Recensione saveReview(Recensione review);

    List<Recensione> getAllReview();

    Optional<Recensione> getReviewById(long id);

    ResponseEntity<String> deleteReview(long id);

    ResponseEntity<String> deleteAllReview();

    ResponseEntity<Recensione> updateReview(long id, @RequestBody Recensione review);
}
