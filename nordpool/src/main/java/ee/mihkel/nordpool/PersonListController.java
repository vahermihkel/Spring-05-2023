package ee.mihkel.nordpool;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PersonListController {
    // TEGELIKKUSES VÕTAME ANDMEBAASIST
    Person isik1 = new Person(1L, "M", "V", "3");
    Person isik2 = new Person(2L, "R", "S", "3");
    Person isik3 = new Person(3L, "A", "L", "3");

    List<Person> persons = new ArrayList<>(Arrays.asList(isik1, isik2, isik3));

    // localhost:8080/persons
    @GetMapping("persons")
    public List<Person> getPersons() {
        return persons;
    }

    // localhost:8080/add-person/4/Mart/Kivi/314213     <---- PathVariable kui kasutaks
    // localhost:8080/add-person?id=4&firstName=Mart&lastName=Kivi&phoneNumber=314213
    @GetMapping("add-person")  // @PostMapping
    public List<Person> addPerson(
            @RequestParam Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phoneNumber
    ) {
        Person person = new Person(id, firstName, lastName, phoneNumber);
        persons.add(person);
        return persons;
    }

    // localhost:8080/delete-person/1
    @GetMapping("delete-person/{index}") // @DeleteMapping
    public List<Person> deletePerson(@PathVariable int index) {
        persons.remove(index);
        return persons;
    }
}

// @GetMapping ---> selleks, et saada brauseris päringuid läbi viia
// Postman

// kasutajad, tellimused, tooted, kategooriad, poed
