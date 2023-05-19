package ee.mihkel.cardgame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private Suit suit; // SPADE, CLUB, DIAMOND, HEART
    private Rank rank; // "2",3,4,5,6,7,8,9,10,J,Q,K,A
    private int value; // 2,3,4,5,6,7,8,9,10,10,10,10,10

    public Card() {
        suit = Suit.getRandomSuit();
        rank = Rank.getRandomRank();

        switch (rank) {
            case TWO -> value = 2;
            case THREE -> value = 3;
            case FOUR -> value = 4;
            case FIVE -> value = 5;
            case SIX -> value = 6;
            case SEVEN -> value = 7;
            case EIGHT -> value = 8;
            case NINE -> value = 9;
            default -> value = 10;
        }
    }
}

//        if (rank == Rank.TWO) {
//            value = 2;
//        } else if (rank == Rank.THREE) {
//            value = 3;
//        } else if (rank == Rank.FOUR) {
//            value = 4;
//        }

// Suit suit = new Suit()
// suit.getRandomSuit();
