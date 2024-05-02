package microservice.reviewservice.repo;

import microservice.reviewservice.model.Review;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    @Query("SELECT r FROM Review r WHERE " + " r.idShop = ?1")
    public List<Review> getAllReviewByidShop (long id);
}
