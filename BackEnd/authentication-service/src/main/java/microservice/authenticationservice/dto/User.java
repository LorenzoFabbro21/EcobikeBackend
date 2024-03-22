package microservice.authenticationservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String nome;
    private String cognome;
    private String mail;
    private String password;
    private String telefono;
    private Boolean googleCheck;
}
