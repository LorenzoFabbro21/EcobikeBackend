package microservice.shopservice.service;

import microservice.shopservice.dto.User;
import microservice.shopservice.model.Shop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    ResponseEntity<?> saveShop(Shop shop);

    List<Shop> getAllShops();

    Optional<Shop> getShopById(long id);

    ResponseEntity<?> deleteShop(long id);

    ResponseEntity<?> deleteAllShops();

    ResponseEntity<Shop> updateShop(long id, @RequestBody Shop shop);

    User getUserFromShop(long id);

    Optional<Shop> getShopFromUser(long id);
}
