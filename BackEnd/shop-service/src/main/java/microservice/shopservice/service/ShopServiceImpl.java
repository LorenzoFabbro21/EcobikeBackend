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
        try {
            Shop reviewCreated = repository.save(shop);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Shop successfully created");
            body.put("id", String.valueOf(reviewCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {

            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }

    }

    @Override
    @Transactional
    public List<Shop> getAllShops() {
        List<Shop> shops = new ArrayList<>();
        repository.findAll().forEach(shops::add);
        return shops;
    }

    @Override
    @Transactional
    public Optional<Shop> getShopById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<?> deleteShop(long id) {
        repository.deleteById(id);
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "Shop has been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @Override
    public ResponseEntity<?> deleteAllShops() {
        repository.deleteAll();
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "All shops have been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);

    }

    @Override
    public ResponseEntity<Shop> updateShop(long id, Shop shop) {
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
    }


    @Override
    @Transactional
    public User getUserFromShop(long id) {
        Optional<Shop> shopData = repository.findById(id);
        if (shopData.isPresent()) {
            Shop _shop = shopData.get();
            User dealer = restTemplate.getForObject("http://user-service/api/dealer/" + _shop.getIdUser(), User.class);
            if (dealer == null) {
                User privateUser = restTemplate.getForObject("http://user-service/api/private/" + _shop.getIdUser(), User.class);
                if (privateUser == null) {
                    return null;
                } else {
                    return privateUser;
                }
            } else {

                return dealer;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public Optional<Shop> getShopFromUser(long id) {
        return repository.findByIdUser(id);
    }
}