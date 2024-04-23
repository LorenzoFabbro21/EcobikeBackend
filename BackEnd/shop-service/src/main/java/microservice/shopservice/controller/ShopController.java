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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.*;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;

    private final RabbitMQSender rabbitMQSender;

    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "Create a shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop created"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
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


    @Operation(summary = "Get a shop by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
    @GetMapping("/{id}")
    @Transactional
    public Optional<Shop> getShop(@PathVariable("id") long id) {

        System.out.println("Get shop...");
        Optional<Shop> shop;
        shop = shopService.getShopById(id);
        return shop;
    }

    @Operation(summary = "Get all shops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shops not found")
    })
    @GetMapping("")
    @Transactional
    public List<Shop> getAllShops() {

        System.out.println("Get all shops...");
        List<Shop> shop = new ArrayList<>();
        shop = shopService.getAllShops();
        return shop;
    }

    @Operation(summary = "Get all shops not yours by idDelear")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shops not found")
    })
    @GetMapping("/all/user/{id}")
    @Transactional
    public List<Shop> getAllShopsForUser(@PathVariable("id") long id) {

        System.out.println("Get all shops for User...");
        List<Shop> shop = new ArrayList<>();
        shop = shopService.getAllShopsForUser(id);
        return shop;
    }

    @Operation(summary = "Get an user by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}/user")
    public User getUserFromShop(@PathVariable("id") long id) {
        User user = new User();
        user = shopService.getUserFromShop(id);
        return user;
    }

    @Operation(summary = "Get a shop by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
    @GetMapping("/user/{id}")
    @Transactional
    public Optional<Shop> getShopFromUser(@PathVariable("id") long id) {
        return shopService.getShopFromUser(id);
    }


    @Operation(summary = "Delete a shop by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") long id) {

        System.out.println("Delete shop with ID = " + id + "...");
        return shopService.deleteShop(id);
    }

    @Operation(summary = "Delete all shops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shops not found")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllShops() {

        System.out.println("Delete All shops...");
        return shopService.deleteAllShops();

    }

    @Operation(summary = "update a shop by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop updated"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Shop not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable("id") long id, @RequestBody Shop shop) {

        System.out.println("Update shop with ID = " + id + "...");
        ResponseEntity<Shop> response = shopService.updateShop(id, shop);
        return response;
    }
}