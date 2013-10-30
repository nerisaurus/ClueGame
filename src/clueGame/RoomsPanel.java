package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RoomsPanel extends JPanel {
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
	JComboBox<String> combo;

	public RoomsPanel(ArrayList<String> rooms) {
		setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		combo = new JComboBox<String>();
		
		JCheckBox checkBox;

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

        for(String room: rooms) {
            checkBox = new JCheckBox(room);
            //checkBox.setName(room);
            checkboxes.add(checkBox);
            combo.addItem(room);
            checkPanel.add(checkBox);
        }

        add(checkPanel);	
        add(combo);
	}


}
