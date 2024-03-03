package microservice.recensioneservice.model;

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
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name = "score")
    private int score;
    @Column(name = "idUser")
    private int idUser; //Utente

    public Recensione(String text, int score, int idUser) {
        this.text = text;
        this.score = score;
        this.idUser = idUser;
    }

}