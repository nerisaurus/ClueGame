package clueGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DetectiveNotesDialog extends JDialog {

	public DetectiveNotesDialog(ArrayList<String> players, ArrayList<String> rooms, ArrayList<String> weapons) {
		setTitle("Detective Notes");
		setSize(700, 500);
		setLayout(new GridLayout(3, 1));
		
		ClueGame.setGUILookAndFeel("CDE/Motif");
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		
		SuspectsPanel pp = new SuspectsPanel(players, "Who", "I suspect ");
		pp.setBackground(Color.BLACK);
		pp.setForeground(Color.BLACK);
		add(pp);
				
		SuspectsPanel rp = new SuspectsPanel(rooms, "Where", "on the planet ");
		rp.setBackground(Color.BLACK);
		rp.setForeground(Color.BLACK);
		add(rp);
				
		SuspectsPanel wp = new SuspectsPanel(weapons, "How", "with the ");
		wp.setBackground(Color.BLACK);
		wp.setForeground(Color.BLACK);
		add(wp);
		
		
		Dimension sd = Toolkit.getDefaultToolkit().getScreenSize(); 
		Dimension fd = getSize(); 
		if (fd.height > sd.height) 
			fd.height = sd.height; 
		if (fd.width > sd.width) 
			fd.width = sd.width; 
		setLocation((sd.width - fd.width) / 2, (sd.height - fd.height) / 2);
	}

}
