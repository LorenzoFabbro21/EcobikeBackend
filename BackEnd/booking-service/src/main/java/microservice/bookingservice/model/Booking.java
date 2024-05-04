package microservice.bookingservice.model;

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
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "idPrivate")
    private long idPrivate;

    @Column(name = "idAnnouncement")
    private long idAnnouncement;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

    public Booking(long id, long idPrivate, long idAnnouncement, Date startdate, Date enddate) {
        this.id = id;
        this.idPrivate = idPrivate;
        this.idAnnouncement = idAnnouncement;
        this.startdate = startdate;
        this.enddate = enddate;
    }
}
