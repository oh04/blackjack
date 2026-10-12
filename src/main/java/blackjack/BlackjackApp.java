package blackjack;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

public class BlackjackApp{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Path savePath = Path.of("player.properties");
        Player player;
        if(Files.exists(savePath)){
            try{
                player = PlayerSave.load(savePath);
            }catch(IOException | IllegalArgumentException e){
                System.out.println("Save file couldn't be loaded.");
                input.close();
                return;
            }
        }else{
            player = new Player(10000);
        }
        BlackjackGame game = new BlackjackGame(player, false);
        session:
        while(game.getPlayer().getBalanceCents() >= 2){
            System.out.println("Available balance: " + formatMoney(game.getPlayer().getBalanceCents()));
            while(true){
                System.out.print("Enter your bet in cents (even amounts only) or q to quit: ");
                if(!input.hasNextLine()){
                    break session;
                }
                String bet = input.nextLine().trim();
                if(bet.equalsIgnoreCase("q")){
                    break session;
                }
                try{
                    game.startRound(Integer.parseInt(bet));
                    break;
                }catch(NumberFormatException e){
                    System.out.println("Enter a whole number");
                }catch(IllegalArgumentException e){
                    System.out.println("Bet must be positive, even, and within your balance.");
                }
            }
            printPlayerHand(game);
            System.out.println("Your balance: " + formatMoney(game.getPlayer().getBalanceCents()) + "\n");
            playPlayerTurn(game, input);
            printRoundResult(game);
        }
        try{
            PlayerSave.save(game.getPlayer(), savePath);
            System.out.println("Progress saved.");
        }catch(IOException e){
            System.out.println("Could not save progress: " + e.getMessage());
        }
        if(game.getPlayer().getBalanceCents() < 2){
            System.out.println("Not enough money left for a valid bet");
        }
        System.out.println("Final balance: " + formatMoney(game.getPlayer().getBalanceCents()));
        System.out.println("Stats:");
        System.out.println("Rounds Played: " + game.getPlayer().getStats().getRoundsPlayed());
        System.out.println("Wins: " + game.getPlayer().getStats().getWins() + "\tLosses: " + game.getPlayer().getStats().getLosses() + "\tPushes: " + game.getPlayer().getStats().getPushes());
        System.out.println("Goodbye");

        input.close();
    }

    private static String formatMoney(int cents){
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }

    private static void printPlayerHand(BlackjackGame game){
        System.out.println("Your cards: " + game.getPlayer().getHand().toString());
        System.out.println("Your score: " + game.getPlayer().getHand().getScore());
    }

    private static void playPlayerTurn(BlackjackGame game, Scanner input){
        while(game.getState() == BlackjackGame.RoundState.PLAYER_TURN){
            System.out.println("Dealer: " + game.getDealer().getUpCard().toString() + " [Hidden card]");
            if(game.canDoubleDown()){
                System.out.print("Enter h to hit, s to stand, or d to double down: ");
            }else{
                System.out.print("Enter h to hit or s to stand: ");
            }
            if(!input.hasNextLine()){
                System.out.println("Input ended. standing and saving.");
                game.stand();
                return;
            }
            String choice = input.nextLine().trim();  
            if(choice.equalsIgnoreCase("d")){
                if(game.canDoubleDown()){
                    game.doubleDown();
                    printPlayerHand(game);
                }else{
                    System.out.println("You can't double down");
                }
            }else if(choice.equalsIgnoreCase("h")){
                game.hit();
                printPlayerHand(game);
            }else if(choice.equalsIgnoreCase("s")){
                game.stand();
            }else{
                System.out.println("Invalid command");
            }
        }
    }
    private static void printRoundResult(BlackjackGame game){
        System.out.println("Dealer: " + game.getDealer().getHand().toString());
        System.out.println("Dealer's score: " + game.getDealer().getHand().getScore());
        System.out.println("Result: " + game.getResult());
        System.out.println("Your balance: " + formatMoney(game.getPlayer().getBalanceCents()));
    }
}