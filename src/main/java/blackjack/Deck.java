package blackjack;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{

    private final List<Card> cards = new ArrayList<>();

    public Deck(){
        for(Card.Suit suit : Card.Suit.values()){
            for(Card.Rank rank : Card.Rank.values()){
                Card c = new Card(suit, rank);
                cards.add(c);
            }
        }
    }

    public Deck(List<Card> orderedCards){
        cards.addAll(orderedCards);
    }

    public int remainingCards(){
        return cards.size();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card draw(){
        if(cards.isEmpty()){
            throw new IllegalStateException("Cannot draw from an empty deck");
        }
        return cards.remove(cards.size() - 1);
    }
}