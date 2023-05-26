package ee.mihkel.nordpool;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class NordpoolController {

    // https://dashboard.elering.ee/api/nps/price?start=2023-05-20T12%3A59%3A59.999Z&end=2023-05-24T20%3A59%3A59.999Z

    @GetMapping("nordpool")
    public List<TimestampPrice> getNordpoolPrices(
            @RequestParam String start,
            @RequestParam String end
    ) {
        // 2023-05-20
        // 2023-05-23
        // localhost:8080/nordpool?start=2023-05-20&end=2023-05-23

        RestTemplate restTemplate = new RestTemplate();
//        ZonedDateTime startDate = ZonedDateTime.;

//        String url = "https://dashboard.elering.ee/api/nps/price?start=" + startDate + "&end=" + endDate;
        // yyyy-MM-dd'T'hh:mm:ssZ
        String url = "https://dashboard.elering.ee/api/nps/price?start=" + start + "T00:00:00.000Z&end=" + end + "T23:59:59.999Z";
        ResponseEntity<NordpoolResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, NordpoolResponse.class);

        return response.getBody().getData().getEe();

        // Võtaks sisendiks riigi ja tagastab selle riigi ajad
    }
}
