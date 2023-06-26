package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Shop;
import ee.mihkel.webshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopController {
    @Autowired
    ShopRepository shopRepository;

    @GetMapping("shop")
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @PostMapping("shop/add")
    public List<Shop> addShop(@RequestBody Shop shop) {
        if (shop.getId() == null || !shopRepository.existsById(shop.getId()))
            shopRepository.save(shop);
        return shopRepository.findAll();
    }

    @PutMapping("shop/edit")
    public List<Shop> editShop(@RequestBody Shop shop) {
        if (shopRepository.existsById(shop.getId())) {
            shopRepository.save(shop);
        }
        return shopRepository.findAll();
    }
    @DeleteMapping("shop/delete/{id}")
    public List<Shop>deleteShop(@PathVariable Long id){
        shopRepository.deleteById(id);
        return shopRepository.findAll();
    }
}
