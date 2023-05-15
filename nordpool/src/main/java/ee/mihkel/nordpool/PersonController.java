package ee.mihkel.nordpool;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    // TEGELIKKUSES VÕTAME ANDMEBAASIST
    //                {"id":1,"firstName":"M","lastName":"V","phoneNumber":"3"}
    Person isik1 = new Person(1L, "M", "V", "3");

    // localhost:8080/get-person
    @GetMapping("get-person")
    public Person getPerson() {
        return isik1;
    }

    // localhost:8080/change-firstname/Mihkel
    @GetMapping("change-firstname/{firstName}") // @PutMapping
    public Person changeFirstName(@PathVariable String firstName) {
        isik1.setFirstName(firstName);
        return isik1;
    }

    // localhost:8080/change-firstname2/Mihkel
    @GetMapping("change-firstname2/{firstName}") // @PutMapping
    public String changeFirstName2(@PathVariable String firstName) {
        isik1.setFirstName(firstName);
        return "Isiku eesnimi muudetud: " + firstName;
    }
}
