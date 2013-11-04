package clueGameGUI;

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
		//TODO:
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
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
			displayCard.setEditable(false);
			displayCard.setText(cardDescription);
			
			//And finally add that to this Panel
			add(displayCard);
		}
	}
}
