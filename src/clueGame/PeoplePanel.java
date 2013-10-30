package clueGame;

import java.awt.LayoutManager;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PeoplePanel extends JPanel {
	LinkedList<JCheckBox> people;

	public PeoplePanel(LinkedList<Player> players) {
		people = new LinkedList<JCheckBox>();

		setBorder(new TitledBorder (new EtchedBorder(), "People"));		
		for(Player p: players){
			people.add(new JCheckBox(p.getName()));
		}
		
		for(JCheckBox person: people){
			add(person);
			//add listener
		}
		
	}

}
