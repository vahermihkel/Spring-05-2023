package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.model.PersonDto;
import ee.mihkel.webshop.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ModelMapper modelMapper;

    //Get all persons localhost:8080/person
    @GetMapping("person")
    public ResponseEntity<List<Person>> getPersons(){
        return ResponseEntity.ok().body(personRepository.findAll());
    }

    @GetMapping("person-public2")
    public List<PersonDto> getPersonsPublic2(){
        List<Person> persons = personRepository.findAll();
        List<PersonDto> personDtos = new ArrayList<>();
        for (Person p: persons) {
            PersonDto personDto = new PersonDto();
            personDto.setFirstName(p.getFirstName());
            personDto.setLastName(p.getLastName());
            personDto.setEmail(p.getEmail());
            personDtos.add(personDto);
        }
        return personDtos;
    }

    @GetMapping("person-public")
    public ResponseEntity<List<PersonDto>> getPersonsPublic(){
        List<Person> persons = personRepository.findAll();
//        ModelMapper modelMapper = new ModelMapper(); // @Autowired - Dependency Injection
//        System.out.println(modelMapper);
//        log.info(modelMapper);
//        System.out.println("VIGA!!!");
//        log.error("VIGA!!!");
        List<PersonDto> personDtos = persons.stream()
                .map(person -> modelMapper.map(person, PersonDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(personDtos);
    }

    //Get person by id
    @GetMapping ("person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        return ResponseEntity.ok().body(personRepository.findById(id).get());
    }
    // Delete person
    @DeleteMapping("person/{id}")
    public ResponseEntity<List<Person>> deletePerson(@PathVariable Long id){
        personRepository.deleteById(id);
        return ResponseEntity.ok().body(personRepository.findAll());
    }

    //POST localhost:8080/person
    @PostMapping("person")
    public ResponseEntity<List<Person>> addPerson(@RequestBody Person person) throws Exception {
        if (person.getId()==null ||!personRepository.existsById(person.getId())) {
            personRepository.save(person);
        } else {
            throw new Exception("Id on juba olemas");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.findAll());
    }
    //PUT localhost:8080/person
    @PutMapping("person")
    public ResponseEntity<List<Person>> editPerson(@RequestBody Person person){
        if (personRepository.existsById(person.getId())) {
            personRepository.save(person);
        }
        return ResponseEntity.ok().body(personRepository.findAll());
    }
}
