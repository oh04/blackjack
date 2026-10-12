package blackjack;

public class DeckCheck{
    public static void main(String[] args){
        Deck deck = new Deck();
        System.out.println(deck.remainingCards());
        deck.shuffle();
        Card drawnCard = deck.draw();
        System.out.println(drawnCard);
        System.out.println(deck.remainingCards());
    }
}