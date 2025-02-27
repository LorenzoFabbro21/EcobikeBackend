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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.*;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.client.RestTemplate;


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
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postShop(@RequestBody ShopParam param) {
        if(param == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Post shop...");
            Shop shop = param.getShop();
            Private user = param.getUser();
            String res = shopService.validateRequest(shop);
            if (!res.equals("ok"))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
            else {
                rabbitMQSender.sendDeleteUser(user);
                rabbitMQSender.sendCreateDealer(user);
                User d = restTemplate.getForObject("http://user-service/api/dealer/email/" + user.getMail(), User.class);
                shop.setIdUser(d.getId());
                ResponseEntity<?> response = shopService.saveShop(shop);
                if(response.getStatusCode() == HttpStatus.OK) {
                    User dealer = restTemplate.getForObject("http://user-service/api/dealer/" + shop.getIdUser(), User.class);
                    ResponseEntity<User> r = ResponseEntity.status(HttpStatus.OK).body(dealer);
                    return r;
                } else
                    return response;
            }
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Get a shop by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Shop not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Optional<Shop>> getShop(@PathVariable("id") Long id) {
        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get shop...");
            return shopService.getShopById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all shops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Shops not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    @Transactional
    public ResponseEntity<List<Shop>> getAllShops() {
        try {
            System.out.println("Get all shops...");
            return shopService.getAllShops();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all shops");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get all shops not yours by idDelear")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Shops not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all/user/{id}")
    @Transactional
    public ResponseEntity<List<Shop>> getAllShopsForUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all shops for User...");
            return shopService.getAllShopsForUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all shops for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get an user by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/user")
    public ResponseEntity<User> getUserFromShop(@PathVariable("id") Long id) {
        if (id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get User from Shop...");
            return shopService.getUserFromShop(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain user from shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get a shop by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Shop not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Transactional
    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<Shop>> getShopFromUser(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get Shop from user...");
            return shopService.getShopFromUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain shop from user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Operation(summary = "Delete a shop by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Shop not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete shop with ID = " + id + "...");
            return shopService.deleteShop(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete all shops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Shops not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllShops() {
        try {
            System.out.println("Delete All shops...");
            return shopService.deleteAllShops();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all shops");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "update a shop by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Shop not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable("id") Long id, @RequestBody Shop shop) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update shop with ID = " + id + "...");
            return shopService.updateShop(id, shop);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}