package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;

public class DetectiveNotesDialog extends JDialog {

	public DetectiveNotesDialog(ArrayList<String> players, ArrayList<String> rooms, ArrayList<String> weapons) {
		setTitle("Detective Notes");
		setSize(500, 600);
		setLayout(new GridLayout(3, 1));
		
		SuspectsPanel pp = new SuspectsPanel(players, "Who");
		add(pp);
				
		SuspectsPanel rp = new SuspectsPanel(rooms, "Where");
		add(rp);
				
		SuspectsPanel wp = new SuspectsPanel(weapons, "How");
		add(wp);
		
	}

}
