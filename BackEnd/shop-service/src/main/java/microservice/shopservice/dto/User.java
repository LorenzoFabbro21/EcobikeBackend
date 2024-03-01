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
    private String nome;
    private String cognome;
    private String mail;
    private String password;
    private int telefono;
}
