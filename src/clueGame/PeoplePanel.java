package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PeoplePanel extends JPanel {
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();

	public PeoplePanel(ArrayList<String> people) {
		setBorder(new TitledBorder (new EtchedBorder(), "People"));

		JCheckBox checkBox;

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        for(String person: people) {
            checkBox = new JCheckBox(person);
            //checkBox.setName(person);
            checkboxes.add(checkBox);
            checkPanel.add(checkBox);
        }

        this.add(checkPanel);		
	}

}
