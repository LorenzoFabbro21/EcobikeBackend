package microservice.reviewservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String phoneNumber;
    private Boolean googleCheck;
}
