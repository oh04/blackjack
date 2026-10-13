package blackjack;

public class BlackjackGame{

    public enum RoundState{NOT_STARTED, PLAYER_TURN, DEALER_TURN, ROUND_OVER};
    public enum RoundResult{PLAYER_WIN, DEALER_WIN, PUSH};

    private final Player player;
    private final Dealer dealer;
    private Deck deck;
    private int currentBetCents;
    private RoundState state = RoundState.NOT_STARTED;

    public BlackjackGame(Player player, boolean hitSoft17){
        if(player == null){
            throw new IllegalArgumentException("Player can't be null");
        }
        this.player = player;
        dealer = new Dealer(hitSoft17);
        deck = new Deck();
    }

    public BlackjackGame(int startingBalanceCents, boolean hitSoft17){
        this(new Player(startingBalanceCents), hitSoft17);
    }

    public Player getPlayer(){
        return player;
    }

    public Dealer getDealer(){
        return dealer;
    }

    public RoundState getState(){
        return state;
    }

    public int getBetCents(){
        return currentBetCents;
    }

    public void startRound(int betCents, Deck roundDeck){
        if(state == RoundState.PLAYER_TURN || state == RoundState.DEALER_TURN){
            throw new IllegalStateException("Cannot start a new round during the player or dealer's turn");
        }else{
            if(betCents <= 0 || betCents > player.getBalanceCents() || betCents % 2 == 1){
                throw new IllegalArgumentException("Invalid bet");
            }
            player.debit(betCents);
            currentBetCents = betCents;
            player.getHand().clear();
            dealer.getHand().clear();
            deck = roundDeck;
            player.getHand().addCard(deck.draw());
            dealer.getHand().addCard(deck.draw());
            player.getHand().addCard(deck.draw());
            dealer.getHand().addCard(deck.draw());
            if(player.getHand().getScore() == 21 || dealer.getHand().getScore() == 21){
                finishRound();
            }else{
                state = RoundState.PLAYER_TURN;
            }
        }
    }

    public void startRound(int betCents){
        Deck deck = new Deck();
        deck.shuffle();
        startRound(betCents, deck);
    }

    public void stand(){
        if(state != RoundState.PLAYER_TURN){
            throw new IllegalStateException("Cannot stand unless player turn");
        }else{
            dealer.playTurn(deck);
            finishRound();
        }
    }

    public void hit(){
        hit(false);
    }

    public void hit(boolean animateDealer){
        if(state != RoundState.PLAYER_TURN){
            throw new IllegalStateException("Cannot hit unless player turn");
        }else{
            player.getHand().addCard(deck.draw());
            if(player.getHand().getScore() > 21){
                finishRound();
            }else if(player.getHand().getScore() == 21){
                if(animateDealer){
                    beginDealerTurn();
                }else{
                    stand();
                }
            }
        }
    }

    public boolean canDoubleDown(){
        if(state == RoundState.PLAYER_TURN && player.getHand().size() == 2 && player.getBalanceCents() >= currentBetCents){
            return true;
        }
        return false;
    }

    public void doubleDown(){
        doubleDown(false);
    }

    public void doubleDown(boolean animateDealer){
        if(!canDoubleDown()){
            throw new IllegalStateException("Player cannot double down");
        }
        player.debit(currentBetCents);
        currentBetCents *= 2;
        player.getHand().addCard(deck.draw());
        if(player.getHand().isBust()){
            finishRound();
        }else if(animateDealer){
            beginDealerTurn();   
        }else{
            stand();
        }
    }

    public RoundResult getResult(){
        if(state != RoundState.ROUND_OVER){
            throw new IllegalStateException("Cannot get result unless round is over");
        }else{
            if(player.getHand().isBust()){
                return RoundResult.DEALER_WIN;
            }else if(dealer.getHand().isBust()){
                return RoundResult.PLAYER_WIN;
            }else if(dealer.getHand().getScore() > player.getHand().getScore()){
                return RoundResult.DEALER_WIN;
            }else if(dealer.getHand().getScore() < player.getHand().getScore()){
                return RoundResult.PLAYER_WIN;
            }else{
                return RoundResult.PUSH;
            }
        }
    }

    private void finishRound(){
        state = RoundState.ROUND_OVER;
        RoundResult result = getResult();
        player.getStats().recordResult(result);
        if(result == RoundResult.PUSH){
            player.credit(currentBetCents);
        }else if(result == RoundResult.PLAYER_WIN && getPlayer().getHand().isBlackjack()){
            player.credit(currentBetCents + currentBetCents + (currentBetCents / 2));
        }else if(result == RoundResult.PLAYER_WIN){
            player.credit(currentBetCents + currentBetCents);
        }
    }

    public void beginDealerTurn(){
        if(state != RoundState.PLAYER_TURN){
            throw new IllegalStateException();
        }
        state = RoundState.DEALER_TURN;
    }

    public void stepDealerTurn(){
        if(state == RoundState.DEALER_TURN){
            if(dealer.shouldHit()){
                dealer.getHand().addCard(deck.draw());
            }
            if(!dealer.shouldHit()){
                finishRound();
            }
        }else{
            throw new IllegalStateException("Can't step dealer outside dealer turn");
        }
    }
}