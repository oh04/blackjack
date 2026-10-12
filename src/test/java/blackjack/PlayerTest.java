package blackjack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test 
    void debitReducesBalance(){
        Player player = new Player(10000);
        player.debit(2500);
        assertEquals(7500, player.getBalanceCents());
    }

    @Test
    void debitRejectsOverspendingWithoutChangingBalance(){
        Player player = new Player(1000);
        assertThrows(IllegalArgumentException.class, () -> player.debit(1002));
        assertEquals(1000, player.getBalanceCents());
    }

    @Test
    void negativeStartingBalanceIsRejected(){
        assertThrows(IllegalArgumentException.class, () -> new Player(-1));
    }
}