package ee.mihkel.cardgame;

import java.util.Random;

public enum Rank {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    public static Rank getRandomRank() {
        Random random = new Random();
        int randomNumber = random.nextInt(values().length);
        return values()[randomNumber];
    }
}
