package microservice.shopservice.dto;

import lombok.Data;

@Data
public class Private {
    private long id;
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String phoneNumber;
    private Boolean googleCheck;
}
