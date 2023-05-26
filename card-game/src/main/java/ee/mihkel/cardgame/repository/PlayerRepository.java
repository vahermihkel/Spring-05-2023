package ee.mihkel.cardgame.repository;

import ee.mihkel.cardgame.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {

    List<Player> findAllByOrderByHighScoreDesc();
//    List<Player> findByCorrectAnswers(int correctAnswers);
//    Optional<Player> findTopByOrderByScoreDesc();

    Player findFirstByOrderByHighScore();
}
