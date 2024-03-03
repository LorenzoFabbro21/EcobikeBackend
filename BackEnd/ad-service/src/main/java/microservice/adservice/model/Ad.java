package microservice.adservice.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.*;

@Setter
@Getter
@Entity
@Data
@Table(name = "ad")
@NoArgsConstructor
public abstract class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "price")
    private float price;

    @Column(name = "idBike")
    private long idBike;

    public Ad(float price, long idBike) {
        this.price = price;
        this.idBike = idBike;
    }
}
