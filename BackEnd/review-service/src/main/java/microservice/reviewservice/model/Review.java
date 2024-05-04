package microservice.reviewservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Data
@Table(name = "review")
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name = "score")
    private int score;
    @Column(name = "idUser")
    private long idUser; //Utente
    @Column(name = "idShop")
    private long idShop;

    public Review(String text, int score, long idUser, long idShop) {
        this.text = text;
        this.score = score;
        this.idUser = idUser;
        this.idShop = idShop;
    }

}