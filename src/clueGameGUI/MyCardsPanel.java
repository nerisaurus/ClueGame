package clueGameGUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;


public class MyCardsPanel extends JPanel{
	JTextField displayCard;
	public MyCardsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(new TitledBorder (BorderFactory.createLineBorder(Color.BLACK),"My Cards", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), Color.BLACK));
	}
	public void setTheme(Player p){
		//cards 
		for(Card card : p.getHand()) {
			//First we describe the card:
			String cardDescription = "";
			
			switch(card.getType()) {
			case ROOM:
				cardDescription += "(room)" + "   ";
				break;
			case PERSON:
				cardDescription += "(person)" + " ";
				break;
			case WEAPON:
				cardDescription += "(weapon)" + " ";	
				break;
			}
			cardDescription += card.getName();
			
			//Now we put that into a JTextField:
			displayCard = new JTextField();
			displayCard.setBackground(Color.BLACK);
			displayCard.setForeground(p.getColor());
			displayCard.setEditable(false);
			displayCard.setText(cardDescription);
			
			//And finally add that to this Panel
			add(displayCard);
		}
		//borders
		setBorder(new TitledBorder (BorderFactory.createLineBorder(p.getColor()),"My Cards", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), p.getColor()));
	}
}
