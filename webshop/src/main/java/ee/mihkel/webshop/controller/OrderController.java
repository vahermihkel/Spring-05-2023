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
import java.util.concurrent.ExecutionException;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

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

    // LISAMISE UUE ORDERI ANDMEBAASI SEL HETKEL KUI MAKSET ALUSTATAKSE
    @PostMapping ("payment/{personId}")
    public EverypayLink payment(@PathVariable Long personId, @RequestBody List<Product> products) throws ExecutionException {

        List<Product> originalProducts = orderService.getDbProducts(products);

        double sum = originalProducts.stream().mapToDouble(Product::getPrice).sum(); // võtta igaühe juurest ID ja leida ta andmebaasist
        // cache   Google Guava

        // ctrl + alt + m
        Order dbOrder = orderService.saveOrderToDb(personId, originalProducts, sum);

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



  //  https://maksmine.web.app/makse?order_reference=41312330&payment_reference=4f843ae3b18c4837d81a097771f5b4105bd9d0b37d64acb73204cef75c7bf9f1
    // https://maksmine.web.app/makse?order_reference=41312331&payment_reference=ec64cfc3bd40e4f6296809d5130b768778b38b3a633742d160f6d69babdff138
}
