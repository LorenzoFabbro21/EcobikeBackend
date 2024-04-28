package microservice.bikeservice.dto;

import lombok.Data;

@Data
public class AdRent {

    private float price;
    private long idBike;
    private long idUser;
}
