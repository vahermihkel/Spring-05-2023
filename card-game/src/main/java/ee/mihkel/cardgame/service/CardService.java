package ee.mihkel.cardgame.service;

import ee.mihkel.cardgame.entity.Game;
import ee.mihkel.cardgame.entity.Player;
import ee.mihkel.cardgame.model.Card;
import ee.mihkel.cardgame.repository.GameRepository;
import ee.mihkel.cardgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class CardService {
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

    public Card getCard(String playerName) {
        if (baseCard == null) {
            gameStartTime = System.currentTimeMillis();
            baseCard = new Card();
            getPlayer(playerName);
            correctAnswers = 0;
            lives = 3;

        }
        roundStartTime = LocalDateTime.now();
        return baseCard;
    }

    private void getPlayer(String playerName) {
        Optional<Player> playerOptional = playerRepository.findById(playerName); // Võib olla Player tüüpi VÕI võib olla null
        if (playerOptional.isPresent()) {
            player = playerOptional.get();
        } else {
            player = new Player();
            player.setName(playerName);
            player.setFirstCreated(new Date());
            playerRepository.save(player);
        }
    }

    public String checkIfCorrect(String input) {
        if (lives <= 0) {
            return "Elud on otsas! Alusta mängu uuesti!";
        }
        if (!input.equals("lower") && !input.equals("equal") && !input.equals("higher")) {
            return "Vale sisend!";
        }
        return null;
    }

    public String checkIfAnsweredOnCorrectTime() {
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
        return null;
    }

    public String checkIfAnsweredCorrectly(String input) {
        Card newCard = new Card();

        if (baseCard.getValue() < newCard.getValue() && input.equals("higher") ||
                baseCard.getValue() == newCard.getValue() && input.equals("equal") ||
                baseCard.getValue() > newCard.getValue() && input.equals("lower")) {
            baseCard = newCard;
            correctAnswers++;
            return "Vastasid õigesti!"; // return "CORRECT_ANSWER";
        } else {
            baseCard = newCard;
            lives--;
            if (lives == 0) {
                sendGameToDb();
                return "Mäng läbi!"; // return "GAME_OVER";
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
}
