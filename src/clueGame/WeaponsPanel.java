package clueGame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class WeaponsPanel extends JPanel {
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	JComboBox<String> combo;

	public WeaponsPanel(ArrayList<String> weapons) {
		setLayout(new GridLayout(1, 2));
		setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		combo = new JComboBox<String>();

		JCheckBox checkBox;

		JPanel checkPanel_1 = new JPanel(new GridLayout(0, 1));
		JPanel checkPanel_2 = new JPanel(new GridLayout(0, 1));

		int  half_options = weapons.size()/2; //Returns half (rounded) the number of weapons
		int counter = 0;
		for(String weapon: weapons) {
			checkBox = new JCheckBox(weapon);
			//checkBox.setName(weapon);
			checkboxes.add(checkBox);
			combo.addItem(weapon);
			if(counter < half_options){
				checkPanel_1.add(checkBox);
			} else {
				checkPanel_2.add(checkBox);
			}
		}

		add(checkPanel_1);
		add(checkPanel_2);
		add(combo);
	}

}

