package blackjack;

public class PlayerStats{
    private int wins;
    private int losses;
    private int pushes;

    public PlayerStats(){

    }

    public PlayerStats(int wins, int losses, int pushes){
        if(wins < 0 || losses < 0 || pushes < 0){
            throw new IllegalArgumentException("Invalid, negative counter");
        }
        this.wins = wins;
        this.losses = losses;
        this.pushes = pushes;
    }

    public int getWins(){
        return wins;
    }

    public int getLosses(){
        return losses;
    }

    public int getPushes(){
        return pushes;
    }

    public int getRoundsPlayed(){
        return wins + losses + pushes;
    }

    public void recordResult(BlackjackGame.RoundResult result){
        switch(result){
            case PLAYER_WIN:
                wins++;
                break;
            case DEALER_WIN:
                losses++;
                break;
            case PUSH:
                pushes++;
                break;
        }
    }
}