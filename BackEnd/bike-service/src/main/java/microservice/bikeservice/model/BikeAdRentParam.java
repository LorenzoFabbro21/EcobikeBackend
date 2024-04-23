package microservice.bikeservice.model;

import lombok.Data;
import microservice.bikeservice.dto.AdRent;

@Data
public class BikeAdRentParam {
    Bike bike;
    AdRent adRent;
}
