package blackjack;

public class Player{
    private final Hand hand = new Hand();
    private int balanceCents;
    private final PlayerStats stats;

    public Player(int startingBalanceCents, PlayerStats stats){
        if(startingBalanceCents < 0){
            throw new IllegalArgumentException("Balance cannot be negative.");
        }else if(stats == null){
            throw new IllegalArgumentException("Null player stats.");
        }else{
            this.balanceCents = startingBalanceCents;
            this.stats = stats;
        }
    }

    public Player(int startingBalanceCents){
        this(startingBalanceCents, new PlayerStats());
    }

    public Hand getHand(){
        return hand;
    }

    public int getBalanceCents(){
        return balanceCents;
    }

    public PlayerStats getStats(){
        return stats;
    }

    public void credit(int amountCents){
        if(amountCents < 0){
            throw new IllegalArgumentException("Amount cannot be negative.");
        }else{
            balanceCents += amountCents;
        }
    }

    public void debit(int amountCents){
        if(amountCents < 0){
            throw new IllegalArgumentException("Amount cannot be negative.");
        }else if(amountCents > balanceCents){
            throw new IllegalArgumentException("Amount cannot be greater than balance.");
        }else{
            balanceCents -= amountCents;
        }
    }
}