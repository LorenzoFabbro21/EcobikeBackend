package microservice.shopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import microservice.shopservice.model.Shop;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopUserResponse {
    private Shop shop;
    private User user;
}
