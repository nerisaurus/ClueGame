package clueGameGUI;

import java.awt.BorderLayout;
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

import clueGame.ClueGame;
import clueGame.HumanPlayer;

public class ClueControlPanel extends JPanel{
	private ClueGame clue;
	
	private TurnPanel currentTurn;
	private SuggestionLogPanel suggestionLog;
	private MyCardsPanel cards;
	private JButton makeAccusation, endTurn, suggest, accuse;
	private SuggestionDialog suggestionDialog, accusationDialog;
	
	public ClueControlPanel(ClueGame clue, HumanPlayer player) {
		setLayout(new BorderLayout());
		
		//Topmost: A panel containing the current player's name and her dice roll:
		currentTurn = new TurnPanel();
		add(currentTurn, BorderLayout.NORTH);
		
		//In the middle, suggestions and cards:
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		
		suggestionLog = new SuggestionLogPanel();
		cards = new MyCardsPanel(player);
		
		middlePanel.add(cards, BorderLayout.SOUTH);
		middlePanel.add(suggestionLog, BorderLayout.CENTER);
		
		add(middlePanel, BorderLayout.CENTER);
		
		//At the bottom, the buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,2));
		
		makeAccusation = new JButton("MAKE AN ACCUSATION!");
		makeAccusation.addActionListener(new AccusationListener());
		
		endTurn = new JButton("End Turn");
		endTurn.addActionListener(new nextPlayerListener());
		
		buttons.add(makeAccusation);
		buttons.add(endTurn);
		
		add(buttons, BorderLayout.SOUTH);
		
	}
/*	JPanel turn;
	ClueGame clue;
	private JTextArea currentPlayer;
	private JPanel currentTurn;
	private DiePanel dp;
	private JTextArea log;
	private JPanel suggestions;
	private JPanel suggestionsAndCards;
	private JPanel turnAndLabel;
	private MyCardsPanel cards;
	
	public ClueControlPanel() {
		//setSize(800,300);		
		setLayout(new BorderLayout());
		
		//Label:
		currentTurn = new JPanel();
		currentTurn.setBorder(new TitledBorder (new EtchedBorder(), "Current Investigator:"));
		currentPlayer = new JTextArea();
		currentPlayer.setText("...");
		currentPlayer.setEditable(false);
		currentTurn.add(currentPlayer);
		
		//Turn sub-Panel: Die rolls, and End Turn button:
		turn = new JPanel();
		turn.setLayout(new GridLayout(1,2));
		
		dp = new DiePanel();
		JButton nextPlayer = new JButton("End Turn");
		nextPlayer.addActionListener(new nextPlayerListener());
		turn.add(dp);
		turn.add(nextPlayer);
		
		//Now we combine them:
		turnAndLabel = new JPanel();
		turnAndLabel.setLayout(new GridLayout(2,1));
		turnAndLabel.add(currentTurn);
		turnAndLabel.add(turn);
		add(turnAndLabel, BorderLayout.NORTH);
		//////////////////////////////////////////////////////////////////////////////
		//Suggestions sub-Panel
		suggestions = new JPanel();
		suggestions.setBorder(new TitledBorder (new EtchedBorder(), "Previous Suggestions"));
		
		//A text field:
		log = new JTextArea(11, 40);
		log.setEditable(false);
		log.setWrapStyleWord(true);
		log.setLineWrap(true);
		suggestions.add(log);
		
		//...and the scrolling part:
		JScrollPane scroll = new JScrollPane(log);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    suggestions.add(scroll);
	    
	    //and a border to explain the purpose of this
		add(suggestions, BorderLayout.CENTER); //so it's given the room it needs
		//DiePanel coolDicePanel = new DiePanel();
		//add(turn.getDiePanel(), BorderLayout.EAST);
		
		//////////////////////////////////////////////////
		//Cards sub-Panel
		cards = new MyCardsPanel();
		
		
		
		////////////
		//We combine the suggestions and cards into one:
		suggestionsAndCards = new JPanel();
		suggestionsAndCards.setLayout(new BorderLayout());
		suggestionsAndCards.add(suggestions, BorderLayout.CENTER);
		suggestionsAndCards.add(cards, BorderLayout.SOUTH);
		add(suggestionsAndCards, BorderLayout.CENTER); //so it's given the room it needs
		
		////////////////////////////////////////////////
		//Accusation sub-Panel
		JButton makeAccusation = new JButton("MAKE AN ACCUSATION!");
		makeAccusation.addActionListener(new AccusationListener());
		add(makeAccusation, BorderLayout.SOUTH);
	}*/

	class nextPlayerListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			nextPlayer();
		}
	}
	class AccusationListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			createAccusationDialog();
			clue.accusationDialogOpen = true;
		}
	}

	public void nextPlayer(){
		if (clue.suggestionDialogOpen == true || clue.accusationDialogOpen == true){
			return;
		}
		else
			clue.EndTurn();
		
	}

	public void giveClueGame(ClueGame clueGame) {
		// TODO Auto-generated method stub
		clue = clueGame;
		//clue.EndTurn();
	}

	public void setRoll(int i) {
		// TODO Auto-generated method stub
		currentTurn.setRoll(i);
	}

	public void setTurn(String name) {
		// TODO Auto-generated method stub
		currentTurn.setCurrentPlayer(name);
	}
	///// suggestion dialog
	class SuggestionLogListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			makeSuggestion();
		}
	}
	class AccusationDialogListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			makeAccusation();
		}
	}
	public void makeSuggestion(){
		suggestionDialog.setVisible(false);
		clue.suggestionDialogOpen = false;
	}
	public void makeAccusation(){
		accusationDialog.setVisible(false);
		clue.accusationDialogOpen = false;
	}
	public void createSuggestionDialog(){
		//if (!accusationDialog.isVisible()){
		suggestionDialog = new SuggestionDialog();
		suggest = new JButton ("Suggest");
		suggest.addActionListener(new SuggestionLogListener());
		suggestionDialog.add(suggest, BorderLayout.SOUTH);
		//}
		//else{
		//	JOptionPane.showMessageDialog(null, "Close Accusation Dialog.");
		//	return;
		//}
	}
	
	public void createAccusationDialog(){
		//if (!suggestionDialog.isVisible()){
			accusationDialog = new SuggestionDialog();
			accuse = new JButton ("Accuse");
			accuse.addActionListener(new AccusationDialogListener());
			accusationDialog.add(accuse);
		//}
		//else{
		//	JOptionPane.showMessageDialog(null, "Close Suggestion Dialog.");
		//	return;
		//}
	}


}

