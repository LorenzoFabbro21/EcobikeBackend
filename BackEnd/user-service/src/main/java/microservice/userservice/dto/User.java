package microservice.userservice.dto;

import lombok.Data;

@Data
public class User {
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String phoneNumber;
    private Boolean googleCheck;
}
