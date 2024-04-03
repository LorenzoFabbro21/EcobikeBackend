package microservice.shopservice.controller;

import jakarta.transaction.*;
import microservice.shopservice.dto.User;
import microservice.shopservice.model.Shop;
import microservice.shopservice.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;

import javax.swing.text.html.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;

    @PostMapping(value = "")
    public ResponseEntity<?> postShop(@RequestBody Shop shop) {

        return shopService.saveShop(new Shop(shop.getName(), shop.getCity(), shop.getAddress(), shop.getPhoneNumber(), shop.getImg(), shop.getIdUser()));
    }

    @GetMapping("/{id}")
    @Transactional
    public Optional<Shop> getShop(@PathVariable("id") long id) {

        System.out.println("Get shop...");
        Optional<Shop> shop;
        shop = shopService.getShopById(id);
        return shop;
    }

    @GetMapping("")
    @Transactional
    public List<Shop> getAllShops() {

        System.out.println("Get all shops...");
        List<Shop> shop = new ArrayList<>();
        shop = shopService.getAllShops();
        return shop;
    }

    @GetMapping("/all/user/{id}")
    @Transactional
    public List<Shop> getAllShopsForUser(@PathVariable("id") long id) {

        System.out.println("Get all shops for User...");
        List<Shop> shop = new ArrayList<>();
        shop = shopService.getAllShopsForUser(id);
        return shop;
    }

    @GetMapping("/{id}/user")
    public User getUserFromShop(@PathVariable("id") long id) {
        User user = new User();
        user = shopService.getUserFromShop(id);
        return user;
    }
    @GetMapping("/user/{id}")
    @Transactional
    public Optional<Shop> getShopFromUser(@PathVariable("id") long id) {
        return shopService.getShopFromUser(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") long id) {

        System.out.println("Delete shop with ID = " + id + "...");
        return shopService.deleteShop(id);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAllShops() {

        System.out.println("Delete All shops...");
        return shopService.deleteAllShops();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable("id") long id, @RequestBody Shop shop) {

        System.out.println("Update shop with ID = " + id + "...");
        ResponseEntity<Shop> response = shopService.updateShop(id, shop);
        return response;
    }
}