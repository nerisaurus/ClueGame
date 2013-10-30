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
		setBorder(new TitledBorder (new EtchedBorder(), "People"));
		combo = new JComboBox<String>();

		JCheckBox checkBox;

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        for(String weapon: weapons) {
            checkBox = new JCheckBox(weapon);
            //checkBox.setName(weapon);
            checkboxes.add(checkBox);
            combo.addItem(weapon);
            checkPanel.add(checkBox);
        }

        add(checkPanel);
        add(combo);
	}

}
