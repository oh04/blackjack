package blackjack;

public class DealerCheck{
    public static void main(String[] args) {
        Dealer dealer = new Dealer(true);
        dealer.getHand().addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.getHand().addCard(new Card(Card.Suit.SPADES, Card.Rank.SIX));
        System.out.println(dealer.shouldHit());
        Dealer dealer2 = new Dealer(true);
        dealer2.getHand().addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer2.getHand().addCard(new Card(Card.Suit.SPADES, Card.Rank.SIX));
        dealer2.getHand().addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        System.out.println(dealer2.shouldHit());
        Dealer dealer3 = new Dealer(false);
        dealer3.getHand().addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer3.getHand().addCard(new Card(Card.Suit.SPADES, Card.Rank.SIX));
        System.out.println(dealer3.shouldHit());
    }
}