package microservice.userservice.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.*;

@Setter
@Getter
@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "googleCheck")
    private Boolean googleCheck;

    public User(String name, String lastName, String mail, String password, String phoneNumber, Boolean googleCheck) {

        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.googleCheck = googleCheck;
    }
}
