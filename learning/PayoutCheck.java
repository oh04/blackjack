package blackjack;

import java.util.List;

public class PayoutCheck{
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame(10000, false);
        Deck preparedDeck = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.NINE),
            new Card(Card.Suit.SPADES, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        ));
        game.startRound(1000, preparedDeck);
        System.out.println(game.getState() + " " + game.getResult() + " " + game.getPlayer().getBalanceCents() + " " + game.getBetCents());
        game.getResult();
        game.getResult();
        System.out.println(game.getPlayer().getBalanceCents());
        checkEquals("Blackjack win balance", 11500, game.getPlayer().getBalanceCents());
        checkEquals("Blackjack win: wins", 1, game.getPlayer().getStats().getWins());
        checkEquals("Blackjack win: losses", 0, game.getPlayer().getStats().getLosses());
        checkEquals("Blackjack win: pushes", 0, game.getPlayer().getStats().getPushes());
        checkEquals("Blackjack win: rounds played", 1, game.getPlayer().getStats().getRoundsPlayed());

        BlackjackGame game2 = new BlackjackGame(10000, false);
        Deck preparedDeck2 = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.SEVEN),
            new Card(Card.Suit.SPADES, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN)
        ));
        game2.startRound(1000, preparedDeck2);
        game2.stand();
        System.out.println(game2.getState() + " " + game2.getResult() + " " + game2.getPlayer().getBalanceCents() + " " + game2.getBetCents());
        game2.getResult();
        game2.getResult();
        System.out.println(game2.getPlayer().getBalanceCents());
        checkEquals("Normal win balance", 11000, game2.getPlayer().getBalanceCents());
        checkEquals("Normal win: wins", 1, game2.getPlayer().getStats().getWins());
        checkEquals("Normal win: losses", 0, game2.getPlayer().getStats().getLosses());
        checkEquals("Normal win: pushes", 0, game2.getPlayer().getStats().getPushes());
        checkEquals("Normal win: rounds played", 1, game2.getPlayer().getStats().getRoundsPlayed());

        BlackjackGame game3 = new BlackjackGame(10000, false);
        Deck preparedDeck3 = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            new Card(Card.Suit.SPADES, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.JACK)
        ));
        game3.startRound(1000, preparedDeck3);
        game3.stand();
        System.out.println(game3.getState() + " " + game3.getResult() + " " + game3.getPlayer().getBalanceCents() + " " + game3.getBetCents());
        game3.getResult();
        game3.getResult();
        System.out.println(game3.getPlayer().getBalanceCents());
        checkEquals("Push balance", 10000, game3.getPlayer().getBalanceCents());
        checkEquals("Push: wins", 0, game3.getPlayer().getStats().getWins());
        checkEquals("Push: losses", 0, game3.getPlayer().getStats().getLosses());
        checkEquals("Push: pushes", 1, game3.getPlayer().getStats().getPushes());
        checkEquals("Push: rounds played", 1, game3.getPlayer().getStats().getRoundsPlayed());

        BlackjackGame game4 = new BlackjackGame(10000, false);
        Deck preparedDeck4 = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.JACK)
        ));
        game4.startRound(1000, preparedDeck4);
        game4.stand();
        System.out.println(game4.getState() + " " + game4.getResult() + " " + game4.getPlayer().getBalanceCents() + " " + game4.getBetCents());
        game4.getResult();
        game4.getResult();
        System.out.println(game4.getPlayer().getBalanceCents());
        checkEquals("Loss balance", 9000, game4.getPlayer().getBalanceCents());
        checkEquals("Loss: wins", 0, game4.getPlayer().getStats().getWins());
        checkEquals("Loss: losses", 1, game4.getPlayer().getStats().getLosses());
        checkEquals("Loss: pushes", 0, game4.getPlayer().getStats().getPushes());
        checkEquals("Loss: rounds played", 1, game4.getPlayer().getStats().getRoundsPlayed());


        BlackjackGame game5 = new BlackjackGame(10000, false);
        Deck preparedDeck5 = new Deck(List.of(
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
            new Card(Card.Suit.CLUBS, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        ));
        game5.startRound(1000, preparedDeck5);
        System.out.println(game5.getState() + " " + game5.getResult() + " " + game5.getPlayer().getBalanceCents() + " " + game5.getBetCents());
        game5.getResult();
        game5.getResult();
        System.out.println(game5.getPlayer().getBalanceCents());
        checkEquals("Both blackjack balance", 10000, game5.getPlayer().getBalanceCents());
        checkEquals("Both blackjacks: wins", 0, game5.getPlayer().getStats().getWins());
        checkEquals("Both blackjacks: losses", 0, game5.getPlayer().getStats().getLosses());
        checkEquals("Both blackjacks: pushes", 1, game5.getPlayer().getStats().getPushes());
        checkEquals("Both blackjacks: rounds played", 1, game5.getPlayer().getStats().getRoundsPlayed());

    }

    private static void checkEquals(String label, int expected, int actual){
        if(expected != actual){
            throw new AssertionError("Label: " + label + " " + "Expected: " + expected + " " + "Actual: " + actual);
        }else{
            System.out.println("PASS: " + label);
        }
    }
}