package microservice.adservice.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private long id;
    private long idUser;
    private Date date;
    private long idAnnouncement;
}

