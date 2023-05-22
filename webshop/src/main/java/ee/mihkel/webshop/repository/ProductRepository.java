package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Hinna järjekorras
    List<Product> findAllByOrderByPrice();
    // Kõige kallim toode
    Product findFirstByOrderByPriceDesc();
    // Kõik aktiivsed tooted
    List<Product> findAllByActive(boolean active);

    // Kõik aktiivsed ja kogus suurem kui 0 tooted
    List<Product> findByActiveTrueAndStockGreaterThan(int stock);
}
