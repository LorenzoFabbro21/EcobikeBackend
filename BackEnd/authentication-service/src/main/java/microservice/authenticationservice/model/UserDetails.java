package microservice.authenticationservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {

    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String phoneNumber;
    private Boolean googleCheck;
}
