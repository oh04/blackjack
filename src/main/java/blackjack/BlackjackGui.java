package blackjack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.Box;


public class BlackjackGui{

    private BlackjackGame game;
    private final JLabel balanceLabel = new JLabel("Balance: $100.00");
    private final JLabel statsLabel = new JLabel("");
    private final JLabel dealerScoreLabel = new JLabel("Dealer score:-");
    private final JLabel playerScoreLabel = new JLabel("Your score:-");
    private final JLabel statusLabel = new JLabel("Place a bet to begin");
    private final JTextField betField = new JTextField("1000", 8);
    private final JButton dealButton = new JButton("Deal");
    private final JButton hitButton = new JButton("Hit");
    private final JButton standButton = new JButton("Stand");
    private final JButton doubleButton = new JButton("Double down");
    private final Path savePath = Path.of("player.properties");
    private Timer dealerTimer;
    private final JPanel dealerCardsPanel = new JPanel();
    private final JPanel playerCardsPanel = new JPanel();
    private final JButton newProfileButton = new JButton("New Profile");

    public BlackjackGui() throws IOException{
        Player player;
        if(Files.exists(savePath)){
            player = PlayerSave.load(savePath);
        }else{
            player = new Player(10000);
        }
        game = new BlackjackGame(player, false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try{
                BlackjackGui gui = new BlackjackGui();gui.createAndShowWindow();
            }catch(IOException | IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, "Could not load player: " + e.getMessage(), "Load error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void createAndShowWindow(){
        JFrame frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                if(game.getState() == BlackjackGame.RoundState.PLAYER_TURN || game.getState() == BlackjackGame.RoundState.DEALER_TURN){
                    JOptionPane.showMessageDialog(null, "Finish the current round before closing.");
                    return;
                }else{
                    try{
                        PlayerSave.save(game.getPlayer(), savePath);
                        frame.dispose();
                    }catch(IOException e){
                        JOptionPane.showMessageDialog(null, "Error saving: " + e);
                    }
                }
            }
        });
        JPanel balancePanel = new JPanel();
        balancePanel.add(balanceLabel);
        balancePanel.add(statsLabel);
        frame.add(balancePanel, BorderLayout.NORTH);
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(22, 85, 57));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        for (JLabel label : new JLabel[]{dealerScoreLabel, playerScoreLabel, statusLabel}){
            label.setForeground(Color.WHITE);
            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        }
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        balanceLabel.setFont(new Font(null, Font.BOLD, 20));
        balancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tablePanel.setLayout(new BorderLayout(0, 15));
        dealerCardsPanel.setOpaque(false);
        playerCardsPanel.setOpaque(false);
        frame.add(tablePanel, BorderLayout.CENTER);
        JPanel dealerSection = new JPanel(new BorderLayout(0, 8));
        dealerSection.setOpaque(false);
        dealerSection.add(dealerScoreLabel, BorderLayout.NORTH);
        JScrollPane dealerScroll = new JScrollPane(dealerCardsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dealerScroll.setOpaque(false);
        dealerScroll.getViewport().setOpaque(false);
        dealerScroll.setBorder(BorderFactory.createEmptyBorder());
        dealerSection.add(dealerScroll, BorderLayout.CENTER);
        JPanel playerSection = new JPanel(new BorderLayout(0, 8));
        playerSection.setOpaque(false);
        playerSection.add(playerScoreLabel, BorderLayout.NORTH);
        JScrollPane playerScroll = new JScrollPane(playerCardsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        playerScroll.setOpaque(false);
        playerScroll.getViewport().setOpaque(false);
        playerScroll.setBorder(BorderFactory.createEmptyBorder());
        playerSection.add(playerScroll, BorderLayout.CENTER);
        JPanel handsPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        handsPanel.setOpaque(false);
        handsPanel.add(dealerSection);
        handsPanel.add(playerSection);
        tablePanel.add(handsPanel, BorderLayout.CENTER);
        tablePanel.add(statusLabel, BorderLayout.SOUTH);
        JPanel controlsPanel = new JPanel();
        controlsPanel.add(new JLabel("Bet (cents):"));
        controlsPanel.add(betField);
        dealerCardsPanel.setLayout(new BoxLayout(dealerCardsPanel, BoxLayout.X_AXIS));
        playerCardsPanel.setLayout(new BoxLayout(playerCardsPanel, BoxLayout.X_AXIS));
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        doubleButton.setEnabled(false);
        controlsPanel.add(dealButton);
        controlsPanel.add(hitButton);
        controlsPanel.add(standButton);
        controlsPanel.add(doubleButton);
        controlsPanel.add(newProfileButton);
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(controlsPanel, BorderLayout.SOUTH);
        frame.setSize(800, 700);
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
            game.hit(true);
            refreshView();
            if(game.getState() == BlackjackGame.RoundState.DEALER_TURN){
                runDealerAnimation();
            }
        });
        standButton.addActionListener(event -> {
            game.beginDealerTurn();
            runDealerAnimation();
        });
        doubleButton.addActionListener(event -> {
            game.doubleDown(true);
            refreshView();
            if(game.getState() == BlackjackGame.RoundState.DEALER_TURN){
                runDealerAnimation();
            }
        });
        newProfileButton.addActionListener(event -> {
            int choice = JOptionPane.showConfirmDialog(frame, "Reset your balance to $100.00 and clear all statistics?", "New Profile", JOptionPane.YES_NO_OPTION);

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
            BlackjackGame newGame = new BlackjackGame(new Player(10000), false);
            try{
                PlayerSave.save(newGame.getPlayer(), savePath);
                game = newGame;
                refreshView();

            }catch(IOException e){
                JOptionPane.showMessageDialog(frame, "Error saving: " + e);
            }
        });
        refreshView();
        frame.setVisible(true);
    }

    private void refreshView(){
        balanceLabel.setText("Balance: " + formatMoney(game.getPlayer().getBalanceCents()));
        statsLabel.setText("Wins: " + game.getPlayer().getStats().getWins() + " Losses: " + game.getPlayer().getStats().getLosses() + " Pushes: " + game.getPlayer().getStats().getPushes());
        playerScoreLabel.setText("Your score: " + game.getPlayer().getHand().getScore());
        updateCardPanel(playerCardsPanel, game.getPlayer().getHand(), false);
        updateCardPanel(dealerCardsPanel, game.getDealer().getHand(), game.getState() == BlackjackGame.RoundState.PLAYER_TURN);
        if(game.getState() == BlackjackGame.RoundState.PLAYER_TURN){
            dealerScoreLabel.setText("Dealer score: hidden");
            statusLabel.setText("Your turn");
        }else if(game.getState() == BlackjackGame.RoundState.DEALER_TURN){
            dealerScoreLabel.setText("Dealer score: " + game.getDealer().getHand().getScore());
            statusLabel.setText("Dealer's turn...");
        }else if(game.getState() == BlackjackGame.RoundState.ROUND_OVER){
            dealerScoreLabel.setText("Dealer score: " + game.getDealer().getHand().getScore());
            switch(game.getResult()){
                case PLAYER_WIN:
                    if(game.getPlayer().getHand().getScore() == 21 && game.getPlayer().getHand().size() == 2){
                        statusLabel.setText("Blackjack! You win.");
                    }else{
                        statusLabel.setText("You win.");
                    }
                    break;
                case DEALER_WIN:
                    if(game.getPlayer().getHand().getScore() > 21){
                        statusLabel.setText("You busted. Dealer wins.");
                    }else{
                        statusLabel.setText("Dealer wins.");
                    }
                    break;
                case PUSH:
                    statusLabel.setText("Push. Your bet is returned.");
                    break;
            }
        }else{
            dealerScoreLabel.setText("Dealer score:-");
            statusLabel.setText("Place a bet to begin");
        }

        boolean playerTurn = game.getState() == BlackjackGame.RoundState.PLAYER_TURN;
        boolean roundActive = playerTurn || game.getState() == BlackjackGame.RoundState.DEALER_TURN;
        hitButton.setEnabled(playerTurn);
        standButton.setEnabled(playerTurn);
        doubleButton.setEnabled(game.canDoubleDown());
        dealButton.setEnabled(!roundActive && game.getPlayer().getBalanceCents() >= 2);
        betField.setEnabled(!roundActive);

        if(!roundActive && game.getPlayer().getBalanceCents() < 2){
            statusLabel.setText("Not enough funds for another round.");
        }

        newProfileButton.setEnabled(!roundActive);
    }

    private static String formatMoney(int cents){
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }

    private void runDealerAnimation(){
        refreshView();
        dealerTimer = new Timer(700, event -> {
            game.stepDealerTurn();
            refreshView();

            if (game.getState() == BlackjackGame.RoundState.ROUND_OVER) {
                dealerTimer.stop();
            }
        });
        dealerTimer.start();
    }

    private void updateCardPanel(JPanel panel, Hand hand, boolean hideSecondCard){
        panel.removeAll();
        for (int i = 0; i < hand.size(); i++){
             if (i > 0) {
                panel.add(Box.createHorizontalStrut(10));
            }
            if (hideSecondCard && i == 1) {
                panel.add(new CardView());
            } else {
                panel.add(new CardView(hand.getCard(i)));
            }
        }
        
        panel.revalidate();
        panel.repaint();
    }
}