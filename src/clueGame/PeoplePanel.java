package clueGame;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PeoplePanel extends JPanel {
	ArrayList<JCheckBox> checkboxes = new ArrayList<JCheckBox>();

	public PeoplePanel(LinkedList<Player> players) {
		setBorder(new TitledBorder (new EtchedBorder(), "People"));

		JCheckBox checkBox;

        JPanel checkPanel = new JPanel(new GridLayout(0, 1));

    	System.out.println("hi");
        for (int i = 0; i < players.size(); i++) {
            checkBox = new JCheckBox(players.get(i).getName());
            checkBox.setName(players.get(i).getName());
            checkboxes.add(checkBox);
            checkPanel.add(checkBox);
        }

        this.add(checkPanel);
		
	}

}
