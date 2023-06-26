package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.OmnivaPM;
import ee.mihkel.webshop.model.ParcelMachineResponse;
import ee.mihkel.webshop.model.SmartpostPM;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ParcelMachineController {

    // 1 @PathVariable     URL:  parcel-machines/{country}       parcel-machines/EE
    // 5+ @RequestBody      URL:  parcel-machines    POST, PUT   {"country": EE}  <--- eraldi mudel
    // 2-4 @RequestParam     URL:  parcel-machines?country=EE     @RequestParam String country

    @GetMapping("parcel-machines/{country}")
    public ParcelMachineResponse getParcelMachines(@PathVariable String country) {

        RestTemplate restTemplate = new RestTemplate(); // Teeb API päringuid

        String omnivaUrl = "https://www.omniva.ee/locations.json";
        ResponseEntity<OmnivaPM[]> omnivaResponse = restTemplate.exchange(
            // url kuhu     meetodi liik  body mille saadan    mida ma sealt lehelt saan
                omnivaUrl, HttpMethod.GET, null, OmnivaPM[].class);
        // ResponseEntity --> siia sisse liiguvad tagastatavad andmed, body, staatuskood, headerid

        List<OmnivaPM> omnivaPMs = Arrays.stream(omnivaResponse.getBody())
                                        .filter(e -> e.a0_NAME.equals(country))
                                        .toList();

        String smartpostUrl = "https://www.smartpost.ee/places.json";
        ResponseEntity<SmartpostPM[]> smartpostResponse = restTemplate.exchange(
                smartpostUrl, HttpMethod.GET, null, SmartpostPM[].class);

        ParcelMachineResponse response = new ParcelMachineResponse();
        response.setOmnivaPMs(omnivaPMs);
        if (country.equals("EE")) {
            response.setSmartpostPMs(Arrays.asList(smartpostResponse.getBody()));
        } else {
            response.setSmartpostPMs(new ArrayList<>());
        }

        return response;
    }
}

//Salat
//        Tee uus veebiprojekt. Koosta klass Toiduaine tarbeks (nimetus, valkude, rasvade ja süsivesikute protsent). Protsent kokku ei saa ületada 100 - muidu antakse veateade - tee see API otspunktist andmeid võttes. Loo API otspunktist brauseri või Postmani abil mõni toiduaine ja pane ta andmebaasi (nt. kartul, hapukoor, vorst). Andmed saab nt (https://tka.nutridata.ee/et/). Sealt kartul (https://tka.nutridata.ee/et/toidud/280). Koosta klass Toidukomponendi tarbeks (kogus, viide toiduainele - võõrvõtmena). Loo mõned toidukomponendid (nt. 100 g kartuleid, 30 g hapukoort, 50 g vorsti). Lisa toidukomponendile API otspunkt selle sees leiduva rasvakoguse arvutamiseks.
//
//        Koosta klass Toidu jaoks (koosneb nimetusest ja toidukomponentidest - võõrvõtmete list). Lisa toidule käsklused küsimaks sisalduvate valkude, rasvade ja süsivesikute kogust. Loo brauseri või Postmani abil retsepti järgi toit (nt. kartulisalat).
//
//        Võimalda otsida Toiduainet, Toidukomponenti kui ka Toitu nimetuse järgi (3 erinevat API otspunkti). Tee uus API otspunkt, mis küsib sisendiks rasva protsendi algväärtust ja lõppväärtust ning tagastab kõik Toidud kellel on rasvaprotsent sellest vahemikus.
