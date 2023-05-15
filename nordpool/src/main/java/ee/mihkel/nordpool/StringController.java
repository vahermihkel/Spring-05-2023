package ee.mihkel.nordpool;

// KÕIK CONTROLLER TÜÜPI FAILID ON NEED, MIS SUHTLEVAD EESRAKENDUSEGA

// MVC   Model-View-Controller

// Model - constructorid, getterid, setterid
// View - React, Angular, Vue
// Controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController  // Annotatsioon - tegemist on controlleriga
public class StringController {

    // localhost:8080/hello
    @GetMapping("hello")
    public String getDate() {
        return "Hello world at " + new Date();
    }

    // @PathVariable <- kindlasti import
    // @PathVariable String <- üritab konverteerida selleks tüübiks
    // @PathVariable String name <- nimetus peab olema täpselt sama kui {name}
    // localhost:8080/hi/Mihkel
    @GetMapping("hi/{name}")
    public String getName(@PathVariable String name) {
        return "Hello " + name;
    }

    // localhost:8080/add/4/6
    @GetMapping("add/{first}/{second}")
    public int add(@PathVariable int first, @PathVariable int second) {
        return first + second;
    }

    // 11.04
    // korrutamine läbi numbrite   multiply

}


// 404 - panin URLi brauserisse kogemata valesti / taaskäivitamata
// 400 - mingisugune viga - ütleb meile ka mis viga on Failed to convert value of type 'java.lang.String' to required type 'int'; For input string: "6m"
