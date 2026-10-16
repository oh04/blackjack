package blackjack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class PlayerSaveTest {
    @TempDir
    Path tempDir;

    @Test
    void saveAndLoadPreservesProgress() throws IOException{
        Player player = new Player(12345, new PlayerStats(3, 2, 1));
        Path path = tempDir.resolve("player.properties");
        PlayerSave.save(player, path);
        Player loadedPlayer = PlayerSave.load(path);
        assertEquals(player.getBalanceCents(), loadedPlayer.getBalanceCents());
        assertEquals(player.getStats().getWins(), loadedPlayer.getStats().getWins());
        assertEquals(player.getStats().getLosses(), loadedPlayer.getStats().getLosses());
        assertEquals(player.getStats().getPushes(), loadedPlayer.getStats().getPushes());
        assertEquals(player.getStats().getRoundsPlayed(), loadedPlayer.getStats().getRoundsPlayed());
        assertEquals(player.getHand().size(), loadedPlayer.getHand().size());
    }

    @Test
    void loadRejectsNonnumericalBalance() throws IOException{
        Path path = tempDir.resolve("invalid-player.properties");
        Files.writeString(path, "balanceCents=hello\nwins=3\nlosses=2\npushes=1\n");
        assertThrows(IllegalArgumentException.class, () -> PlayerSave.load(path));
    }

    @Test
    void loadRejectsNegativeStats() throws IOException{
        Path path = tempDir.resolve("invalid-player.properties");
        Files.writeString(path, "balanceCents=10000\nwins=-1\nlosses=2\npushes=1\n");
        assertThrows(IllegalArgumentException.class, () -> PlayerSave.load(path));
    }
}