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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SuggestionLog extends JTextPane {
	StyledDocument doc;
	JScrollPane scroll;
	String fileLocation;
	public SuggestionLog(){
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setEditable(false);
		setSize(300, 200);
		setOpaque(false);
		doc = getStyledDocument();
		scroll = new JScrollPane();
		scroll.setBackground(Color.BLACK);
		scroll.setForeground(Color.WHITE);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.EAST);
	}
	
	//A constructor that lets you set the theme in one fell swoop, just in case you want to do that
	public SuggestionLog(Player p){
		setTheme(p);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setEditable(false);
		setSize(300, 200);
		setOpaque(false);
		doc = getStyledDocument();
		scroll = new JScrollPane();
		scroll.setBackground(Color.BLACK);
		scroll.setForeground(Color.WHITE);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.EAST);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setTheme(Player p){
		fileLocation = "Themes/" + p.getName() + "/";
	}
    protected void paintComponent(Graphics g) {
        BufferedImage image = null;
        try {
            //
            // Load an image for the background image of out JTextPane.
            //
        	//.get("Human").getFirst().getName() + "/background.bmp"
        	image = ImageIO.read(new File(fileLocation + "logBackground.png"));
        	//RescaleOp op = new RescaleOp(.3f, 0, null);
            //image = op.filter(image, null);
            g.drawImage(image, 0, 0, (int) getSize().getWidth(),
                    (int) getSize().getHeight(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        super.paintComponent(g);
    }
}
