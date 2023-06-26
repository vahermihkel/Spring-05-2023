package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ProductController {

    @Autowired
    ProductCache productCache;

    @Autowired
    ProductRepository productRepository;


    // GET localhost:8080/product
    @GetMapping("product")
    public List<Product> getProducts() {
        return productRepository.findAllByOrderById();
    }

    // DELETE localhost:8080/product/1
    @DeleteMapping("product/{id}")
    public List<Product> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        productCache.emptyCache();
        return productRepository.findAll();
    }

    // GET localhost:8080/product/1
    @GetMapping("product/{id}")
    public Product getProduct(@PathVariable Long id) throws ExecutionException {
        return productCache.getProduct(id);
    }

    // POST localhost:8080/product
    @PostMapping("product")
    public List<Product> addProduct(@RequestBody Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            productRepository.save(product);
        }
        return productRepository.findAll();
    }

    // PUT localhost:8080/product
    @PutMapping("product")
    public List<Product> editProduct(@RequestBody Product product) {
        if (productRepository.existsById(product.getId())) {
            productRepository.save(product);
            productCache.updateProduct(product.getId());
        }
        return productRepository.findAllByOrderById();
    }

    // PATCH localhost:8080/decrease-stock/1
    @PatchMapping("decrease-stock/{id}")
    public List<Product> decreaseStock(@PathVariable Long id) {
        Product product = productRepository.findById(id).get();
        if (product.getStock() > 0) {
            product.setStock(product.getStock()-1);
            productRepository.save(product);
            productCache.updateProduct(product.getId());
        }
        return productRepository.findAllByOrderById();
    }

    // PATCH localhost:8080/increase-stock/1
    @PatchMapping("increase-stock/{id}")
    public List<Product> increaseStock(@PathVariable Long id) {
        Product product = productRepository.findById(id).get();
        product.setStock(product.getStock()+1);
        productRepository.save(product);
        productCache.updateProduct(product.getId());
        return productRepository.findAllByOrderById();
    }

    // GET localhost:8080/products-by-price
    @GetMapping("products-by-price")
    public List<Product> getProductsByPrice() {
        return productRepository.findAllByOrderByPrice();
    }

    // GET localhost:8080/highest-price-product
    @GetMapping("highest-price-product")
    public Product getProductWithHighestPrice() {
        return productRepository.findFirstByOrderByPriceDesc();
    }

    // GET localhost:8080/active-products
    @GetMapping("active-products")
    public List<Product> getAllActiveProducts() {
        return productRepository.findAllByActive(true);
    }

    // GET localhost:8080/public-products
    @GetMapping("public-products")
    public List<Product> getAllActiveProductsWithStock() {
        return productRepository.findByActiveTrueAndStockGreaterThan(0);
    }
}

// Staatus 200 - õnnestus
// Staatus 400 - üldine viga
// Staatus 404 - URL valesti pandud
// Staatus 405 - URL ja API tüüp ei ühti (GET,POST,DELETE,PUT)
// Staatus 415 - Body on valet tüüpi
