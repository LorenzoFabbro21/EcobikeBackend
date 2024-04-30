package microservice.shopservice.service;

import microservice.shopservice.dto.User;
import microservice.shopservice.model.Shop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    ResponseEntity<?> saveShop(Shop shop);

    ResponseEntity<List<Shop>> getAllShops();

    ResponseEntity<List<Shop>> getAllShopsForUser(Long id);

    ResponseEntity<Optional<Shop>> getShopById(Long id);

    ResponseEntity<?> deleteShop(Long id);

    ResponseEntity<?> deleteAllShops();

    ResponseEntity<Shop> updateShop(Long id, @RequestBody Shop shop);

    ResponseEntity<User> getUserFromShop(Long id);

    ResponseEntity<Optional<Shop>> getShopFromUser(Long id);

    String validateRequest(Shop shop);
}
