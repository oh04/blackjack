package blackjack;

public class PlayerCheck{
    public static void main(String[] args) {
        try{
            new Player(-10000);
            System.out.println("FAIL: allowed negative starting balance");
        } catch(IllegalArgumentException e){
            System.out.println("PASS: rejected negative starting balance");
        }
        Player player = new Player(10000);
        System.out.println(player.getBalanceCents());
        player.debit(2500);
        System.out.println(player.getBalanceCents());
        player.credit(1000);
        System.out.println(player.getBalanceCents());
        try {
            player.credit(-1000);
            System.out.println("FAIL: allowed negative credit");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: rejected negative credit");
        }
        System.out.println(player.getBalanceCents());
        try {
            player.debit(9000);
            System.out.println("FAIL: allowed overspending");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: rejected overspending");
        }
        System.out.println(player.getBalanceCents());
        try {
            player.debit(-1000);
            System.out.println("FAIL: allowed negative debit");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: rejected negative debit");
        }
        System.out.println(player.getBalanceCents());
        player.debit(8500);
        System.out.println(player.getBalanceCents());
    }
}