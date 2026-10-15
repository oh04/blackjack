package blackjack;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class CardView extends JLabel{
    
    public CardView(Card card){
        setOpaque(true);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(130, 100));
        setMinimumSize(new Dimension(130, 100));
        setMaximumSize(new Dimension(130, 100));
        if(card.getSuit() == Card.Suit.HEARTS || card.getSuit() == Card.Suit.DIAMONDS){
            setForeground(Color.RED);
        }
        String rankText;
        switch(card.getRank()){
            case ACE:
                rankText = "A";
                break;
            case JACK:
                rankText = "J";
                break;
            case QUEEN:
                rankText = "Q";
                break;
            case KING:
                rankText = "K";
                break;
            default:
                rankText = Integer.toString(card.getValue());
                break;
        }

        String suitText;
        switch(card.getSuit()){
            case HEARTS:
                suitText = "♥";
                break;
            case DIAMONDS:
                suitText = "♦";
                break;
            case CLUBS:
                suitText = "♣";
                break;
            case SPADES:
                suitText = "♠";
                break;
            default:
                throw new IllegalStateException("Unexpected suit");
        }

        setText(rankText + " " + suitText);
    }
    
    public CardView(){
        setText("?");
        setOpaque(true);
        setBackground(new Color(30,50,100));
        setForeground(Color.WHITE);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(130, 100));
        setMinimumSize(new Dimension(130, 100));
        setMaximumSize(new Dimension(130, 100));
    }
}