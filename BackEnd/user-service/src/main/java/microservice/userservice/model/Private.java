package microservice.userservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class Private extends User{
    public Private() {
        super();
    }

    public Private(String name, String lastName, String mail, String password, String phoneNumber, Boolean googleCheck) {
        super(name, lastName, mail, password, phoneNumber, googleCheck);
    }
}
