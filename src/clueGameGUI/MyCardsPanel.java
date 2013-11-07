package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	JLabel cardIcon;
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
			
			//Then pick its image:
			cardIcon = new JLabel();
			cardIcon.setBackground(Color.BLACK);
			cardIcon.setForeground(p.getColor());
			
			switch(card.getType()) {
			case ROOM:
				cardIcon.setIcon(new ImageIcon("roomCardIcon.png"));
				//cardDescription += "(room)" + "   ";
				break;
			case PERSON:
				cardIcon.setIcon(new ImageIcon("personCardIcon.png"));
				//cardDescription += "(person)" + " ";
				break;
			case WEAPON:
				cardIcon.setIcon(new ImageIcon("weaponCardIcon.png"));
				//cardDescription += "(weapon)" + " ";	
				break;
			}
			cardDescription += card.getName();
			
			//Now we put the description we made into a JTextField
			displayCard = new JTextField();
			displayCard.setBackground(Color.BLACK);
			displayCard.setForeground(p.getColor());
			displayCard.setEditable(false);
			displayCard.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.black));
			displayCard.setText(cardDescription);
			
			//And add it and the icon into a JPanel
			JPanel cardLine = new JPanel();
			cardLine.setBackground(Color.BLACK);
			cardLine.setForeground(p.getColor());
			cardLine.setLayout(new FlowLayout(FlowLayout.LEFT));
			cardLine.add(cardIcon);
			cardLine.add(displayCard);
			
			//And finally add that to this Panel
			add(cardLine);
		}
		//borders
		setBorder(new TitledBorder (BorderFactory.createLineBorder(p.getColor()),"My Cards", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), p.getColor()));
	}
}
