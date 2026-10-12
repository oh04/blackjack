package blackjack;

import java.util.List;
import java.util.ArrayList;

public class Hand{

    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card){
        cards.add(card);
    }

    public int getScore(){
        int total = 0;
        boolean hasAce = false;
        for(Card card : cards){
            total += card.getValue();
            if(card.getRank() == Card.Rank.ACE){
                hasAce = true;
            }
        }
        if(hasAce && total + 10 <= 21){
            total += 10;
        }
        return total;
    }

    public boolean isBust(){
        return getScore() > 21;
    }

    public boolean isBlackjack(){
        return cards.size() == 2 && getScore() == 21;
    }

    public boolean isSoft(){
        int total = 0;
        boolean hasAce = false;
        for(Card card : cards){
            total += card.getValue();
            if(card.getRank() == Card.Rank.ACE){
                hasAce = true;
            }
        }
        if(hasAce && total + 10 <= 21){
            return true;
        }
        return false;
    }

    public void clear(){
        cards.clear();
    }

    public int size(){
        return cards.size();
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    @Override
    public String toString(){
        String s = "";
        for(Card card : cards){
            if(!s.isEmpty()){
                s += ", ";
            }
            s += card.toString();
        }
        return s;
    }
}