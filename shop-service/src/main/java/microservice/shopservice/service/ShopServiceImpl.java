package microservice.shopservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.shopservice.model.Shop;
import microservice.shopservice.repo.ShopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository repository;

    @Override
    public Shop saveShop(Shop shop) {
        return repository.save(shop);
    }

    @Override
    public List<Shop> getAllShops() {
        List<Shop> shops = new ArrayList<>();
        repository.findAll().forEach(shops::add);
        return shops;
    }

    @Override
    public Optional<Shop> getShopById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<String> deleteShop(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Shop has been deleted!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteAllShops() {
        repository.deleteAll();
        return new ResponseEntity<>("All shops have been deleted!", HttpStatus.OK);
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
}