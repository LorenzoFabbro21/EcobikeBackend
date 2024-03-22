package microservice.appointmentservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class User {
    private long id;

    private String nome;

    private String cognome;

    private String mail;

    private int telefono;

    private String password;
}
