package ee.mihkel.cardgame.entity;

// import javax.persistence.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
//import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    private String name;
    private Date firstCreated;
    private int highScore;

//    private List<Game> games;

    // @OneToOne
    // private ContactData contact;
}
