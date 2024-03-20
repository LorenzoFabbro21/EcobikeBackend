package microservice.bookingservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class Bike {
    private long id;

    private String brand;

    private String model;

    private String size;

    private String type;

    private String color;

    private String measure;

    private String img;
}
