package microservice.bikeservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Setter
@Getter
@Entity
@Data
@Table(name = "bike")
@NoArgsConstructor
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "size")
    private String size;

    @Column(name = "type")
    private String type;

    @Column(name = "color")
    private String color;

    @Column(name = "measure")
    private String measure;

    @Column(name = "img")
    @Lob
    private String img;

    public Bike(String brand, String model, String size, String type, String color, String measure, String img) {
        this.brand = brand;
        this.model = model;
        this.size = size;
        this.type = type;
        this.color = color;
        this.measure = measure;
        this.img = img;
    }
}
