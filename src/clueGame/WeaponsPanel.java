package clueGame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class WeaponsPanel extends JPanel {
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();

	public WeaponsPanel(ArrayList<String> weapons) {
		setBorder(new TitledBorder (new EtchedBorder(), "People"));

		JCheckBox checkBox;

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        for(String weapon: weapons) {
            checkBox = new JCheckBox(weapon);
            //checkBox.setName(weapon);
            checkboxes.add(checkBox);
            checkPanel.add(checkBox);
        }

        this.add(checkPanel);		
	}

}
