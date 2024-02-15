package microservice.shopservice.service;

import microservice.shopservice.model.Shop;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    Shop saveShop(Shop shop);

    List<Shop> getAllShops();

    Optional<Shop> getShopById(long id);

    ResponseEntity<String> deleteShop(long id);

    ResponseEntity<String> deleteAllShops();

    ResponseEntity<Shop> updateShop(long id, @RequestBody Shop shop);
}
