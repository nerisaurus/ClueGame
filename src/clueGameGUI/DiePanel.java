package clueGameGUI;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DiePanel extends JPanel {
	ImageIcon dieFace;
	JLabel dieFrame;
	public DiePanel() { 	
		setBackground(Color.BLACK);
	    dieFace = new ImageIcon("zero.png"); 
	    dieFrame = new JLabel(); 
	    dieFrame.setIcon(dieFace); 
	    add(dieFrame, BorderLayout.CENTER);
	    
	}
	
	//TODO: Just Add in the images (or rather, new - slightly smaller - images)
	public void setFace(int i){
		switch (i){
		case 1: {dieFrame.setIcon(new ImageIcon("one.png")); break;}
		case 2: {dieFrame.setIcon(new ImageIcon("two.png")); break;}
		case 3: {dieFrame.setIcon(new ImageIcon("three.png")); break;}
		case 4: {dieFrame.setIcon(new ImageIcon("four.png")); break;}
		case 5: {dieFrame.setIcon(new ImageIcon("five.png")); break;}
		case 6: {dieFrame.setIcon(new ImageIcon("six.png")); break;}
			default: {dieFrame.setIcon(new ImageIcon("zero.png")); break;}
		}
	}
	
}
