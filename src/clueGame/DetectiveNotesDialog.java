package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JDialog;

public class DetectiveNotesDialog extends JDialog {

	public DetectiveNotesDialog(ArrayList<String> players, ArrayList<String> rooms, ArrayList<String> weapons) {
		setTitle("Detective Notes");
		setSize(400, 800);
		setLayout(new GridLayout(3, 2));
		
		PeoplePanel pp = new PeoplePanel(players);
		add(pp);
		
		//add people guess panel here
		
		RoomsPanel rp = new RoomsPanel(rooms);
		add(rp);
		
		//add room guess panel here
		
		WeaponsPanel wp = new WeaponsPanel(weapons);
		add(wp);
		
	}

}
