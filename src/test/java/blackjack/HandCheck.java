package blackjack;

public class HandCheck{

    public static void main(String[] args){
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SIX));
        System.out.println(hand.getScore());
        System.out.println(hand.isBust());
        System.out.println(hand.isBlackjack());
        System.out.println(hand.isSoft());
        Hand hand2 = new Hand();
        hand2.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand2.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand2.addCard(new Card(Card.Suit.CLUBS, Card.Rank.NINE));
        System.out.println(hand2.getScore());
        System.out.println(hand2.isBust());
        System.out.println(hand2.isBlackjack());
        System.out.println(hand2.isSoft());
        System.out.println(hand2.toString());
        Hand hand3 = new Hand();
        hand3.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        hand3.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));
        System.out.println(hand3.getScore());
        System.out.println(hand3.isBust());
        System.out.println(hand3.isBlackjack());
        System.out.println(hand3.isSoft());
        hand3.clear();
        System.out.println(hand3.toString());
    }
}