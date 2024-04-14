package microservice.shopservice.dto;

import lombok.Data;
import microservice.shopservice.model.Shop;

@Data
public class ShopParam {
    Shop shop;
    User user;
    String token;
}
