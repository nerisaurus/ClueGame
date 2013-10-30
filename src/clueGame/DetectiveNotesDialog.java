package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;

public class DetectiveNotesDialog extends JDialog {

	public DetectiveNotesDialog(ArrayList<String> players, ArrayList<String> rooms, ArrayList<String> weapons) {
		setTitle("Detective Notes");
		setSize(400, 800);
		setLayout(new GridLayout(3, 1));
		
		PeoplePanel pp = new PeoplePanel(players);
		add(pp);
				
		RoomsPanel rp = new RoomsPanel(rooms);
		add(rp);
				
		WeaponsPanel wp = new WeaponsPanel(weapons);
		add(wp);
		
	}

}
