package ee.mihkel.cardgame.controller;

import ee.mihkel.cardgame.entity.Game;
import ee.mihkel.cardgame.entity.Player;
import ee.mihkel.cardgame.repository.GameRepository;
import ee.mihkel.cardgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;


    // 2. Tagastatakse kõik mängijad
    @GetMapping("players")
    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    // 3. Tagastatakse kõik mängud
    @GetMapping("games")
    public List<Game> games(){
        return gameRepository.findAll();
    }

    // 4. Tagatatakse kõik mängud high-score järjekorras

    @GetMapping("card-game")
    public List<Player> getHighScore(){
        return playerRepository.findAllByOrderByHighScoreDesc();
    }

    // 5. Tagastataske kõik selle mängija mängud
    @GetMapping("/games/players")
    public List<Game> getGamesByPlayers() {
        return gameRepository.findByPlayer(playerRepository.findById("playerId").orElse(null));
    }

    // 6. Tagastataske kõik selle mängija mängud high-score järjekorras

    @GetMapping("/games/players/descending/{name}")
    public List<Game> getGamesByPlayerDescending(@PathVariable String name) {
        Player player = playerRepository.findById(name).get();
        return gameRepository.findAllByPlayerOrderByCorrectAnswersDesc(player);
    }

    // 7. Tagasta kõik mängud millel on vähemalt 2 õiget vastust
    @GetMapping("/games/correct-answers")
    public List<Game> getGamesWithAtLeastTwoCorrectAnswers() {
        return gameRepository.findAllByCorrectAnswersIsGreaterThan(2);
    }

    //  8. Tagasta kõige suurema skooriga mängija - 2te moodi (mängud järjekorda, mängijad high-score järjekorda)
    @GetMapping("/players/highest-score")
    public Player getPlayerWithHighestScore() {
        return playerRepository.findFirstByOrderByHighScore();
    }
}
