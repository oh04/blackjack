package blackjack;

public class BetCheck{
    public static void main (String[] args){
        BlackjackGame game = new BlackjackGame(10000, false);
        try {
            game.startRound(0);
            System.out.println("FAIL: allowed 0 bet");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: rejected 0 bet");
        }
        System.out.println(game.getPlayer().getBalanceCents());
        System.out.println(game.getState());
        System.out.println(game.getBetCents());
        try {
            game.startRound(-1000);
            System.out.println("FAIL: allowed negative bet");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: rejected negative bet");
        }
        System.out.println(game.getPlayer().getBalanceCents());
        System.out.println(game.getState());
        System.out.println(game.getBetCents());
        try {
            game.startRound(10002);
            System.out.println("FAIL: allowed bet larger than balance");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: rejected bet larger than balance");
        }
        System.out.println(game.getPlayer().getBalanceCents());
        System.out.println(game.getState());
        System.out.println(game.getBetCents());
        try {
            game.startRound(999);
            System.out.println("FAIL: allowed odd bet");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: rejected odd bet");
        }
        System.out.println(game.getPlayer().getBalanceCents());
        System.out.println(game.getState());
        System.out.println(game.getBetCents());
        game.startRound(1000);
        System.out.println(game.getPlayer().getBalanceCents());
        System.out.println(game.getState());
        System.out.println(game.getBetCents());
    }
}