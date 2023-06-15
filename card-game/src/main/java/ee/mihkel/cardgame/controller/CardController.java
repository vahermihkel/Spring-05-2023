package ee.mihkel.cardgame.controller;

import ee.mihkel.cardgame.entity.Game;
import ee.mihkel.cardgame.repository.GameRepository;
import ee.mihkel.cardgame.entity.Player;
import ee.mihkel.cardgame.repository.PlayerRepository;
import ee.mihkel.cardgame.model.Card;
import ee.mihkel.cardgame.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping("start/{playerName}")
    public Card start(
            @PathVariable String playerName
    ) {
        return cardService.getCard(playerName);
    }

    // localhost:8080/guess/lower    // localhost:8080/guess/equal    // localhost:8080/guess/higher
    @GetMapping("guess/{input}")
    public String guess(@PathVariable String input) {
        String errorMessage = cardService.checkIfCorrect(input);
        if (errorMessage != null) return errorMessage;

        errorMessage = cardService.checkIfAnsweredOnCorrectTime();
        if (errorMessage != null) return errorMessage;

        return cardService.checkIfAnsweredCorrectly(input);
    }

    // Kui mäng lõppeb, võiks teada anda kasutajale+++
    // Kellaaeg 10 sekundit nullida iga raund+++
    // Kui mäng algab, siis nullida kõik correct answers ja lives+++
    // High-score mängijale arvutada+++

    // KOOS:
    // Kui esimesena käivitatakse mingi muu API otspunkt kui "start" - mis teeme?
    // Kui käivitatakse guess korduvalt, kas peaks lahendama?

    // ANDMEBAASI:
    // Tehke ühendus paremalt Database alt
    // Looge uus Database PGAdmin sees: "card-game"
    // Application.properties sees tehke seadistused

    // 1. Uus controller: DatabaseController
    // 2. Tagastatakse kõik mängijad
    // 3. Tagastatakse kõik mängud
    // 4. Tagatatakse kõik mängud high-score järjekorras
    // 5. Tagastataske kõik selle mängija mängud
    // 6. Tagastataske kõik selle mängija mängud high-score järjekorras
    // 7. Tagasta kõik mängud millel on vähemalt 2 õiget vastust
    // 8. Tagasta kõige suurema skooriga mängija - 2te moodi (mängud järjekorda, mängijad high-score järjekorda)
    // 9. Erinevad kaustad
    // Võtame kasutusele Service-i
    // Front-end
    // Unit testid

//    @GetMapping("new-round")
//    public Card newRound() {
//        return baseCard;
//    }
}
