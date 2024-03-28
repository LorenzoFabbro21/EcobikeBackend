package microservice.bookingservice.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class Adrent {
    private long id;

    private float price;

    private long idBike;

    private long idUser;
}
