package ee.mihkel.webshop.service;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.model.EverypayData;
import ee.mihkel.webshop.model.EverypayLink;
import ee.mihkel.webshop.model.EverypayPaymentState;
import ee.mihkel.webshop.model.EverypayResponse;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.OrderRowRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    @Value("${everypay.url}")
    String everypayUrl;

    @Value("${everypay.authorization}")
    String everypayAuthorization;

    @Value("${everypay.username}")
    String everypayUsername;

    @Value("${everypay.customer-url}")
    String everypayCustomerUrl;

    @Autowired
    OrderRowRepository orderRowRepository;

    @Transactional  // keerab kõik andmebaasipäringud tagasi kui lõpuni ei jõuta
    public Order saveOrderToDb(Long personId, List<OrderRow> orderRows, double sum) throws Exception {
        Person person;
        if (personRepository.findById(personId).isPresent()) {
            person = personRepository.findById(personId).get();
        } else {
            throw new Exception("Person not found");
        }

        orderRowRepository.saveAll(orderRows);

        Order order = new Order();
        order.setPaid("initial");
        order.setTotalSum(sum);
        order.setCreationDate(new Date());
        order.setOrderRows(orderRows); // ! kui lisan Orderi andmebaasi, siis siin kohas ta proovib siduda OrderRow-dega,
                                            // aga neid ju pole andmebaasis
        order.setPerson(person);

        return orderRepository.save(order);
    }

    public EverypayLink getEverypayLink(double sum, Order dbOrder) {
        EverypayData data = getEverypayData(sum, dbOrder);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, everypayAuthorization);
        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(data, headers);

        String url = everypayUrl + "/payments/oneoff";
        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EverypayResponse.class );

        EverypayLink everypayLink = new EverypayLink();

        everypayLink.setLink(response.getBody().payment_link);
        return everypayLink;
    }

    private EverypayData getEverypayData(double sum, Order dbOrder) {
        EverypayData data = new EverypayData();
        data.setApi_username(everypayUsername);
        data.setAccount_name("EUR3D1");
        data.setAmount(sum);
        data.setOrder_reference(dbOrder.getId().toString());
        data.setNonce("a9b7f7a" + dbOrder.getId() + new Date());
        data.setTimestamp(ZonedDateTime.now().toString());
        data.setCustomer_url(everypayCustomerUrl);
        return data;
    }

    // Product.id, Product.id, Product.id    ---    { Product.id, quantity }, { Product.id, quantity }
    public List<OrderRow> getDbProducts(List<OrderRow> orderRows) throws ExecutionException {
        List<OrderRow> dbProducts = new ArrayList<>();
        for (OrderRow o: orderRows) {
            Product originalProduct = productCache.getProduct(o.getProduct().getId());
            OrderRow orderRow = new OrderRow();
            orderRow.setProduct(originalProduct);
            orderRow.setQuantity(o.getQuantity());
            dbProducts.add(orderRow);
        }
        return dbProducts;
    }

    public Order checkIfOrderPaid(String paymentReference) {

        String username = everypayUsername;
        String url = everypayUrl + "/payments/" + paymentReference +"?api_username=" + username;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, everypayAuthorization);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<EverypayPaymentState> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity,EverypayPaymentState.class );
        EverypayPaymentState httpBody = response.getBody();

        Order order = orderRepository.findById(Long.valueOf(httpBody.order_reference)).get();
        String orderStatus = httpBody.payment_state;

        order.setPaid(orderStatus);
        orderRepository.save(order);

        return order;
    }
}
