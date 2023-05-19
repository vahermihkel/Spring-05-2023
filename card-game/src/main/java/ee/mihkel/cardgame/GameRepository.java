package ee.mihkel.cardgame;

import org.springframework.data.jpa.repository.JpaRepository;

// CrudRepository <-- kõige lihtsam
// SortingAndPagingRepository <-- pakub ka sortimist ja pagineerimist
// JpaRepository <-- kõike üleval + tühjendamist
public interface GameRepository extends JpaRepository<Game, Long> {
}
