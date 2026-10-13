package blackjack;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {
    @Test
    void blackjackPaysThreeToTwo(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.NINE),
            new Card(Card.Suit.SPADES, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        ));
        game.startRound(1000, deck);
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(BlackjackGame.RoundResult.PLAYER_WIN, game.getResult());
        assertEquals(11500, game.getPlayer().getBalanceCents());
        assertEquals(1000, game.getBetCents());
        game.getResult();
        game.getResult();
        assertEquals(11500, game.getPlayer().getBalanceCents());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void normalWinPaysEvenMoney(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.SEVEN),
            new Card(Card.Suit.SPADES, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN)
        ));
        game.startRound(1000, deck);
        game.stand();
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(BlackjackGame.RoundResult.PLAYER_WIN, game.getResult());
        assertEquals(11000, game.getPlayer().getBalanceCents());
        assertEquals(1000, game.getBetCents());
        game.getResult();
        game.getResult();
        assertEquals(11000, game.getPlayer().getBalanceCents());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void equalScoreReturnBet(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            new Card(Card.Suit.SPADES, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.JACK)
        ));
        game.startRound(1000, deck);
        game.stand();
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(BlackjackGame.RoundResult.PUSH, game.getResult());
        assertEquals(10000, game.getPlayer().getBalanceCents());
        assertEquals(1000, game.getBetCents());
        game.getResult();
        game.getResult();
        assertEquals(10000, game.getPlayer().getBalanceCents());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void lossKeepsBetDeducted(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.JACK)
        ));
        game.startRound(1000, deck);
        game.stand();
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(BlackjackGame.RoundResult.DEALER_WIN, game.getResult());
        assertEquals(9000, game.getPlayer().getBalanceCents());
        assertEquals(1000, game.getBetCents());
        game.getResult();
        game.getResult();
        assertEquals(9000, game.getPlayer().getBalanceCents());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void bothBlackjacksReturnBet(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
            new Card(Card.Suit.CLUBS, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        ));
        game.startRound(1000, deck);
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(BlackjackGame.RoundResult.PUSH, game.getResult());
        assertEquals(10000, game.getPlayer().getBalanceCents());
        assertEquals(1000, game.getBetCents());
        game.getResult();
        game.getResult();
        assertEquals(10000, game.getPlayer().getBalanceCents());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void doubleDownWinPaysUsingDoubledBet(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.TEN),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
            new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE)
        ));
        game.startRound(1000, deck);
        assertTrue(game.canDoubleDown());
        game.doubleDown();
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(2000, game.getBetCents());
        assertEquals(3, game.getPlayer().getHand().size());
        assertEquals(12000, game.getPlayer().getBalanceCents());
        assertEquals(BlackjackGame.RoundResult.PLAYER_WIN, game.getResult());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void doubleDownBestEndsRoundWithoutDealerDrawing(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.NINE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX)
        ));
        game.startRound(1000, deck);
        assertTrue(game.canDoubleDown());
        game.doubleDown();
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(2000, game.getBetCents());
        assertEquals(3, game.getPlayer().getHand().size());
        assertEquals(2, game.getDealer().getHand().size());
        assertEquals(8000, game.getPlayer().getBalanceCents());
        assertEquals(BlackjackGame.RoundResult.DEALER_WIN, game.getResult());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void doubleDOwnAllowsExactRemainingBalance(){
        BlackjackGame game = new BlackjackGame(2000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.TEN),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
            new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE)
        ));
        game.startRound(1000, deck);
        assertTrue(game.canDoubleDown());
        game.doubleDown();
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(2000, game.getBetCents());
        assertEquals(3, game.getPlayer().getHand().size());
        assertEquals(4000, game.getPlayer().getBalanceCents());
        assertEquals(BlackjackGame.RoundResult.PLAYER_WIN, game.getResult());
        assertEquals(1, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void doubleDownRejectsInsufficientBalance(){
        BlackjackGame game = new BlackjackGame(1500, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.TEN),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
            new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE)
        ));
        game.startRound(1000, deck);
        assertFalse(game.canDoubleDown());
        assertThrows(IllegalStateException.class, () -> game.doubleDown());
        assertEquals(500, game.getPlayer().getBalanceCents());
        assertEquals(1000, game.getBetCents());
        assertEquals(2, game.getPlayer().getHand().size());
        assertEquals(BlackjackGame.RoundState.PLAYER_TURN, game.getState());
        assertEquals(0, game.getPlayer().getStats().getRoundsPlayed());
    }

    @Test
    void dealerTurnAdvancesOneCardAtATime(){
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck deck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.TEN),
            new Card(Card.Suit.SPADES, Card.Rank.TWO),
            new Card(Card.Suit.CLUBS, Card.Rank.THREE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
            new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR),
            new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN)
        ));
        game.startRound(1000, deck);
        game.beginDealerTurn();
        assertEquals(BlackjackGame.RoundState.DEALER_TURN, game.getState());
        assertEquals(2, game.getDealer().getHand().size());
        game.stepDealerTurn();
        assertEquals(BlackjackGame.RoundState.DEALER_TURN, game.getState());
        assertEquals(3, game.getDealer().getHand().size());
        game.stepDealerTurn();
        assertEquals(BlackjackGame.RoundState.ROUND_OVER, game.getState());
        assertEquals(4, game.getDealer().getHand().size());
        assertEquals(11000, game.getPlayer().getBalanceCents());
        assertThrows(IllegalStateException.class, () -> game.stepDealerTurn());
    }
}