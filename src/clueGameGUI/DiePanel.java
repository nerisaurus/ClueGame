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
	//ImageIcon one, two, three, four, five, six, zero;
	public DiePanel() { 	
		setBackground(Color.BLACK);
	    dieFrame = new JLabel(); 
	    dieFrame.setIcon(new ImageIcon("zero.png")); 
	    add(dieFrame, BorderLayout.CENTER);   
	}
	
	public void setTheme(Player p){
		fileLocation = "Themes/" + p.getName() + "/";
		/*one = new ImageIcon(fileLocation + "one.png");
		two = new ImageIcon(fileLocation + "two.png");
		three = new ImageIcon(fileLocation + "three.png");
		four = new ImageIcon(fileLocation + "four.png");
		five = new ImageIcon(fileLocation + "five.png");
		six = new ImageIcon(fileLocation + "six.png");
		zero = new ImageIcon(fileLocation + "zero.png");*/
	}
	
	//Adds in Dice Image to Control Panel:
	public void setFace(int i){
		switch (i){
		case 1: {dieFrame.setIcon(new ImageIcon(fileLocation + "one.png")); break;}
		case 2: {dieFrame.setIcon(new ImageIcon(fileLocation + "two.png")); break;}
		case 3: {dieFrame.setIcon(new ImageIcon(fileLocation + "three.png")); break;}
		case 4: {dieFrame.setIcon(new ImageIcon(fileLocation + "four.png")); break;}
		case 5: {dieFrame.setIcon(new ImageIcon(fileLocation + "five.png")); break;}
		case 6: {dieFrame.setIcon(new ImageIcon(fileLocation + "six.png")); break;}
			default: {dieFrame.setIcon(new ImageIcon(fileLocation + "zero.png")); break;}
		}
		
		//Trying to see if pre-setting icons worked (it didn't)
	/*	switch (i){
		case 1: {dieFrame.setIcon(one); break;}
		case 2: {dieFrame.setIcon(two); break;}
		case 3: {dieFrame.setIcon(three); break;}
		case 4: {dieFrame.setIcon(four); break;}
		case 5: {dieFrame.setIcon(five); break;}
		case 6: {dieFrame.setIcon(six); break;}
			default: {dieFrame.setIcon(zero); break;}
		}*/
		
		//This Works (but has less functionality)
		/*switch (i){
		case 1: {dieFrame.setIcon(new ImageIcon("one.png")); break;}
		case 2: {dieFrame.setIcon(new ImageIcon("two.png")); break;}
		case 3: {dieFrame.setIcon(new ImageIcon("three.png")); break;}
		case 4: {dieFrame.setIcon(new ImageIcon("four.png")); break;}
		case 5: {dieFrame.setIcon(new ImageIcon("five.png")); break;}
		case 6: {dieFrame.setIcon(new ImageIcon("six.png")); break;}
			default: {dieFrame.setIcon(new ImageIcon("zero.png")); break;}
		}*/
		
		//TODO: Testing...
		//Observed Errors:
		//The first call is always null.  Somehow we never set setTheme() to the actual human player once we've figured that out.
		//
		//However, that should be fairly easy to fix, unlike the other issue:
		//Other calls are arbitrary.  Sometimes a file will be "false" (cannot be found) and sometimes "true".  I cannot figure out
		//why.  We might need more complex techniques (such as preloading images somehow).
		//
		//I tried preloading all images in the setTheme (as you might notice commented out) above... but it still fails. The images are sometimes
		//there and sometimes not (so ImageIcon's constructor just uses that as a location to look for - it doesn't actually
		//store the image itself).
		switch (i){
		case 1: {System.out.println(new File(fileLocation + "one.png").exists() + " " + fileLocation + "one.png"); break;}
		case 2: {System.out.println(new File(fileLocation + "two.png").exists()+ " " + fileLocation + "two.png"); break;}
		case 3: {System.out.println(new File(fileLocation + "three.png").exists()+ " " + fileLocation + "three.png"); break;}
		case 4: {System.out.println(new File(fileLocation + "four.png").exists()+ " " + fileLocation + "four.png"); break;}
		case 5: {System.out.println(new File(fileLocation + "five.png").exists()+ " " + fileLocation + "five.png"); break;}
		case 6: {System.out.println(new File(fileLocation + "six.png").exists()+ " " + fileLocation + "six.png"); break;}
			default: {System.out.println(new File(fileLocation + "zero.png").exists()+ " " + fileLocation + "zero.png"); break;}
		}
		
		
	}
	
}
