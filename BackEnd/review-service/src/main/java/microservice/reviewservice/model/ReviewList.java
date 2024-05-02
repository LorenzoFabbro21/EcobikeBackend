package microservice.reviewservice.model;

import java.util.List;

public class ReviewList {

    private List<Review> reviews;

    public ReviewList(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}