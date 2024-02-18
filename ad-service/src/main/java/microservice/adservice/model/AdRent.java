package microservice.adservice.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
public class AdRent extends Ad {
    public AdRent() {
        super();
    }

    public AdRent(float price, long idBike) {
        super(price, idBike);
    }
}
