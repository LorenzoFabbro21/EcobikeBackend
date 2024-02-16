package microservice.recensioneservice.dto;

import lombok.*;

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
