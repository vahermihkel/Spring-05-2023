package ee.mihkel.webshop.service;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    Product product = new Product();
    OrderRow orderRow1 = new OrderRow(1L, 2, product);
    OrderRow orderRow2 = new OrderRow(2L, 5, product);


    @Mock
    ProductRepository productRepository;

    @Mock
    ProductCache productCache;

    OrderService orderService = new OrderService();

    @BeforeEach
    void setUp() {
        product.setPrice(1.99);
        orderService.productRepository = productRepository;
        orderService.productCache = productCache;
        productRepository.save(product);
    }

    @Test
    void getDbProducts() throws ExecutionException {
        List<OrderRow> originalProducts = orderService.getDbProducts(Arrays.asList(orderRow1, orderRow2));
        assertEquals(2, originalProducts.size());
        assertEquals(5, originalProducts.get(1).getQuantity());
    }


    @Test
    void calculateTotalSum() {
        double sum = orderService.calculateTotalSum(Collections.singletonList(orderRow1));
        assertEquals(3.98,sum);
    }
}
