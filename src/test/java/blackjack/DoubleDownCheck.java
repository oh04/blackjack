package blackjack;

import java.util.List;

public class DoubleDownCheck{
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck preparedDeck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.TEN),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
            new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE)
        ));
        game.startRound(1000, preparedDeck);
        System.out.println(game.canDoubleDown());
        game.doubleDown();
        System.out.println(game.getState() + " " + game.getResult() + " " + game.getPlayer().getBalanceCents() + " " + game.getBetCents() + " " + game.getPlayer().getHand().size());

        BlackjackGame game2 = new BlackjackGame(10000, false);
        Deck preparedDeck2 = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.NINE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX)
        ));
        game2.startRound(1000, preparedDeck2);
        System.out.println(game2.canDoubleDown());
        game2.doubleDown();
        System.out.println(game2.getState() + " " + game2.getResult() + " " + game2.getPlayer().getBalanceCents() + " " + game2.getBetCents() + " " + game2.getPlayer().getHand().size());

        
        BlackjackGame game3 = new BlackjackGame(2000, false);
        Deck preparedDeck3 = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.TEN),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
            new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE)
        ));
        game3.startRound(1000, preparedDeck3);
        System.out.println(game3.canDoubleDown());
        game3.doubleDown();
        System.out.println(game3.getState() + " " + game3.getResult() + " " + game3.getPlayer().getBalanceCents() + " " + game3.getBetCents() + " " + game3.getPlayer().getHand().size());

        BlackjackGame game4 = new BlackjackGame(1500, false);
        Deck preparedDeck4 = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.NINE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.SIX)
        ));
        game4.startRound(1000, preparedDeck4);
        System.out.println(game4.canDoubleDown());
        try{
            game4.doubleDown();
            System.out.println("FAIL: accepted double down with insufficient balance");
        }catch (IllegalStateException e){
            System.out.println("PASS: rejected double down with insufficient balance");
        }
        System.out.println(game4.getState() + " " + game4.getPlayer().getBalanceCents() + " " + game4.getBetCents() + " " + game4.getPlayer().getHand().size());
    }
}