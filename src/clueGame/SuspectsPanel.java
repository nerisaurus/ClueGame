package clueGame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class SuspectsPanel extends JPanel {
	static final String unsure = "I'm Unsure";
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	DefaultComboBoxModel<String> combo;
	JComboBox<String> comboList;

	//Logic for Updating the Notes:
	private class CheckboxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(JCheckBox checkbox : checkboxes){
				if(checkbox.isSelected()){ 
					//if the player marks the target as seen
					combo.removeElement(checkbox.getText());
				} else { 
					//if the target is unchecked
					if(combo.getIndexOf(checkbox.getText()) == -1){ 
						//and it has been removed (the player changed their mind?)
						combo.addElement(checkbox.getText());
					}

				}
			}
			//And to add/remove an "Unsure" element
			if(combo.getIndexOf(unsure) == -1 && combo.getSize() > 1){
				combo.insertElementAt(unsure,0); //insertElementAt instead of addElement in order to always put this first
			} else if(combo.getIndexOf(unsure) != -1 && combo.getSize() <= 2){
				combo.removeElement(unsure);
			}
		}
	}

	public SuspectsPanel(ArrayList<String> possible_cards, String name) {
		setLayout(new GridLayout(1, 3));
		setBorder(new TitledBorder (new EtchedBorder(), name));
		combo = new DefaultComboBoxModel<String>();
		comboList = new JComboBox<String>(combo);

		JCheckBox checkBox;

		JPanel checkPanel_1 = new JPanel(new GridLayout(0, 1));
		JPanel checkPanel_2 = new JPanel(new GridLayout(0, 1));

		int  half_options = possible_cards.size()/2; //Returns half (rounded) the number of options
		int counter = 0;
		combo.addElement(unsure);
		for(String card: possible_cards) {
			checkBox = new JCheckBox(card);
			checkBox.addActionListener(new CheckboxListener());
			//checkBox.setName(weapon);
			checkboxes.add(checkBox);
			combo.addElement(card);
			if(counter < half_options){
				checkPanel_1.add(checkBox);
			} else {
				checkPanel_2.add(checkBox);
			}
			counter++;
		}

		add(checkPanel_1);
		add(checkPanel_2);
		add(comboList);
	}

}

