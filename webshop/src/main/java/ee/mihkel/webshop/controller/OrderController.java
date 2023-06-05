package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.model.EverypayData;
import ee.mihkel.webshop.model.EverypayLink;
import ee.mihkel.webshop.model.EverypayResponse;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;


    // võtab kõik
    @GetMapping("order")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    // võtab ühe id järgi
    @GetMapping("order/{id}")
    public Order getOrder(@PathVariable Long id){
        return orderRepository.findById(id).get();
    }

    @Autowired
    OrderService orderService;

    // LISAMISE UUE ORDERI ANDMEBAASI SEL HETKEL KUI MAKSET ALUSTATAKSE
    @PostMapping ("payment/{personId}")
    public EverypayLink payment(@PathVariable Long personId, @RequestBody List<Product> products) {
        double sum = products.stream().mapToDouble(Product::getPrice).sum();

        // ctrl + alt + m
        Order dbOrder = orderService.saveOrderToDb(personId, products, sum);

        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        return orderService.getEverypayLink(sum, dbOrder, url);
    }




    // kustutab id järgi
//    @DeleteMapping("order")
//    public List<Order> deleteOrder(@PathVariable Long id){
//        orderRepository.deleteById(id);
//        return orderRepository.findAll();
//    }
    // muudab orderi
//    @PutMapping("order/delete")
//    public List<Order> editOrder(@PathVariable Order order){
//        if (orderRepository.existsById(order.getId())) {
//            orderRepository.save(order);
//        }
//        return orderRepository.findAll();
//    }

}
