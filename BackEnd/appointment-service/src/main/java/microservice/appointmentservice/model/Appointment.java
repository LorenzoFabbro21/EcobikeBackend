package microservice.appointmentservice.model;

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
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "idUser")
    private int idUser;

    @Column(name = "idAnnouncement")
    private int idAnnouncement;

    @Column(name = "date")
    private Date date;

    public Appointment(long id, int idUser, int idAnnouncement, Date date) {
        this.id = id;
        this.idUser = idUser;
        this.idAnnouncement = idAnnouncement;
        this.date = date;
    }
}
