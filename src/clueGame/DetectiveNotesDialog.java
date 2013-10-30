package clueGame;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class DetectiveNotesDialog extends JDialog {

	public DetectiveNotesDialog(LinkedList<Player> players) {
		setTitle("Detective Notes");
		setSize(400, 600);
		setLayout(new GridLayout(2, 3));
		
		PeoplePanel pp = new PeoplePanel(players);
		add(pp);
		
	}

}
