package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SuggestionLogPanel extends JPanel {
	private JTextPane log;
	private JScrollPane scroll;
	StyledDocument doc;
	SuggestionLogPanel(){
		//We add a scrolling text area for
		//all previous suggestions and
		//their results
		log = new JTextPane();
		log.setEditable(false);
		log.setSize(200, 200);
		//log.setWrapStyleWord(true);
		//log.setLineWrap(true);
		setLayout(new BorderLayout());
		add(log, BorderLayout.CENTER);
		doc = log.getStyledDocument();
		//the scrolling part:
		scroll = new JScrollPane(log);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scroll);

		//and a border to explain the purpose of this
		setBorder(new TitledBorder (new EtchedBorder(), "Previous Suggestions"));	
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
			disproved = "DISPROVEN:  " + disprovingCard.getName();
		}

		String logEntry = "\n" + suspect + " on " + location + " with the " + weapon + "\n";
		//Then we add this:
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, accuser.getColor());
		StyleConstants.setBackground(keyWord, accuser.getColor().darker().darker().darker());
		StyleConstants.setBold(keyWord, true);
		try {
			doc.insertString(0, detective, keyWord );
			doc.insertString(detective.length(), logEntry, null);
			doc.insertString(detective.length() + logEntry.length(), disproved + "\n", null);
			
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

