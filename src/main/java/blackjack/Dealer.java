package blackjack;

public class Dealer{

    private final Hand hand = new Hand();
    private final boolean hitSoft17;

    public Dealer(boolean hitSoft17){
        this.hitSoft17 = hitSoft17;

    }

    public Hand getHand(){
        return hand;
    }

    public boolean shouldHit(){
        if(hand.getScore() < 17){
            return true;
        } else if (hand.getScore() == 17 && hitSoft17 && hand.isSoft()){
            return true;
        }
        return false;
    }

    public void playTurn(Deck deck){
        while(shouldHit()){
            hand.addCard(deck.draw());
        }
    }

    public Card getUpCard(){
        return hand.getCard(0);
    }
}