package clueGame;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;


public class DetectiveNotesDialog extends JDialog {
	SuspectsPanel pp, rp, wp;
	public DetectiveNotesDialog(ArrayList<String> players, ArrayList<String> rooms, ArrayList<String> weapons) {
		setTitle("Detective Notes");
		setSize(700, 500);
		setLayout(new GridLayout(3, 1));

		//Setting the GUI to match the other dialogs:
		ClueGame.setGUILookAndFeel("CDE/Motif");

		pp = new SuspectsPanel(players, "Who", "I suspect ");
		add(pp);

		rp = new SuspectsPanel(rooms, "Where", "on the planet ");
		add(rp);

		wp = new SuspectsPanel(weapons, "How", "with the ");
		add(wp);

		//Logic to load it in the middle of the screen:
		Dimension sd = Toolkit.getDefaultToolkit().getScreenSize(); 
		Dimension fd = getSize(); 
		if (fd.height > sd.height) 
			fd.height = sd.height; 
		if (fd.width > sd.width) 
			fd.width = sd.width; 
		setLocation((sd.width - fd.width) / 2, (sd.height - fd.height) / 2);
	}
	public void setTheme(Player p){
		pp.setBorder(new TitledBorder (BorderFactory.createLineBorder(p.getColor()),"Who", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), p.getColor()));
		rp.setBorder(new TitledBorder (BorderFactory.createLineBorder(p.getColor()),"Where", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), p.getColor()));
		wp.setBorder(new TitledBorder (BorderFactory.createLineBorder(p.getColor()),"How", 0, 0, new Font("Arial Narrow", Font.BOLD, 12), p.getColor()));
	}

}
