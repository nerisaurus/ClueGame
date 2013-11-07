package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SuggestionLog extends JTextPane {
	private StyledDocument doc;
	//private int backgroundHeight, backgroundWidth;
	
	String fileLocation;
	public SuggestionLog(){
		DefaultCaret caret = (DefaultCaret)this.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setEditable(false);
		setSize(300, 200);
		setOpaque(false);
		doc = getStyledDocument();
	}
	
	public void addAccusation(Player accuser, Solution suggestion, boolean correct) {
		//TODO: (Complete these sections - this works very much like how addSuggestion works)
		//(Feel free to be creative with the wording and formatting here.  It should be
		//obvious that an accusation is not a suggestion [in terms of format - maybe invert the colors]
		//but it shouldn't disrupt the log too much - still keep it to 2 or 3 lines)
		
		//First we figure out all the words we need:
		
		//Then we generate the lines to add in.  First the accusation...
		
		//...Then the statement about whether it was correct or not:
		
		
		//Then we do some formatting:
		
		//Then we add the lines to the doc
	}
	public void addSuggestion(Player accuser, Solution suggestion, Card disprovingCard) {
		//First, we figure out all the words we need:
		String detective = accuser.getName(); 
		String suspect = suggestion.getPerson().getName();
		String location = suggestion.getRoom().getName();
		String weapon = suggestion.getWeapon().getName();
		
		//Then we generate the lines to add in: First, the suggestion...
		String suggested = suspect + " on " + location + " with the " + weapon;
		
		//...Then the statement of its disproven-ness
		String disproved;
		if(disprovingCard == null) {
			disproved = "This has not been disproven...";
		} else {
			disproved = "This was disproven with the card: " + disprovingCard.getName();
		}

		//String logEntry = detective + " " + " suspected " + suspect + " on the planet " + location + " with the " + weapon + ". " + disproved + "\n";
		//Then we add this:
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, accuser.getColor());
		StyleConstants.setBackground(keyWord, accuser.getColor().darker().darker().darker());
		StyleConstants.setBold(keyWord, true);
		
		try {
			doc.insertString(0, detective, keyWord);
			doc.insertString(detective.length(), "\n" + suggested + "\n", null);
			doc.insertString(detective.length() + suggested.length() + 1, "\n" + disproved + "\n", null);
			
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	//TODO: (Remove this code if it's not needed any more.)
/*	public void setTheme(Player p){
		fileLocation = "Themes/" + p.getName() + "/";
		//backgroundHeight = (int) getSize().getHeight();
		//backgroundWidth = (int) getSize().getWidth();
	}*/
  /*  protected void paintComponent(Graphics g) {
        BufferedImage image = null;
        try {
            //
            // Load an image for the background image of out JTextPane.
            //
        	//.get("Human").getFirst().getName() + "/background.bmp"
        	image = ImageIO.read(new File(fileLocation + "logBackground.png"));
        	//RescaleOp op = new RescaleOp(.3f, 0, null);
            //image = op.filter(image, null);
            g.drawImage(image, 0, 0, 
            		(int) getSize().getWidth(),
            		(int) getSize().getHeight(), this);
            
           // g.drawImage(image, 0, 0, backgroundWidth,
           //         backgroundHeight, this);
            super.paintComponent(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        super.paintComponent(g);
    }*/
}
