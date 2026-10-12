package blackjack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HandTest{
    @Test
    void aceChangesFromElevenToOneAfterHit() {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SIX));
        assertEquals(17, hand.getScore());
        assertTrue(hand.isSoft());
        hand.addCard(new Card(Card.Suit.CLUBS, Card.Rank.NINE));
        assertEquals(16, hand.getScore());
        assertFalse(hand.isSoft());
    }

    @Test
    void twoCardTwentyOneIsBlackJack(){
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        assertEquals(21, hand.getScore());
        assertTrue(hand.isBlackjack());
        assertFalse(hand.isBust());
    }

    @Test
    void threeCardTwentyOneIsNotBlackjack(){
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.SEVEN));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.NINE));
        assertEquals(21, hand.getScore());
        assertFalse(hand.isBlackjack());
        assertFalse(hand.isBust());
    }

    @Test
    void multipleAcesAreScoredCorrectly(){
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.NINE));
        assertEquals(21, hand.getScore());
        assertTrue(hand.isSoft());
        assertFalse(hand.isBlackjack());
    }
}