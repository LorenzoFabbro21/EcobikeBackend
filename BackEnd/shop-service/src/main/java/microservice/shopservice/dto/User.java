package microservice.shopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String phoneNumber;
    private Boolean googleCheck;
}
