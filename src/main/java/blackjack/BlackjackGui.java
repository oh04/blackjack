package blackjack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;


public class BlackjackGui{

    private final BlackjackGame game = new BlackjackGame(10000, false);
    private final JLabel balanceLabel = new JLabel("Balance: $100.00");
    private final JLabel dealerCardsLabel = new JLabel("Dealer cards:-");
    private final JLabel dealerScoreLabel = new JLabel("Dealer score:-");
    private final JLabel playerCardsLabel = new JLabel("Your cards:-");
    private final JLabel playerScoreLabel = new JLabel("Your score:-");
    private final JLabel statusLabel = new JLabel("Place a bet to begin");
    private final JTextField betField = new JTextField("1000", 8);
    private final JButton dealButton = new JButton("Deal");
    private final JButton hitButton = new JButton("Hit");
    private final JButton standButton = new JButton("Stand");
    private final JButton doubleButton = new JButton("Double down");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {BlackjackGui gui = new BlackjackGui();gui.createAndShowWindow();});
    }

    private void createAndShowWindow(){
        JFrame frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.add(balanceLabel);
        frame.add(panel, BorderLayout.NORTH);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(5, 1));
        panel2.add(dealerCardsLabel);
        panel2.add(dealerScoreLabel);
        panel2.add(playerCardsLabel);
        panel2.add(playerScoreLabel);
        panel2.add(statusLabel);
        frame.add(panel2, BorderLayout.CENTER);
        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Bet (cents):"));
        panel3.add(betField);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        doubleButton.setEnabled(false);
        panel3.add(dealButton);
        panel3.add(hitButton);
        panel3.add(standButton);
        panel3.add(doubleButton);
        frame.add(panel3, BorderLayout.SOUTH);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        dealButton.addActionListener(event -> {
            try {
                int betCents = Integer.parseInt(betField.getText().trim());
                game.startRound(betCents);
                refreshView();
            } catch (NumberFormatException e) {
                statusLabel.setText("Enter a whole number of cents.");
            } catch (IllegalArgumentException e) {
                statusLabel.setText("Bet must be positive, even, and within your balance.");
            }
        });
        hitButton.addActionListener(event -> {
            game.hit();
            refreshView();
        });
        standButton.addActionListener(event -> {
            game.stand();
            refreshView();
        });
        doubleButton.addActionListener(event -> {
            game.doubleDown();
            refreshView();
        });
        refreshView();
        frame.setVisible(true);
    }

    private void refreshView(){
        balanceLabel.setText("Balance: " + formatMoney(game.getPlayer().getBalanceCents()));
        playerCardsLabel.setText("Your cards: " + game.getPlayer().getHand());
        playerScoreLabel.setText("Your score: " + game.getPlayer().getHand().getScore());
        if(game.getState() == BlackjackGame.RoundState.PLAYER_TURN){
            dealerCardsLabel.setText("Dealer's cards: " + game.getDealer().getUpCard() + ", [Hidden card]");
            dealerScoreLabel.setText("Dealer score: hidden");
            statusLabel.setText("Your turn");
        }else if(game.getState() == BlackjackGame.RoundState.ROUND_OVER){
            dealerCardsLabel.setText("Dealer's cards: " + game.getDealer().getHand());
            dealerScoreLabel.setText("Dealer score: " + game.getDealer().getHand().getScore());
            statusLabel.setText("Result: " + game.getResult());
        }else{
            dealerCardsLabel.setText("Dealer cards:-");
            dealerScoreLabel.setText("Dealer score:-");
            statusLabel.setText("Place a bet to begin");
        }

        boolean playerTurn = game.getState() == BlackjackGame.RoundState.PLAYER_TURN;
        hitButton.setEnabled(playerTurn);
        standButton.setEnabled(playerTurn);
        doubleButton.setEnabled(game.canDoubleDown());
        dealButton.setEnabled(!playerTurn && game.getPlayer().getBalanceCents() >= 2);
        betField.setEnabled(!playerTurn);
    }

    private static String formatMoney(int cents){
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }
}