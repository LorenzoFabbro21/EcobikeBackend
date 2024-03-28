package microservice.userservice.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@Data
@NoArgsConstructor
public class Appointment {
    private long id;


    private int idUser;


    private int idAnnouncement;


    private Date date;
}
