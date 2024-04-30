package microservice.adservice.dto;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    private long id;
    private String brand;
    private String model;
    private String size;
    private String type;
    private String color;
    private String info;
    private String img;

}
