package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class ClueControlPanel extends JPanel{
	private ClueGame clue;

	private TurnPanel currentTurn;
	private SuggestionLogPanel suggestionLog;
	private MyCardsPanel cards;
	private JButton makeAccusation, endTurn, suggest, accuse;
	private SuggestionDialog suggestionDialog, accusationDialog;
	private HumanPlayer player;
	public ClueControlPanel(ClueGame clue) {
		setLayout(new BorderLayout());
		//Topmost: A panel containing the current player's name and her dice roll:
		currentTurn = new TurnPanel();
		add(currentTurn, BorderLayout.NORTH);
		setBackground(Color.BLACK);
		//In the middle, suggestions and cards:
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		suggestionLog = new SuggestionLogPanel();
		cards = new MyCardsPanel();

		middlePanel.add(cards, BorderLayout.SOUTH);
		middlePanel.add(suggestionLog, BorderLayout.CENTER);

		add(middlePanel, BorderLayout.CENTER);

		//At the bottom, the buttons
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.BLACK);
		buttons.setLayout(new GridLayout(1,2));

		makeAccusation = new JButton("MAKE AN ACCUSATION!");
		makeAccusation.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		makeAccusation.setBackground(Color.BLACK);
		makeAccusation.setForeground(Color.WHITE);
		makeAccusation.addActionListener(new AccusationListener());

		endTurn = new JButton("End Turn");
		endTurn.setBackground(Color.BLACK);
		endTurn.setForeground(Color.WHITE);
		endTurn.addActionListener(new nextPlayerListener());

		buttons.add(makeAccusation);
		buttons.add(endTurn);

		add(buttons, BorderLayout.SOUTH);

	}

	class nextPlayerListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if (clue.gameOngoing)
				nextPlayer();
		}
	}
	class AccusationListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if (clue.isValidAccusationTime() && (clue.gameOngoing)){
				clue.makeAccusation();
			}
		}
	}

	public void nextPlayer(){
		if (!clue.isValidEndTurn()){
			return;
		}
		else
			clue.EndTurn();

	}

	public void giveClueGame(ClueGame clueGame) {
		clue = clueGame;
	}

	public void setRoll(int i) {
		//Set the dice panel to display the roll (passed in)
		currentTurn.setRoll(i);
	}

	public void addSuggestionToLog(Player accuser, Solution suggestion, Card disprovedBy) {
		suggestionLog.addSuggestion(accuser, suggestion, disprovedBy);
	}
	
	public void addAccusationToLog(Player accuser, Solution suggestion, boolean b) {
		suggestionLog.addAccusationToLog(accuser, suggestion, b);
	}
	///// suggestion dialog
	class SuggestionDialogListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (clue.gameOngoing)
				makeSuggestion();
		}
	}
	class AccusationDialogListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (clue.gameOngoing)
				makeAccusation();
		}
	}
	public void makeSuggestion(){
		//Figure out what the suggestion was:
		Card room = new Card(suggestionDialog.roomName.getText(), CardType.ROOM);
		Card suspect = new Card((String) suggestionDialog.people.getSelectedItem(), CardType.PERSON);
		Card weapon = new Card((String) suggestionDialog.weapons.getSelectedItem(), CardType.WEAPON);

		Solution suggestion = new Solution(suspect, weapon, room);
		//Handle it:
		clue.handleSuggestion(clue.getPlayers().get("Human").getFirst(), suggestion, true);

		//And, of course, actually close the Dialog:
		suggestionDialog.setVisible(false);
		clue.suggestionDialogOpen = false;
	}
	public void makeAccusation(){
		//Figure out what the ACCUSATION was:
		Card room = new Card((String) accusationDialog.rooms.getSelectedItem(), CardType.ROOM);
		Card suspect = new Card((String) accusationDialog.people.getSelectedItem(), CardType.PERSON);
		Card weapon = new Card((String) accusationDialog.weapons.getSelectedItem(), CardType.WEAPON);

		Solution accusation = new Solution(suspect, weapon, room);
		//moved it up here so that it will close the dialog box right away, but the internal work will still continue!
		
		accusationDialog.setVisible(false);
		clue.accusationDialogOpen = false;
		clue.hasActed = true;
		//Handle it:
		clue.testAccusation(accusation, clue.getPlayers().get("Human").getFirst(), true);
	}
	public void createSuggestionDialog(String room){
		suggestionDialog = new SuggestionDialog(room, clue);
		suggestionDialog.setTitle("Make a Suggestion");
		//
		suggestionDialog.submitButton.setText("Suggest");
		suggestionDialog.submitButton.addActionListener(new SuggestionDialogListener());
	}

	public void createAccusationDialog(){
		accusationDialog = new SuggestionDialog(null, clue);
		accusationDialog.setTitle("Make an Accusation!");
		//
		accusationDialog.submitButton.setText("Accuse");
		accusationDialog.submitButton.addActionListener(new AccusationDialogListener());
	}

	///player theme
	public void setHumanTheme(Player p){
		currentTurn.setTheme(p);
		suggestionLog.setTheme(p);
		cards.setTheme(p);
		setBackground(p.getColor());
	}
	public void setCurrentPlayerTheme(Player p){
		//Nameplate
		currentTurn.setCurrentPlayerTheme(p);
	}


}

