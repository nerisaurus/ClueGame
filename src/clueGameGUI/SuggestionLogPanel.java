package clueGameGUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;

public class SuggestionLogPanel extends JPanel {
	private JTextArea log;

	SuggestionLogPanel(){
		//We add a scrolling text area for
		//all previous suggestions and
		//their results
		log = new JTextArea(20, 40);
		log.setEditable(false);
		log.setWrapStyleWord(true);
		log.setLineWrap(true);
		add(log);

		//the scrolling part:
		JScrollPane scroll = new JScrollPane(log);
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
			disproved = "This was disproven with the card: " + disprovingCard.getName();
		}

		String logEntry = detective + " " + " suspected " + suspect + " on the planet " + location + " with the " + weapon + ". " + disproved + "\n";
		
		//Then we add this:
		log.append(logEntry);
	}

}

