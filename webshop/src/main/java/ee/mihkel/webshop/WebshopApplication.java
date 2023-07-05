package ee.mihkel.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebshopApplication.class, args);
    }

}

// 28.06 --- 14.
// 03.07 --- 15. autentimisega, Tokeni validatsioon, enda tellimusi (vastavalt tokenile)
// 05.07 --- 16. rolle - Admin / Tavakasutaja, enda profiili (vastavalt tokenile)
// 10.07 --- 17. serverisse üles -> makse lõpuni, unit testid
// Vahepealne aeg lõpuprojekti tegemiseks
// 21.07 --- 05.07 koolitusest 45min teeme järgi
// 28.07     18. poolik päev, arutame sinu projekti
