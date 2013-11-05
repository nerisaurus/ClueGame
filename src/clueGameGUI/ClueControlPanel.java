package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
		suggestionDialog = new SuggestionDialog();
		suggest = new JButton ("Suggest");
		suggest.addActionListener(new SuggestionLogListener());
		suggestionDialog.add(suggest);
	}
	
	public void createAccusationDialog(){
		accusationDialog = new SuggestionDialog();
		accuse = new JButton ("Accuse");
		accuse.addActionListener(new AccusationDialogListener());
		accusationDialog.add(accuse);
	}


}

