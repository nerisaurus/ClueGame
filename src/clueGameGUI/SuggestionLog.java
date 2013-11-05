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
	public void addSuggestion(Player accuser, Solution suggestion, Card disprovingCard) {
		//First, we generate the new line
		String detective = accuser.getName(); 
		String suspect = suggestion.getPerson().getName();
		String location = suggestion.getRoom().getName();
		String weapon = suggestion.getWeapon().getName();
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
			doc.insertString(0, detective, keyWord );
			doc.insertString(detective.length(), "\n" + suspect + "|" + location + "|" + weapon + "\n", null);
			
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    protected void paintComponent(Graphics g) {
        BufferedImage image = null;
        try {
            //
            // Load an image for the background image of out JTextPane.
            //
        	//.get("Human").getFirst().getName() + "/background.bmp"
        	image = ImageIO.read(new File("unsc1.bmp"));
        	RescaleOp op = new RescaleOp(.3f, 0, null);
            image = op.filter(image, null);
            g.drawImage(image, 0, 0, (int) getSize().getWidth(),
                    (int) getSize().getHeight(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        super.paintComponent(g);
    }
}
