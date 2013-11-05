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


public class MyCardsPanel extends JPanel{
	HumanPlayer hp;
	
	public MyCardsPanel(HumanPlayer hp) {
		this.hp = hp;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(new TitledBorder (BorderFactory.createLineBorder(Color.red),"My Cards", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), hp.getColor()));
		for(Card card : hp.getHand()) {
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
			JTextField displayCard = new JTextField();
			displayCard.setBackground(Color.BLACK);
			displayCard.setForeground(hp.getColor());
			displayCard.setEditable(false);
			displayCard.setText(cardDescription);
			
			//And finally add that to this Panel
			add(displayCard);
		}
	}
}
