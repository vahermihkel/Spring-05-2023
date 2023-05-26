package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    //Get all persons localhost:8080/person
    @GetMapping("person")
    public List<Person> getPersons(){
        return personRepository.findAll();
    }
    //Get person by id
    @GetMapping ("person/{id}")
    public Person getPerson(@PathVariable Long id){
        return personRepository.findById(id).get();
    }
    // Delete person
    @DeleteMapping("person/{id}")
    public List<Person> deletePerson(@PathVariable Long id){
        personRepository.deleteById(id);
        return personRepository.findAll();
    }

    //POST localhost:8080/person
    @PostMapping("person")
    public List<Person> addPerson(@RequestBody Person person){
        if (person.getId()==null ||!personRepository.existsById(person.getId())) {
            personRepository.save(person);
        }
        return personRepository.findAll();
    }
    //PUT localhost:8080/person
    @PutMapping("person")
    public List<Person> editPerson(@RequestBody Person person){
        if (personRepository.existsById(person.getId())) {
            personRepository.save(person);
        }
        return personRepository.findAll();
    }
}
