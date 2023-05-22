package ee.mihkel.cardgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
public class CardController {

    // Dependency Injection
    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    Card baseCard;
    LocalDateTime roundStartTime;
    Player player;
    int lives = 3;
    int correctAnswers = 0;
    private long gameStartTime;

    @GetMapping("start/{playerName}")
    public Card start(
            @PathVariable String playerName
    ) {
        if (baseCard == null) {
            gameStartTime = System.currentTimeMillis();
            baseCard = new Card();
            Optional<Player> playerOptional = playerRepository.findById(playerName); // Võib olla Player tüüpi VÕI võib olla null
            if (playerOptional.isPresent()) {
                player = playerOptional.get();
            } else {
                player = new Player();
                player.setName(playerName);
                player.setFirstCreated(new Date());
                playerRepository.save(player);
            }
            correctAnswers = 0;
            lives = 3;
        }
        roundStartTime = LocalDateTime.now();
        return baseCard;
    }

    // localhost:8080/guess/lower    // localhost:8080/guess/equal    // localhost:8080/guess/higher
    @GetMapping("guess/{input}")
    public String guess(@PathVariable String input) {
        if (lives <= 0) {
            return "Elud on otsas! Alusta mängu uuesti!";
        }
        if (!input.equals("lower") && !input.equals("equal") && !input.equals("higher")) {
            return "Vale sisend!";
        }
        LocalDateTime limitTime = roundStartTime.plusSeconds(10);
        LocalDateTime answerTime = LocalDateTime.now();
        if (answerTime.isAfter(limitTime)) {
            lives--; // lives -= 1;     lives = lives - 1;
            if (lives == 0) {
                sendGameToDb();
                return "Mäng läbi!";
            }
            return "Vastasid liiga hilja!";
        }

        Card newCard = new Card();

        if (baseCard.getValue() < newCard.getValue() && input.equals("higher") ||
                baseCard.getValue() == newCard.getValue() && input.equals("equal") ||
                    baseCard.getValue() > newCard.getValue() && input.equals("lower")) {
            baseCard = newCard;
            correctAnswers++;
            return "Vastasid õigesti!";
        } else {
            baseCard = newCard;
            lives--;
            if (lives == 0) {
                sendGameToDb();
                return "Mäng läbi!";
            }
            return "Vastasid valesti!";
        }
    }

    private void sendGameToDb() {
//        if (lives == 0) {
            Game game = new Game();
            game.setPlayer(player);
            game.setCorrectAnswers(correctAnswers);
            long duration = System.currentTimeMillis() - gameStartTime;
            game.setDuration(duration);
            gameRepository.save(game);
            baseCard = null;
            if (player.getHighScore() < correctAnswers) {
                player.setHighScore(correctAnswers);
                playerRepository.save(player); // muutmisega
            }
//        }
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
