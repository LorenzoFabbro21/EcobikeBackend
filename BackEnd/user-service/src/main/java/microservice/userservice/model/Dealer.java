package microservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.*;
@Entity
public class Dealer extends User{
    public Dealer() {
        super();
    }

    public Dealer(String name, String lastName, String mail, String password, String phoneNumber, Boolean googleCheck) {
        super(name, lastName, mail, password, phoneNumber, googleCheck);
    }
}
