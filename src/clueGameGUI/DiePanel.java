package clueGameGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Player;

public class DiePanel extends JPanel {
	private String fileLocation;
	JLabel dieFrame;
	public DiePanel() { 	
		setBackground(Color.BLACK);
		dieFrame = new JLabel(); 
		dieFrame.setIcon(new ImageIcon("zero.png")); 
		add(dieFrame, BorderLayout.CENTER);   
	}

	public void setTheme(Player p){ //Currently a vestigial command.
		fileLocation = "Themes/" + p.getName() + "/";
	}

	//Adds in Dice Image to Control Panel:
	public void setFace(int i){
		//Code which doesn't work:
		/*switch (i){
		case 1: {dieFrame.setIcon(new ImageIcon(fileLocation + "one.png")); break;}
		case 2: {dieFrame.setIcon(new ImageIcon(fileLocation + "two.png")); break;}
		case 3: {dieFrame.setIcon(new ImageIcon(fileLocation + "three.png")); break;}
		case 4: {dieFrame.setIcon(new ImageIcon(fileLocation + "four.png")); break;}
		case 5: {dieFrame.setIcon(new ImageIcon(fileLocation + "five.png")); break;}
		case 6: {dieFrame.setIcon(new ImageIcon(fileLocation + "six.png")); break;}
			default: {dieFrame.setIcon(new ImageIcon(fileLocation + "zero.png")); break;}
		}*/

		//TODO: Old code - works, but not as fancy
		switch (i){
		case 1: {dieFrame.setIcon(new ImageIcon("one.png")); break;}
		case 2: {dieFrame.setIcon(new ImageIcon("two.png")); break;}
		case 3: {dieFrame.setIcon(new ImageIcon("three.png")); break;}
		case 4: {dieFrame.setIcon(new ImageIcon("four.png")); break;}
		case 5: {dieFrame.setIcon(new ImageIcon("five.png")); break;}
		case 6: {dieFrame.setIcon(new ImageIcon("six.png")); break;}
		default: {dieFrame.setIcon(new ImageIcon("zero.png")); break;}
		}
		/*			
			//TODO: Testing
			ImageIcon n;
			switch (i){
			case 1: {n = new ImageIcon(fileLocation + "one.png"); break;}
			case 2: {n = new ImageIcon(fileLocation + "two.png"); break;}
			case 3: {n = new ImageIcon(fileLocation + "three.png"); break;}
			case 4: {n = new ImageIcon(fileLocation + "four.png"); break;}
			case 5: {n = new ImageIcon(fileLocation + "five.png"); break;}
			case 6: {n = new ImageIcon(fileLocation + "six.png"); break;}
				default: {n = new ImageIcon("one.png"); break;}
			}
			System.out.println(n.getImageLoadStatus() + " is " + i + "'s load status.");

			//How to read this test result:
			public static final int	ABORTED	2
			public static final int	COMPLETE	8
			public static final int	ERRORED	4
			public static final int	LOADING	1


			//TODO: Testing...
			//Observed Errors:
			//The first call is always null.  Somehow we never set setTheme() to the actual human player once we've figured that out.
			//
			//However, that should be fairly easy to fix, unlike the other issue:
			//Other calls are arbitrary.  Sometimes a file will be "false" (cannot be found) and sometimes "true".  I cannot figure out
			//why.  We might need more complex techniques (such as preloading images somehow).
			//
			switch (i){
			case 1: {System.out.println(new File(fileLocation + "one.png").exists() + " " + fileLocation + "one.png"); break;}
			case 2: {System.out.println(new File(fileLocation + "two.png").exists()+ " " + fileLocation + "two.png"); break;}
			case 3: {System.out.println(new File(fileLocation + "three.png").exists()+ " " + fileLocation + "three.png"); break;}
			case 4: {System.out.println(new File(fileLocation + "four.png").exists()+ " " + fileLocation + "four.png"); break;}
			case 5: {System.out.println(new File(fileLocation + "five.png").exists()+ " " + fileLocation + "five.png"); break;}
			case 6: {System.out.println(new File(fileLocation + "six.png").exists()+ " " + fileLocation + "six.png"); break;}
				default: {System.out.println(new File(fileLocation + "zero.png").exists()+ " " + fileLocation + "zero.png"); break;}
			}
		 */


	}

}
