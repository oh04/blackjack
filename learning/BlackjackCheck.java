package blackjack;

public class BlackjackCheck{
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame(10000, true);
        System.out.println(game.getState());
        game.startRound(1000);
        System.out.println(game.getState());
        System.out.println(game.getPlayer().getHand().toString());
        System.out.println(game.getDealer().getHand().toString());
        
        if (game.getState() == BlackjackGame.RoundState.PLAYER_TURN) {
            try{
                game.startRound(1000);
                System.out.println("FAIL: allowed PLAYER_TURN on start");
            } catch (IllegalStateException e){
                System.out.println("PASS: rejected PLAYER_TURN on start");
            }

            game.stand();
        }
        System.out.println(game.getResult());
        try {
            game.stand();
            System.out.println("FAIL: allowed stand");
        } catch (IllegalStateException e) {
            System.out.println("PASS: rejected stand");
        }
        System.out.println(game.getPlayer().getHand().toString());
        System.out.println(game.getDealer().getHand().toString());
        System.out.println(game.getPlayer().getBalanceCents());
        game.startRound(1000);
        System.out.println(game.getState());
    }
}