package blackjack;

public class HitCheck{
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame(10000, false);
        try {
            game.hit();
            System.out.println("FAIL: allowed player to hit on start");
        } catch (IllegalStateException e) {
            System.out.println("PASS: rejected player to hit on start");
        }
        game.startRound(1000);
        while(game.getState() == BlackjackGame.RoundState.PLAYER_TURN){
            game.hit();
            System.out.println(game.getPlayer().getHand().getScore());
        }
        System.out.println(game.getResult());
        try {
            game.hit();
            System.out.println("FAIL: allowed player to hit when not player turn");
        } catch (IllegalStateException e) {
            System.out.println("PASS: rejected player to hit when not player turn");
        }

    }
}