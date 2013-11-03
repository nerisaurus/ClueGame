package clueGameGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.ClueGame;

public class ClueControlFrame extends JPanel{
	JPanel turn;
	JPanel suggestions;
	ClueGame clue;
	private JTextArea currentPlayer;
	private DiePanel dp;
	private JTextArea log;
	private JPanel logger;
	
	public ClueControlFrame() {
		//setSize(800,300);		
		setLayout(new BorderLayout());
		turn = new JPanel();
		
		turn.setLayout(new GridLayout(3,1));
		turn.setBorder(new TitledBorder (new EtchedBorder(), "Turn"));
		dp = new DiePanel();
		currentPlayer = new JTextArea(2, 10);
		currentPlayer.setEditable(false);
		currentPlayer.setWrapStyleWord(true);
		currentPlayer.setLineWrap(true);
		turn.add(currentPlayer);
		currentPlayer.setText(" ... ");
		JButton nextPlayer = new JButton("End Turn");
		nextPlayer.addActionListener(new nextPlayerListener());
		turn.add(dp);
		turn.add(nextPlayer);
		
		add(turn, BorderLayout.WEST);
		//////////////////////////////////////////////////////////////////////////////
		suggestions = new JPanel();
		logger = new JPanel();
		logger.setBorder(new TitledBorder (new EtchedBorder(), "Previous Suggestions"));
		log = new JTextArea(11, 40);
		log.setEditable(false);
		log.setWrapStyleWord(true);
		log.setLineWrap(true);
		logger.add(log);
		
		//the scrolling part:
		JScrollPane scroll = new JScrollPane(log);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    logger.add(scroll);
	    
	    //and a border to explain the purpose of this
		suggestions.setLayout(new BoxLayout(suggestions, BoxLayout.Y_AXIS));
		suggestions.add(logger);
		JButton makeAccusation = new JButton("MAKE AN ACCUSATION!");
		suggestions.add(makeAccusation);
		makeAccusation.addActionListener(new SuggestionListener());
		add(suggestions, BorderLayout.CENTER); //so it's given the room it needs
		//DiePanel coolDicePanel = new DiePanel();
		//add(turn.getDiePanel(), BorderLayout.EAST);
	}

	class nextPlayerListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			nextPlayer();
		}
	}
	
	class SuggestionListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			addSuggestions("SUGGESTION");
		}
	}

	
	public void addSuggestions(String s){
		log.append(s + "\n");
	}
	
	public void nextPlayer(){
		clue.EndTurn();
		
	}

	public void giveClueGame(ClueGame clueGame) {
		// TODO Auto-generated method stub
		clue = clueGame;
		//clue.EndTurn();
	}

	public void setRoll(int i) {
		// TODO Auto-generated method stub
		dp.setFace(i);
	}

	public void setTurn(String name) {
		// TODO Auto-generated method stub
		currentPlayer.setText(name);
	}



}

