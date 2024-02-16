package microservice.adservice.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
public class AdRent extends Ad {
    public AdRent() {
        super();
    }

    public AdRent(float price, long idBike) {
        super(price, idBike);
    }
}
