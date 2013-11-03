package clueGameGUI;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.CardType;
import clueGame.HumanPlayer;


public class MyCardsPanel extends JPanel{
	HumanPlayer hp;
	public MyCardsPanel(HumanPlayer hp){
		this.hp = hp;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		CardType[] type = {CardType.PERSON, CardType.WEAPON, CardType.ROOM};
		for (CardType t : type){
			add(new JLabel(t.toString()));
			add((new JTextArea(hp.getCard(t))));
		}
	}
}
