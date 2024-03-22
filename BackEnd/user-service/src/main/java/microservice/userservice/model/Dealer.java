package microservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.*;
@Entity
public class Dealer extends User{
    public Dealer() {
        super();
    }

    public Dealer(String nome, String cognome, String mail, String password, String telefono, Boolean googleCheck) {
        super(nome, cognome, mail, password, telefono, googleCheck);
    }
}
