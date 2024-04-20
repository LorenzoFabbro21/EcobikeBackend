package microservice.bikeservice.dto;

import lombok.*;


@Data
public class AdSell {

    private float price;
    private long idBike;
    private long idUser;
}
