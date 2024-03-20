package microservice.userservice.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
@NoArgsConstructor
public class Booking {

    private long id;

    private int idPrivate;

    private int idAnnouncement;

    private Date startdate;

    private Date enddate;

}
