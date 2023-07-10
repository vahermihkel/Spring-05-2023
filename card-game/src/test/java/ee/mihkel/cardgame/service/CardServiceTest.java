package ee.mihkel.cardgame.service;

import ee.mihkel.cardgame.model.Card;
import ee.mihkel.cardgame.model.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardServiceTest {

    @Autowired
    CardService cardService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void Card_suit_is_one_of_the_suits() {
        Card card = cardService.getCard("mihkel");
        List<Suit> suits = new ArrayList<>(Arrays.asList(Suit.values()));
        //Suit[] suits1 = Suit.values();
        boolean isSuit = suits.contains(card.getSuit());
        //boolean isSuit1 = Arrays.stream(suits1).anyMatch(e -> e.equals(card.getSuit()));

        assertTrue(isSuit);
    }

    @Test
    void Card_value_is_between_two_and_ten() {
        Card card = cardService.getCard("mihkel");
        boolean isBetween = card.getValue() >= 2 && card.getValue() <= 10;
        assertTrue(isBetween);
    }

    @Test
    void Returns_Wrong_when_wrong_input() {
//        cardService.getCard("mihkel");
        String response = cardService.checkIfCorrect("random_input");
        assertEquals("Vale sisend!", response);
    }

    @Test
    void Returns_Timeout_when_slower_than_10_seconds() throws InterruptedException {
        cardService.getCard("mihkel");
        Thread.sleep(10001);
        String response = cardService.checkIfAnsweredOnCorrectTime();
        assertEquals("Vastasid liiga hilja!", response);
    }

    @Test
    void Returns_Timeout_when_slower_than_10_seconds_two_times() throws InterruptedException {
        cardService.getCard("mihkel");
        Thread.sleep(10001);
        cardService.checkIfAnsweredOnCorrectTime();
        String response = cardService.checkIfAnsweredOnCorrectTime();
        assertEquals("Vastasid liiga hilja!", response);
    }

    @Test
    void Returns_Gameover_when_slower_than_10_seconds_three_times() throws InterruptedException {
        cardService.getCard("mihkel");
        Thread.sleep(10001);
        cardService.checkIfAnsweredOnCorrectTime();
        cardService.checkIfAnsweredOnCorrectTime();
        String response = cardService.checkIfAnsweredOnCorrectTime();
        assertEquals("Mäng läbi!", response);
    }

    @Test
    void checkIfCorrect() {
    }

    @Test
    void checkIfAnsweredOnCorrectTime() {
    }

    @Test
    void checkIfAnsweredCorrectly() {
    }
}
