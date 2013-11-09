package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
		
		//First we figure out all the words we need:
		String detective = accuser.getName(); 
		String suspect = suggestion.getPerson().getName();
		String location = suggestion.getRoom().getName();
		String weapon = suggestion.getWeapon().getName();
		//Then we generate the lines to add in.  First the accusation...
		String accused = suspect + " on " + location + " with the " + weapon;
		//...Then the statement about whether it was correct or not:
		String correctness= "";
		if (correct)
			correctness = "(Correct) ";
		else
			correctness = "(FAILED) ";
		//Then we do some formatting:
		
		//Then we add the lines to the doc
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, accuser.getColor());
		StyleConstants.setBackground(keyWord, accuser.getColor().darker().darker().darker());
		StyleConstants.setBold(keyWord, true);
		
		
		SimpleAttributeSet accusationAttemptHeader = new SimpleAttributeSet();
		StyleConstants.setForeground(accusationAttemptHeader, Color.WHITE);
		//StyleConstants.setBackground(accusationAttemptHeader, Color.BLACK);
		StyleConstants.setBold(accusationAttemptHeader, true);
		StyleConstants.setItalic(accusationAttemptHeader, true);
		String attempt = "ACCUSATION:";
		
		SimpleAttributeSet accusationAttempt = new SimpleAttributeSet();
		StyleConstants.setForeground(accusationAttempt, Color.WHITE);
		StyleConstants.setItalic(accusationAttempt, true);
		//StyleConstants.setBackground(accusationAttempt, Color.BLACK);
		
		try {
			doc.insertString(0, detective, keyWord);
			doc.insertString(detective.length(), "\n" + correctness + attempt + "\n", accusationAttemptHeader);
			doc.insertString(detective.length() + attempt.length() + correctness.length() + 1, " \n" + accused + "\n", accusationAttempt);
			
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
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
}
