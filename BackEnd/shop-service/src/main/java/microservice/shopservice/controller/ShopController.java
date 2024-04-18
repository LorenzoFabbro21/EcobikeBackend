package microservice.shopservice.controller;

import jakarta.transaction.*;
import microservice.shopservice.dto.Private;
import microservice.shopservice.dto.ShopParam;
import microservice.shopservice.dto.User;
import microservice.shopservice.model.Shop;
import microservice.shopservice.rabbitMQ.RabbitMQSender;
import microservice.shopservice.repo.ShopRepository;
import microservice.shopservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;

    private final RabbitMQSender rabbitMQSender;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "")
    public User postShop(@RequestBody ShopParam param) {
        System.out.println("New Shop:"+ param);
        Shop shop = param.getShop();
        Private user = param.getUser();
        System.out.println("111111111111111111111111111111111111");
        rabbitMQSender.sendDeleteUser(user);
        System.out.println("22222222222222222222222222222222222222");
        rabbitMQSender.sendCreateDealer(user);
        User d = restTemplate.getForObject("http://user-service/api/dealer/email/" + user.getMail(), User.class);
        System.out.println("dealerererrere: " + d);

        //Impostare idUser in shop = idDealer appena creato
        shop.setIdUser(d.getId());


        shopService.saveShop(shop);
        System.out.println("444444444444444444444444444444444");
        User dealer = restTemplate.getForObject("http://user-service/api/dealer/" + shop.getIdUser(), User.class);
        System.out.println("5555555555555555555555555555555");
        return dealer;
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