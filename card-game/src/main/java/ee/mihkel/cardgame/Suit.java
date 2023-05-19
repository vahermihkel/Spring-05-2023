package ee.mihkel.cardgame;

import java.util.Random;

public enum Suit {
    SPADE, CLUB, DIAMOND, HEART;

    public static Suit getRandomSuit() {
        Random random = new Random();
        int randomNumber = random.nextInt(values().length);
        return values()[randomNumber];
    }
}

// return [SPADE, CLUB, DIAMOND, HEART][3]
// values() on enumisse sissekirjutatud funktsioon, mis annab kõik
