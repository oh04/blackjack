package blackjack;

import java.util.Properties;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.Writer;
import java.io.IOException;
import java.io.Reader;

public class PlayerSave{

    public static void save(Player player, Path path) throws IOException{
        Properties prop = new Properties();
        prop.setProperty("balanceCents", Integer.toString(player.getBalanceCents()));
        prop.setProperty("wins", Integer.toString(player.getStats().getWins()));
        prop.setProperty("losses", Integer.toString(player.getStats().getLosses()));
        prop.setProperty("pushes", Integer.toString(player.getStats().getPushes()));
        try(Writer writer = Files.newBufferedWriter(path)){
            prop.store(writer, "Blackjack player save");
        }
    }

    public static Player load(Path path) throws IOException{
        Properties prop = new Properties();
        try(Reader reader = Files.newBufferedReader(path)){
            prop.load(reader);
        }
        int balanceCents = Integer.parseInt(prop.getProperty("balanceCents"));
        int wins = Integer.parseInt(prop.getProperty("wins"));
        int losses = Integer.parseInt(prop.getProperty("losses"));
        int pushes = Integer.parseInt(prop.getProperty("pushes"));
        PlayerStats stats = new PlayerStats(wins, losses, pushes);
        return new Player(balanceCents, stats);
    }
}