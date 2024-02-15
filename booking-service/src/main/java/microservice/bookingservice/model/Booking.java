package microservice.bookingservice.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "idPrivate")
    private int idPrivate;

    @Column(name = "idAnnouncement")
    private int idAnnouncement;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

    public Booking(long id, int idPrivate, int idAnnouncement, Date startdate, Date enddate) {
        this.id = id;
        this.idPrivate = idPrivate;
        this.idAnnouncement = idAnnouncement;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public Booking() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdPrivate() {
        return idPrivate;
    }

    public void setIdPrivate(int idPrivate) {
        this.idPrivate = idPrivate;
    }

    public int getIdAnnouncement() {
        return idAnnouncement;
    }

    public void setIdAnnouncement(int idAnnouncement) {
        this.idAnnouncement = idAnnouncement;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
