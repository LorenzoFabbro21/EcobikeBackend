package microservice.shopservice.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@Entity
@Data
@Table(name = "shop")
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "img")
    @Lob
    private String img;

    @Column(name = "idUser")
    private long idUser;

    public Shop( String name, String city, String address, String phoneNumber, String img, long idUser) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.img = img;
        this.idUser = idUser;
    }
}