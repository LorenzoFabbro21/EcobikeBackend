package microservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class Private extends User{
    public Private() {
        super();
    }

    public Private(String nome, String cognome, String mail, String password, String telefono, Boolean googleCheck) {
        super(nome, cognome, mail, password, telefono, googleCheck);
    }
}
