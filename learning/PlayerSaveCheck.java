package blackjack;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

public class PlayerSaveCheck{
    public static void main(String[] args) throws IOException{
        Player player = new Player(12345, new PlayerStats(3, 2, 1));
        Path path = Path.of("player-save-check.properties");
        PlayerSave.save(player, path);
        Player loadedPlayer = PlayerSave.load(path);
        checkEquals("Balance", 12345, loadedPlayer.getBalanceCents());
        checkEquals("Wins", 3, loadedPlayer.getStats().getWins());
        checkEquals("Losses", 2, loadedPlayer.getStats().getLosses());
        checkEquals("Pushes", 1, loadedPlayer.getStats().getPushes());
        checkEquals("Rounds played", 6, loadedPlayer.getStats().getRoundsPlayed());
        checkEquals("Player hand size", 0, loadedPlayer.getHand().size());
        Path inPath = Path.of("invalid-player-save-check.properties");
        Files.writeString(inPath, "balanceCents=hello\nwins=3\nlosses=2\npushes=1\n");
        try{
            loadedPlayer = PlayerSave.load(inPath);
            throw new AssertionError("Accepted a nonnumeric balance");
        }catch(IllegalArgumentException e){
            System.out.println("PASS: rejected invalid balance.");
        }
        Files.writeString(inPath, "balanceCents=10000\nwins=-1\nlosses=2\npushes=1\n");
        try{
            loadedPlayer = PlayerSave.load(inPath);
            throw new AssertionError("Accepted a negative win count");
        }catch(IllegalArgumentException e){
            System.out.println("PASS: rejected invalid win counter.");
        }
        System.out.println(loadedPlayer.getBalanceCents() + " " + loadedPlayer.getStats().getWins() + " " + loadedPlayer.getStats().getLosses() + " " + loadedPlayer.getStats().getPushes() + " " + loadedPlayer.getStats().getRoundsPlayed());
    }

    private static void checkEquals(String label, int expected, int actual){
        if(expected != actual){
            throw new AssertionError("Label: " + label + " " + "Expected: " + expected + " " + "Actual: " + actual);
        }else{
            System.out.println("PASS: " + label);
        }
    }
}