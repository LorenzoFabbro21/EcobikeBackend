package microservice.bikeservice.model;

import lombok.Data;
import microservice.bikeservice.dto.AdSell;

@Data
public class BikeAdSellParam {
    Bike bike;
    AdSell adSell;
}
