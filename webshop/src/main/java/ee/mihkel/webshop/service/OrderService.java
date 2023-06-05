package ee.mihkel.webshop.service;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.model.EverypayData;
import ee.mihkel.webshop.model.EverypayLink;
import ee.mihkel.webshop.model.EverypayResponse;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCache productCache;

    @Autowired
    RestTemplate restTemplate;

    public Order saveOrderToDb(Long personId, List<Product> products, double sum) {
        Person person = personRepository.findById(personId).get();

        Order order = new Order();
        order.setPaid(false);
        order.setTotalSum(sum);
        order.setCreationDate(new Date());
        order.setProducts(products);
        order.setPerson(person);

        return orderRepository.save(order);
    }

    public EverypayLink getEverypayLink(double sum, Order dbOrder, String url) {
        EverypayData data = getEverypayData(sum, dbOrder);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic ZTM2ZWI0MGY1ZWM4N2ZhMjo3YjkxYTNiOWUxYjc0NTI0YzJlOWZjMjgyZjhhYzhjZA==");
        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(data, headers);

        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EverypayResponse.class );

        EverypayLink everypayLink = new EverypayLink();

        everypayLink.setLink(response.getBody().payment_link);
        return everypayLink;
    }

    private EverypayData getEverypayData(double sum, Order dbOrder) {
        EverypayData data = new EverypayData();
        data.setApi_username("e36eb40f5ec87fa2");
        data.setAccount_name("EUR3D1");
        data.setAmount(sum);
        data.setOrder_reference(dbOrder.getId().toString());
        data.setNonce("a9b7f7a" + dbOrder.getId() + new Date());
        data.setTimestamp(ZonedDateTime.now().toString());
        data.setCustomer_url("https://maksmine.web.app/makse");
        return data;
    }

    public List<Product> getDbProducts(List<Product> products) throws ExecutionException {
        List<Product> dbProducts = new ArrayList<>();
        for (Product p: products) {
            Product originalProduct = productCache.getProduct(p.getId());
            dbProducts.add(originalProduct);
        }
        return dbProducts;
    }
}
