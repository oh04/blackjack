package blackjack;

public class Card{
    
    public enum Suit{HEARTS, DIAMONDS, CLUBS, SPADES}
    public enum Rank{ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}

    private final Suit suit;
    private final Rank rank;

    public Card (Suit s, Rank r){
        suit = s;
        rank = r;
    }

    public Suit getSuit(){
        return suit;
    }

    public Rank getRank(){
        return rank;
    }

    public int getValue(){
        switch(rank){
            case ACE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case JACK:
                return 10;
            case QUEEN:
                return 10;
            case KING:
                return 10;
            default:
                throw new IllegalStateException("Unexpected rank: " + rank);
        }
    }

    @Override 
    public String toString(){
        return rank + " of " + suit;
    }
}