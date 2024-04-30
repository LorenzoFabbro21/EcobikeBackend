package microservice.shopservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.shopservice.dto.User;
import microservice.shopservice.model.Shop;
import microservice.shopservice.repo.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> saveShop(Shop shop) {
        if(shop == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Shop s = repository.save(shop);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Shop successfully created");
            body.put("id", String.valueOf(s.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create a shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }


    public String validateRequest(Shop shop) {
        String response = validateShopParams(shop);
        if (!response.equals("ok"))
            return response;
        return "ok";
    }


    private String validateShopParams(Shop shop) {
        if (shop.getName() == null || shop.getName().isEmpty()) {
            System.out.println("Nome inserito non valido");
            return "Nome inserito non valido";
        }
        if (shop.getCity() == null || shop.getCity().isEmpty()) {
            System.out.println("Città inserita non valida");
            return "Città inserita non valida";
        }
        if (shop.getAddress() == null || shop.getAddress().isEmpty()) {
            System.out.println("Indirizzo inserito non valido");
            return "Indirizzo inserito non valido";
        }
        if (shop.getPhoneNumber().length() < 9) {
            System.out.println("Numero di telefono non valido");
            return "Numero di telefono non valido";
        }
        return "ok";
    }

    @Override
    @Transactional
    public ResponseEntity<List<Shop>> getAllShops() {
        try {
            List<Shop> shops = new ArrayList<>();
            repository.findAll().forEach(shops::add);
            return ResponseEntity.status(HttpStatus.OK).body(shops);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all shops");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<Shop>> getAllShopsForUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<Shop> shops = new ArrayList<>();
            repository.getAllShopForUser(id).forEach(shops::add);
            return ResponseEntity.status(HttpStatus.OK).body(shops);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all shops for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Optional<Shop>> getShopById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Shop> shop = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(shop);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain shop by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteShop(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Shop has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteAllShops() {
        try {
            repository.deleteAll();
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "All shops have been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all shops");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Shop> updateShop(Long id, Shop shop) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Shop> shopData = repository.findById(id);
            if(shopData.isPresent()) {
                Shop Shop = shopData.get();
                Shop.setName(shop.getName());
                Shop.setCity(shop.getCity());
                Shop.setAddress(shop.getAddress());
                Shop.setPhoneNumber(shop.getPhoneNumber());
                Shop.setIdUser(shop.getIdUser());
                repository.save(Shop);
                return new ResponseEntity<>(repository.save(Shop), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<User> getUserFromShop(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Shop> shopData = repository.findById(id);
            if (shopData.isPresent()) {
                Shop _shop = shopData.get();
                User dealer = restTemplate.getForObject("http://user-service:8081/api/dealer/" + _shop.getIdUser(), User.class);
                if (dealer == null) {
                    User privateUser = restTemplate.getForObject("http://user-service:8081/api/private/" + _shop.getIdUser(), User.class);
                    if (privateUser == null)
                        return null;
                    else
                        return ResponseEntity.status(HttpStatus.OK).body(privateUser);
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(dealer);
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain user form shop");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Optional<Shop>> getShopFromUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Shop> shop = repository.findByIdUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(shop);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain shop from user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}