package clueGame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class SuspectsPanel extends JPanel {
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	JComboBox<String> combo;

	public SuspectsPanel(ArrayList<String> possible_cards, String name) {
		setLayout(new GridLayout(1, 3));
		setBorder(new TitledBorder (new EtchedBorder(), name));
		combo = new JComboBox<String>();

		JCheckBox checkBox;

		JPanel checkPanel_1 = new JPanel(new GridLayout(0, 1));
		JPanel checkPanel_2 = new JPanel(new GridLayout(0, 1));

		int  half_options = possible_cards.size()/2; //Returns half (rounded) the number of weapons
		int counter = 0;
		for(String card: possible_cards) {
			checkBox = new JCheckBox(card);
			//checkBox.setName(weapon);
			checkboxes.add(checkBox);
			combo.addItem(card);
			if(counter < half_options){
				checkPanel_1.add(checkBox);
			} else {
				checkPanel_2.add(checkBox);
			}
			counter++;
		}

		add(checkPanel_1);
		add(checkPanel_2);
		add(combo);
	}

}

