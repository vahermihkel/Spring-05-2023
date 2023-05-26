package ee.mihkel.cardgame.repository;

import ee.mihkel.cardgame.entity.Game;
import ee.mihkel.cardgame.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// CrudRepository <-- kõige lihtsam
// SortingAndPagingRepository <-- pakub ka sortimist ja pagineerimist
// JpaRepository <-- kõike üleval + tühjendamist
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayer(Player player);
//    List<Game> findAllByOrderByPlayerDesc();

    List<Game> findAllByOrderByCorrectAnswersDesc();

    List<Game> findAllByPlayerOrderByCorrectAnswersDesc(Player player);


//    List<Game> findAllByPlayerAndCorrectAnswers(Player player, int correctAnswers);
//    leian kindla mängija ja kindla correctAnswer järgi

    List<Game> findAllByCorrectAnswersIsGreaterThan(int correctAnswers);

    List<Game> findAllByPlayerAndCorrectAnswersIsGreaterThan(Player player, int correctAnswers);

    Game findFirstByOrderByCorrectAnswers();
}


// 2. Tagastatakse kõik mängijad+++
// 3. Tagastatakse kõik mängud+++
// 4. Tagatatakse kõik mängud high-score järjekorras+++
// 4. Tagatatakse kõik mängijad high-score järjekorras+++
// 5. Tagastataske kõik selle mängija mängud+++
// 6. Tagastataske kõik selle mängija mängud correctAnswers järjekorras
// 7. Tagasta kõik mängud millel on vähemalt 2 õiget vastust
// 8. Tagasta kõige suurema skooriga mängija - 2te moodi (mängud järjekorda, mängijad high-score järjekorda)
