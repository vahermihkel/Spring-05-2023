package ee.mihkel.nordpool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Random;

@RestController
public class PaymentController {



    @GetMapping("payment/{sum}")
    public ResponseEntity<?> makePayment(@PathVariable String sum) throws URISyntaxException, JsonProcessingException {
        String apiUrl = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        String paymentData = "{\n" +
                "    \"api_username\": \"e36eb40f5ec87fa2\",\n" +
                "    \"account_name\": \"EUR3D1\",\n" +
                "    \"amount\": \"" + sum + "\",\n" +
                "    \"order_reference\": " + Math.ceil(new Random().nextDouble() * 999999) + ",\n" +
                "    \"nonce\": \"a9b7f7e7as" + LocalDateTime.now() + new Random().nextDouble() * 999999 + "\",\n" +
                "    \"timestamp\": \"" + ZonedDateTime.now() + "\",\n" +
                "    \"customer_url\": \"https://maksmine.web.app/makse\"\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic ZTM2ZWI0MGY1ZWM4N2ZhMjo3YjkxYTNiOWUxYjc0NTI0YzJlOWZjMjgyZjhhYzhjZA==");

        HttpEntity<String> request = new HttpEntity<>(paymentData, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(new URI(apiUrl), request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseContent = response.getBody();
            JsonNode jsonNode = new ObjectMapper().readTree(responseContent);
            String paymentLink = jsonNode.get("payment_link").asText();
            return ResponseEntity.ok(paymentLink);
        } else {
            return ResponseEntity.badRequest().body("Payment failed.");
        }
    }
}
