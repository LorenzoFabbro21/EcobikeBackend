package microservice.shopservice.controller;

import microservice.shopservice.dto.User;
import microservice.shopservice.model.Shop;
import microservice.shopservice.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;

import javax.swing.text.html.*;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;

    @PostMapping(value = "")
    public Shop postShop(@RequestBody Shop shop) {

        Shop _Shop = shopService.saveShop(new Shop(shop.getName(), shop.getCity(), shop.getAddress(), shop.getPhoneNumber(), shop.getIdUser()));
        return _Shop;
    }

    @GetMapping("/{id}")
    public Optional<Shop> getShop(@PathVariable("id") long id) {

        System.out.println("Get shop...");
        Optional<Shop> shop;
        shop = shopService.getShopById(id);
        return shop;
    }

    @GetMapping("")
    public List<Shop> getAllShops() {

        System.out.println("Get all shops...");
        List<Shop> shop = new ArrayList<>();
        shop = shopService.getAllShops();
        return shop;
    }

    @GetMapping("/{id}/user")
    public User getUserFromShop(@PathVariable("id") long id) {
        System.out.println("Get user from review...");
        User user = new User();
        user = shopService.getUserFromShop(id);
        return user;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable("id") long id) {

        System.out.println("Delete shop with ID = " + id + "...");
        ResponseEntity<String> response = shopService.deleteShop(id);
        return response;
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAllShops() {

        System.out.println("Delete All shops...");
        ResponseEntity<String> response = shopService.deleteAllShops();
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable("id") long id, @RequestBody Shop shop) {

        System.out.println("Update shop with ID = " + id + "...");
        ResponseEntity<Shop> response = shopService.updateShop(id, shop);
        return response;
    }
}