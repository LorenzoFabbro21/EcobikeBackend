package microservice.recensioneservice.model;

import java.util.List;

public class RecensioneList {

    private List<Recensione> reviews;

    public RecensioneList(List<Recensione> reviews) {
        this.reviews = reviews;
    }

    public List<Recensione> getReviews() {
        return reviews;
    }

    public void setReviews(List<Recensione> reviews) {
        this.reviews = reviews;
    }
}