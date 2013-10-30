package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PeoplePanel extends JPanel {
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	JComboBox<String> combo;

	public PeoplePanel(ArrayList<String> people) {
		setLayout(new GridLayout(1, 2));
		setBorder(new TitledBorder (new EtchedBorder(), "People"));
		combo = new JComboBox<String>();

		JCheckBox checkBox;

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        for(String person: people) {
            checkBox = new JCheckBox(person);
            //checkBox.setName(person);
            checkboxes.add(checkBox);
            combo.addItem(person);
            checkPanel.add(checkBox);
        }

        add(checkPanel);	
        add(combo);
	}

}
