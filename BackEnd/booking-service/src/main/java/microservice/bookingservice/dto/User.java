package microservice.bookingservice.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class User {
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String phoneNumber;
    private Boolean googleCheck;

}
