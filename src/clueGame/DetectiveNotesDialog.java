package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;

public class DetectiveNotesDialog extends JDialog {

	public DetectiveNotesDialog(ArrayList<String> players, ArrayList<String> rooms, ArrayList<String> weapons) {
		setTitle("Detective Notes");
		setSize(700, 500);
		setLayout(new GridLayout(3, 1));
		
		SuspectsPanel pp = new SuspectsPanel(players, "Who", "I suspect ");
		add(pp);
				
		SuspectsPanel rp = new SuspectsPanel(rooms, "Where", "on the planet ");
		add(rp);
				
		SuspectsPanel wp = new SuspectsPanel(weapons, "How", "with the ");
		add(wp);
		
	}

}
