package microservice.authenticationservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {

    private String nome;
    private String cognome;
    private String mail;
    private String password;
    private String telefono;
    private Boolean googleCheck;
}
