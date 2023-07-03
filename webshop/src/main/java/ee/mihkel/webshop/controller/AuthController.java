package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.model.AuthToken;
import ee.mihkel.webshop.model.LoginData;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController()
public class AuthController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    TokenGenerator tokenGenerator;

    @PostMapping("login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginData loginData){

        Person person = personRepository.findPersonByEmail(loginData.getEmail());
        AuthToken authToken = new AuthToken();


//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(loginData.getPassword(), person.getPassword())) {
            authToken.setToken(tokenGenerator.generateToken(person.getEmail()));
        }

        return ResponseEntity.ok().body(authToken);
    }

    @PostMapping("signup")
    public ResponseEntity<AuthToken> signup(@RequestBody Person person) throws Exception {
        AuthToken authToken = new AuthToken();

        if (person.getId()==null ||!personRepository.existsById(person.getId())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(person.getPassword());
            person.setPassword(hashedPassword);
            person.setCreationDate(new Date());
            personRepository.save(person);
            authToken.setToken(tokenGenerator.generateToken(person.getEmail()));
        } else {
            throw new Exception("Id on juba olemas");
        }

        return ResponseEntity.ok().body(authToken);
    }
}
