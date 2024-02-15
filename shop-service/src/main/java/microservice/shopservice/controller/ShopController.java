package microservice.shopservice.controller;

import microservice.shopservice.model.Shop;
import microservice.shopservice.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.extern.slf4j.*;

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
    public Shop getShop(@PathVariable("id") long id) {

        System.out.println("Get shop...");
        Shop shop = new Shop();
        if (shopService.getShopById(id).isPresent()) {
            return shop;
        }
        else return null;
    }

    @GetMapping("")
    public List<Shop> getAllShops() {

        System.out.println("Get all shops...");
        List<Shop> shop = new ArrayList<>();
        shop = shopService.getAllShops();
        return shop;
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