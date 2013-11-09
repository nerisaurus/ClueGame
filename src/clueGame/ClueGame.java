package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

import clueGameGUI.ClueControlPanel;
import clueGameGUI.SuggestionDialog;

public class ClueGame extends JFrame implements ComponentListener {
	//File Names
	public static final String BOARD = "board.csv";
	public static final String LEGEND = "legend.csv"; // format: room character, room name, color, label y position, label x position
	public static final String PERSON_CARDS = "person_cards.csv"; // format: name, color, starting row, starting col. (first player is human)
	public static final String WEAPON_CARDS = "weapon_cards.csv"; // format: name
	public static final String ROOM_CARDS = "room_cards.csv"; //format: name
	private static UIManager UI;

	//The important stuff: Board, list of players, deck of cards, Solution
	private Board board;
	private Map<String, LinkedList<Player>> players; // see note in constructor
	private LinkedList<Card> deck;
	private Solution solution;
	private int numPeople, numWeapons, numRooms;

	//These store which file is which:
	private String peopleCardsFile;
	private String weaponCardsFile;
	private String roomCardsFile;

	//Stuff Detective Notes
	private DetectiveNotesDialog dNotes;
	private ArrayList<String> people, rooms, weapons;

	//Boolean Game Logic
	public boolean gameOngoing; //false until game begins, false after someone wins.  Otherwise true.
	boolean playerTurn; //true if it is the player's turn
	public boolean hasActed; //true if the player has either: made a move, or made an accusation this round
	public boolean suggestionDialogOpen; //set to true when the suggestion dialog is opened, and false when it is closed
	public boolean accusationDialogOpen; //as the above, but for the accusation dialog
	//(and don't forget Board's highlightTargets boolean, which isn't here but has a similar purpose)

	//Similarly, here's a bit of logic for the AI:
	private Solution goodAccusation = null;
	public boolean timerStop = true;

	//Two Panels for the JFrame
	ClueControlPanel controls;
	SuggestionDialog s;
	//public ClueControlFrame ccf = new ClueControlFrame();

	//get buttons

	public ClueGame() {
		this.players = new HashMap<String, LinkedList<Player>>();
		this.setBoard(new Board());

		this.getBoard().setPlayerMap(players);
		this.people = new ArrayList<String>();
		this.rooms = new ArrayList<String>();
		this.weapons = new ArrayList<String>();
		setupFrame();
	}

	public ClueGame(String legend, String board, String people, String weapons, String rooms, boolean runningTest) {
		//files
		this.peopleCardsFile = people;
		this.weaponCardsFile = weapons;
		this.roomCardsFile = rooms;
		this.people = new ArrayList<String>();
		this.rooms = new ArrayList<String>();
		this.weapons = new ArrayList<String>();

		//setup empty players hash
		// NOTE: There may be some coding overhead to maintain this but
		// it insures that we always know where the human player is
		// also creates flexibility in the number of computer players
		// versus human players in the game.
		// Added a getAllPlayers() method to make it a little easer
		// to, well, get all of the players whether or not they 
		// are human or computer.
		setPlayers();

		//board loads its own config files
		this.setBoard(new Board(board, legend));
		this.getBoard().setPlayerMap(players);

		this.deck = new LinkedList<Card>();
		this.solution = new Solution();


		//these guys are called in the test, calling them here again doubles the method calls
		// therefore we skip them if we are running the tests.
		if(!runningTest){ 
			loadConfigFiles();
			buildSolution();
			dealCards();
			setupFrame();
			controls.giveClueGame(this);
			gameOngoing = true;
			//startWithHuman();
		}
	}

	private void startWithHuman() {
		//We roll the die:
		int humanRoll = rollDie();
		controls.setRoll(humanRoll);

		//Set "Whose Turn" to the player's name
		controls.setCurrentPlayerTheme(players.get("Human").getFirst());

		//Calculate Targets:
		players.get("Human").getFirst().pickTarget(humanRoll, getBoard());

		//Now we're ready for the player's turn to begin
		playerTurn = true;
		getBoard().setHighlightTargets(true);
		hasActed = false;

		//And repaint the board (targets are now highlighted)
		getBoard().repaint();
	}

	//JFrame initialization methods
	private void setupFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue in SPACE");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setBackground(Color.BLACK);
		menuBar.add(createFileMenu());	
		//two halves

		//Let's let the board keep track of its size:
		board.addComponentListener(this);

		add(getBoard(), BorderLayout.CENTER);
		controls = new ClueControlPanel(this);
		//sidePanel.add(mcp, BorderLayout.EAST);
		add(controls, BorderLayout.EAST);


		setGUILookAndFeel("CDE/Motif");
		dNotes = new DetectiveNotesDialog(people, rooms, weapons);

		//Setting Frame Size
		getBoard().setPreferredSize(new Dimension(getBoard().getPanelWidth(), getBoard().getPanelHeight()));
		getBoard().addMouseListener(new boardClickListener());
		//Add further elements in here if needed.
		pack();
	}

	class boardClickListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			boardClick(e.getX(), e.getY());
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

	public void loadConfigFiles() {
		try {
			loadDeck();
			loadPlayers();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void buildSolution() {
		//prepare deck index for each type of card
		//then add card to solution and remove from the deck
		int personIndex = (int) (0 + (Math.random() * (5))); // 6 possible people - 1
		this.solution.setPerson(deck.get(personIndex));
		deck.remove(personIndex);

		int weaponIndex = (int) (5 + (Math.random() * (5))); // 6 possible weapons - 1
		this.solution.setWeapon(deck.get(weaponIndex));
		deck.remove(weaponIndex);

		int roomIndex = (int) (10 + (Math.random() * (8))); // 9 possible rooms - 1
		this.solution.setRoom(deck.get(roomIndex));
		deck.remove(roomIndex);

		//For Testing (or Cheating) Purposes Only:
		//System.out.println(this.solution);
	}

	public void dealCards() {	
		do{
			for(Player p: getAllPlayers()){
				if(deck.size() == 0)
					break;
				else{
					int indexR = new Random().nextInt(deck.size());
					p.addCardToHand(deck.get(indexR));
					deck.remove(indexR);
				}
			}
		}while(deck.size() > 1);
	}

	public Card handleSuggestion(Player accusingPlayer, Solution suggestion, boolean updatePanel) {
		if (suggestion.getPerson().getName().equals((players.get("Human")).getFirst().getName())){
			players.get("Human").getFirst().setLocation(accusingPlayer.getCurrentColumn(), accusingPlayer.getCurrentRow());
		} else{
			for (int i = 0; i < players.get("Computer").size(); i++){
				if(suggestion.getPerson().getName().equals(players.get("Computer").get(i).getName())) {
					players.get("Computer").get(i).setLocation(accusingPlayer.getCurrentColumn(), accusingPlayer.getCurrentRow());
				}
			}
		}


		for(Player p: getPlayersSansCurrent(accusingPlayer)) {
			Card c = p.disproveSuggestion(suggestion);
			if(c != null) {
				//Update Suggestion Log:
				if(updatePanel) {
					controls.addSuggestionToLog(accusingPlayer, suggestion, c);
				}

				//Let all the players see it:
				for(Player seer : players.get("Computer")) {
					seer.seesCard(c);
				}

				//And return the card for further logic (ai logic)
				return c;		
			}
		}

		//And if it's not disproven:
		goodAccusation = suggestion;
		//Update Suggestion Log:
		if(updatePanel) {
			controls.addSuggestionToLog(accusingPlayer, suggestion, null);
		}
		return null;
	}

	// HELPERS ****************************************************************
	public void loadPlayers() throws FileNotFoundException {
		FileReader file = new FileReader(peopleCardsFile);
		Scanner reader = new Scanner(file);
		int row = 0;
		while(reader.hasNextLine()) {
			String[] line = reader.nextLine().split(",");
			String name = line[0];
			String color = line[1];
			int startingRowPosition = Integer.parseInt(line[2]);
			int startingColumnPosition = Integer.parseInt(line[3]);			

			if(row == 0){
				HumanPlayer player = new HumanPlayer(name, color, 
						startingRowPosition, startingColumnPosition);
				players.get("Human").add(player);
			}
			else{
				ComputerPlayer player = new ComputerPlayer(name, color, 
						startingRowPosition, startingColumnPosition, deck);
				players.get("Computer").add(player);
			}
			row++;
		}
		reader.close();
	}

	public void loadDeck() throws FileNotFoundException {
		loadPeopleCards();
		loadWeaponCards();
		loadRoomCards();
	}

	public void loadPeopleCards() throws FileNotFoundException {
		FileReader file = new FileReader(peopleCardsFile);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String[] line = reader.nextLine().split(",");
			String playerName = line[0];
			Card card = new Card(playerName, CardType.PERSON);
			people.add(playerName);
			deck.add(card);
			this.numPeople++;
		}
		reader.close();
	}

	public void loadWeaponCards() throws FileNotFoundException {
		FileReader file = new FileReader(weaponCardsFile);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String weaponName = reader.nextLine();
			Card card = new Card(weaponName, CardType.WEAPON);
			weapons.add(weaponName);
			deck.add(card);
			this.numWeapons++;
		}
		reader.close();
	}

	public void loadRoomCards() throws FileNotFoundException {
		FileReader file = new FileReader(roomCardsFile);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String roomName = reader.nextLine();
			Card card = new Card(roomName, CardType.ROOM);
			rooms.add(roomName);
			deck.add(card);
			this.numRooms++;
		}
		reader.close();
	}

	public int rollDie() { //rolls a 6-sided die (or simulates that, with Random)
		Random die = new Random();
		int roll = die.nextInt(6) + 1;
		return roll;
	}

	public boolean testAccusation(Solution accusation, Player accuser, boolean isPlayer){
		boolean won = false;

		if(solution.equals(accusation)) {
			won = true;
			gameOngoing = false; //The game has ended

			board.setWinnerColor(accuser.getColor());
			board.repaint();

			//Adds the accusation to the log:
			controls.addAccusationToLog(accuser, accusation, won);

			//Now to make a Dialog:

			//First, we have to do some setup:
			String message, title, name, action;
			String murderer, murderWeapon, murderRoom;

			//Get the name of our lucky detective and his discoveries:
			name = accuser.getName();
			murderer = accusation.getPerson().getName();
			murderWeapon = accusation.getWeapon().getName();
			murderRoom = accusation.getRoom().getName();
			action = " was on the case!"; //Default action statement

			if(name.equals(murderer)){
				//Since all of our characters have unisex names, let's have some fun randomness.
				boolean male = new Random().nextBoolean();
				if(male){
					action = " turned himself in.";
				} else {
					action = " turned herself in.";
				}
			}

			//Adjust text based on whether the human won:
			if (name.equals(players.get("Human").getFirst().getName())) {
				name = "you";
				title = "You won!";
				action = " were on the case!";
				//And if you're also the murderer:
				if(murderer.equals(players.get("Human").getFirst().getName())){
					murderer = "you";
					action = " ...turned yourself in?";
					title = "You won...?";
				}
			} else {
				title = "You were outwitted!";
				//And if you're also the murderer:
				if(murderer.equals(players.get("Human").getFirst().getName())){
					murderer = "you";
					action = " could put you behind bars.";
					title = "You were caught!";
				}
			}


			//Get the accusation he made and string it all into a sentence.
			message = "Turns out the murderer was " + murderer + " all along!  "
					+ "Yes, " + murderer + " on the planet " + murderRoom
					+ "\n" + "with the " + murderWeapon + ".  "
					+ "Good thing " + name + action;

			int option = JOptionPane.showOptionDialog(null,
					message,
					title,
					2,
					JOptionPane.PLAIN_MESSAGE,
					null,
					new String[] {"Play Again", "That is Enough For Now"},
					null);
			if (option == 0){ //Play Again
				setVisible(false);
				ClueGame.main(null);
			}
			else { //Goodbye
				System.exit(0);
			}

		}
		else{
			//(only for human players - the dialog of failed accusations):
			if (accuser.getName().equals(players.get("Human").getFirst().getName())){
				String message = "You give an impassioned indictment that the foul deed was committed by:\n" 
						+"\""+ accusation.getPerson().getName() + " on the planet " + accusation.getRoom().getName() + " with the " + accusation.getWeapon().getName() + "!\"" +
						"\n" + "But... you are wrong.";
				JOptionPane.showOptionDialog(null,
						message,
						"A Failure of a Detective!",
						1,
						JOptionPane.PLAIN_MESSAGE,
						null,
						new String[] {"I promise it won't happen again, your Honour!"},
						null);
				controls.addAccusationToLog(accuser, accusation, won);
			}
		}
		return won;

	}
	public void aiTurn(Player ai, int aiRoll) {
		//Should we make an accusation?
		if(ai.makeAccusation(goodAccusation) != null) {
			//If they do make an accusation, let's test it:
			boolean won = testAccusation(ai.makeAccusation(goodAccusation), ai, false);
			//Then add this accusation to the log:
			controls.addAccusationToLog(ai, ai.makeAccusation(goodAccusation), won);

			//Then a reset.  If they didn't win, it must not be the right thing, and
			//other AI know this (they "know" that any AI will pick that suggestion
			//right away, so after one turn they already abandon it as the proper idea
			goodAccusation = null;

		} else {
			//Move:
			ai.pickTarget(aiRoll, getBoard());

			//Are they in a room now?
			if(getBoard().getRoomCellAt(ai.getCurrentRow(), ai.getCurrentColumn()) != null) {
				//Make a suggestion:
				RoomCell r = getBoard().getRoomCellAt(ai.getCurrentRow(), ai.getCurrentColumn());
				char init = r.getInitial();
				String s = getBoard().getRooms().get(init);
				Card roomCard = new Card (s, CardType.ROOM);
				//(First we have to give the ai a room card to start its suggestion from)
				Solution suggestion = ai.makeSuggestion(roomCard);
				handleSuggestion(ai, suggestion, true);
			}

			//And, of course, we have to repaint our board if they moved
			getBoard().repaint();
		}
	}

	//This checks without the laf (Look and Feel) can be implemented.  If so, it sets it up.
	//This is just to move the rather bulky piece of code out of main()
	public static void setGUILookAndFeel(String laf) {
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if (laf.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	//BUTTON VALIDITY CHECKS: *************************
	//these methods answer the question "Should this click do anything right now?"
	public boolean isValidEndTurn(){
		//The player must have a turn to be ending, must have already acted, and can't have other critical dialogs open at the time
		if (gameOngoing){
			if(playerTurn && hasActed && !accusationDialogOpen && !suggestionDialogOpen) {
				return true;
			} else {
				String message = null;
				if(!playerTurn){
					message = "It is not your turn.";
				} else if(!hasActed) {
					message = "Please move or make an accusation first.";
				} else if(accusationDialogOpen) {
					message = "You cannot end your turn while making an accusation.";
				} else if(suggestionDialogOpen){
					message = "You cannot end your turn while making a suggestion.";
				}
				//we give them a notification of what they're doing wrong and then do nothing:
				JOptionPane.showMessageDialog(null, message);
				return false;
			}
		}
		return false;
	}

	public boolean isValidAccusationTime(){
		//It must be the player's turn, she must have not already
		//acted (moved or made an earlier accusation) and can't have other critical dialogs open at the time
		//(suggestionDialogOpen isn't relevant, since suggestions are only made after moves)
		if (gameOngoing){
			if(playerTurn && !hasActed && !accusationDialogOpen) {
				return true;
			} else {
				String message = null;
				if(!playerTurn){
					message = "It is not your turn.";
				} else if(hasActed) {
					message = "You have already acted this round.  Sorry.";
				} else if(accusationDialogOpen) {
					message = "You are already making an accusation.";
				}
				//we give them a notification of what they're doing wrong and then do nothing:
				JOptionPane.showMessageDialog(null, message);
				return false;
			}
		}
		return false;

	}

	public boolean isValidBoardClick(){
		//This is just to check whether clicking on the board at all is valid.
		//This does not check whether the particular click location is a valid
		//target.  Thus all we need to know is that the player hasn't already moved,
		//(but that it is his turn), and doesn't have an accusation dialog open.
		if(playerTurn && !hasActed && !accusationDialogOpen && gameOngoing) {
			return true;
		} else {
			//Since the board isn't a button, we don't expect players
			//to feel it is unresponsive if it doesn't respond to random
			//clicking.  Instead, having dialogs telling them that they
			//shouldn't click could be bothersome.  Thus, we just don't
			//respond at all to invalid clicks.
			return false;
		}
	}

	//BUTTON ACTIONS *******************************************
	//If the Button or Mouse Click is valid, call these guys:
	//THESE ASSUME THAT THE ACTION CALLED IS VALID as per the above methods.
	public void EndTurn(){
		//The turn ends, so we adjust our logic accordingly:
		playerTurn = false;

		//Run through all the AI turns:
		Timer tick = null;
		if(timerStop){
			int delay = 1000; //milliseconds
			TimerListener timer = new TimerListener(players.get("Computer"));
			tick = new Timer(delay, timer);
			tick.start();	
		} else {
			tick.stop();
			timerStop = true;
		}
	}

	public void makeAccusation(){
		//This necessitates a change to our logic:
		accusationDialogOpen = true;
		getBoard().setHighlightTargets(false); 

		//And of course a repaint:
		getBoard().repaint();

		//Pop up the Accusation Dialog
		controls.createAccusationDialog();
	}

	public void boardClick(int x, int y){ //pass in the raw x,y data of a mouse click.  This'll calculate its board location for you.
		//Calculate location: the floor of the point divided by cell size
		int cellX, cellY;
		cellX = x / getBoard().getCellDimensions();
		cellY = y / getBoard().getCellDimensions();

		//Check if location is valid:
		boolean validTarget = false;
		for(BoardCell target : getBoard().getTargets()) {
			if(cellX == target.getCol() && cellY == target.getRow()) {
				validTarget = true;
			}
		}
		if (gameOngoing){
			if(validTarget) {
				//Move the player
				players.get("Human").getFirst().setLocation(cellX, cellY); 
				//"getFirst()" Only one human player - but more complicated logic could be used if we wanted to expand for
				//multiple players.

				//Since the player has moved, we set our game logic to note this:
				hasActed = true;
				getBoard().setHighlightTargets(false);

				//Since the board has changed, we repaint the board
				getBoard().repaint();

				if(getBoard().getRoomCellAt(cellY, cellX) != null) {
					suggestionDialogOpen = true;
					controls.createSuggestionDialog(board.getRooms().get(getBoard().getRoomCellAt(cellY, cellX).getInitial()));
					//suggestion made
				}
			} else {
				JOptionPane.showMessageDialog(null, "You cannot move there.");
				return;
			}
		}

	}


	// GETTERS ****************************************************************
	// Used for testing purposes only. (or are they?)
	public Map<String, LinkedList<Player>> getPlayers() {
		return players;
	}

	public LinkedList<Card> getDeck() {
		return deck;
	}

	public Board getBoard() {
		return board;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution s) {
		solution = s;
	}

	// Return a list of all players human and computer alike.
	public LinkedList<Player> getAllPlayers() {	
		LinkedList<Player> allPlayers = new LinkedList<Player>();
		for(String playerType: players.keySet()){
			for(Player p: players.get(playerType)){
				allPlayers.add(p);
			}
		}
		return allPlayers;		
	}

	public LinkedList<Player> getPlayersSansCurrent(Player current) {	
		LinkedList<Player> allPlayers = getAllPlayers();
		LinkedList<Player> otherPlayers = new LinkedList<Player>();
		//load bottom
		for(int i = allPlayers.indexOf(current)+1; i<allPlayers.size(); i++){
			otherPlayers.add(allPlayers.get(i));
		}
		//load top
		for(int i = 0; i<allPlayers.indexOf(current); i++){
			otherPlayers.add(allPlayers.get(i));
		}
		return otherPlayers;		
	}



	public ArrayList<String> getPeople() {
		return people;
	}

	public ArrayList<String> getRooms() {
		return rooms;
	}

	public ArrayList<String> getWeapons() {
		return weapons;
	}

	public boolean checkAccusation(Solution accusation) {
		if(solution.equals(accusation))
			return true;
		else
			return false;
	}

	// Used in testing only.
	public void addPlayer(String playerType, Player player) {
		this.players.get(playerType).add(player);
	}

	public void setPlayers() {
		this.players = new HashMap<String, LinkedList<Player>>();
		LinkedList<Player> emptyHumanList = new LinkedList<Player>();
		this.players.put("Human", emptyHumanList);
		LinkedList<Player> emptyComputerList = new LinkedList<Player>();
		this.players.put("Computer", emptyComputerList);
	}
	// MENU
	private JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File"); 
		menu.setBackground(Color.BLACK);
		menu.setForeground(Color.WHITE);
		menu.add(createFileExitItem());
		menu.add(createFileNotesItem());
		return menu;
	}

	private JMenuItem createFileExitItem()
	{
		JMenuItem exit = new JMenuItem("Exit");
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.WHITE);
		class MenuItemListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String message = "Are you sure?";
				String title = "Goodbye?";
				int exit = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
				if(exit == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		}
		exit.addActionListener(new MenuItemListener());
		return exit;
	}		
	private JMenuItem createFileNotesItem()
	{
		JMenuItem notes = new JMenuItem("Detective notes");
		notes.setBackground(Color.BLACK);
		notes.setForeground(Color.WHITE);
		class MenuItemListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dNotes.setTheme(getPlayers().get("Human").getFirst());
				dNotes.setVisible(true);
			}
		}
		notes.addActionListener(new MenuItemListener());
		return notes;
	}



	public static void main(String[] args) {
		UIStuff();



		//Set the Basic GUI to Metal (Java's default) Look and Feel:
		// - File Menu
		// - Detective Panel
		// - Scrollbar
		// - Buttons
		ClueGame.setGUILookAndFeel("CDE/Motif");
		//ClueGame.setGUILookAndFeel("Metal");
		//Set the Basic GUI to Metal (Java's default) Look and Feel:
		// - File Menu
		// - Detective Panel
		// - Scrollbar
		// - Buttons
		ClueGame.setGUILookAndFeel("Metal");

		//Initialize Just About Everything:
		ClueGame clue = new ClueGame(LEGEND, BOARD,
				PERSON_CARDS, WEAPON_CARDS, ROOM_CARDS, false);

		//Set the rest of the GUI to CDE/Motif:
		//- Dialogs
		//- JOptionPane Dialogs
		ClueGame.setGUILookAndFeel("CDE/Motif");

		//Figure out who our characters are: (people stores a list of strings of all character names)
		Object[] characterOptions = clue.people.toArray();
		//Input Dialog: Pick your Character:
		String characterPick = (String) JOptionPane.showInputDialog(null, 
				"Select Your Character:", 
				"Want to Play Clue?", 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				characterOptions, 
				clue.getPlayers().get("Human").getFirst().getName());

		//If we picked one:
		if ((characterPick != null) && (characterPick.length() > 0)) {

			//Change Our Player
			for(Player p : clue.getPlayers().get("Computer")){
				if(p.getName().equals(characterPick)) {
					clue.getPlayers().get("Human").getFirst().switchWithPlayer(p);

				}
			}

			//Last Bit of Setup (since it needs to know who the human is first):
			clue.startWithHuman();
			clue.setTheme(clue.getPlayers().get("Human").getFirst());
			//Let us see our board:
			clue.setVisible(true);

			//Sets the Screen to Open in the Center of the Screen:
			Dimension sd = Toolkit.getDefaultToolkit().getScreenSize(); 
			Dimension fd = clue.getSize(); 
			if (fd.height > sd.height) 
				fd.height = sd.height; 
			if (fd.width > sd.width) 
				fd.width = sd.width; 
			clue.setLocation((sd.width - fd.width) / 2, (sd.height - fd.height) / 2); 
		} else {
			System.exit(0);
		}	
	}


	private void setTheme(Player first) {
		controls.setHumanTheme(first);
		controls.setCurrentPlayerTheme(first);

	}

	public void setBoard(Board board) {
		this.board = board;
	}

	//Our Timer:
	private class TimerListener implements ActionListener {
		LinkedList<Player> bots;
		//ClueGame game;
		int counter = 0;
		int roll;

		public TimerListener(LinkedList<Player> bots) {
			super();
			this.bots = bots;
			counter = 0;

			this.roll = rollDie();
			controls.setCurrentPlayerTheme(bots.get(counter));
			controls.setRoll(roll);
		}

		public void actionPerformed(ActionEvent e) {
			if(counter < bots.size()){
				aiTurn(bots.get(counter), roll);	
			} else if(counter == bots.size()) {
				timerStop = true;

				//Set "Whose Turn" to the player's name
				controls.setCurrentPlayerTheme(players.get("Human").getFirst());
				//Roll the die
				int humanRoll = rollDie();

				//Set the diePanel to display the roll
				controls.setRoll(humanRoll);

				//Calculate Targets:
				players.get("Human").getFirst().pickTarget(humanRoll, getBoard());

				//Now we're ready for the player's turn to begin
				playerTurn = true;
				getBoard().setHighlightTargets(true);
				hasActed = false;

				//And repaint the board (targets are now highlighted)
				getBoard().repaint();
			}
			counter++;
			this.roll = rollDie();
			if(counter < bots.size()) {
				controls.setCurrentPlayerTheme(bots.get(counter));
				controls.setRoll(roll);
			}
			getBoard().repaint();
		} 
	}


	//Component Listener Events:
	@Override
	public void componentResized(ComponentEvent e) { 
		board.setPanelHeight(board.getHeight());
		board.setPanelWidth(board.getWidth());
		board.resetCellDimensions();
		board.repaint();	
	}

	//(the ones we don't care about:)
	@Override
	public void componentHidden(ComponentEvent e) {	}
	@Override
	public void componentMoved(ComponentEvent e) {	}
	@Override
	public void componentShown(ComponentEvent e) { }
	
	//UI Monstrosity Function
	private static void UIStuff() {
		UI=new UIManager();


		UI.put("MenuItem.selectionBackground",new ColorUIResource(255,255,255));
		UI.put("MenuItem.selectionForeground",new ColorUIResource(0,0,0));
		UI.put("Menu.selectionBackground",new ColorUIResource(255,255,255));
		UI.put("Menu.selectionForeground",new ColorUIResource(0,0,0));

		UI.put("OptionPane.background",new ColorUIResource(0,0,0));
		UI.put("OptionPane.messageForeground",new ColorUIResource(255,255,255));

		UI.put("Panel.background",new ColorUIResource(0,0,0));
		UI.put("Panel.foreground",new ColorUIResource(255,255,255));

		UI.put("Button.background",new ColorUIResource(0,0,0));
		UI.put("Button.foreground",new ColorUIResource(255,255,255));
		UI.put("Button.select",new ColorUIResource(0,0,0));
		UI.put("Button.highlight",new ColorUIResource(255,255,255));
		UI.put("Button.shadow",new ColorUIResource(255,255,255));

		UI.put("ComboBox.background",new ColorUIResource(0,0,0));
		UI.put("ComboBox.foreground",new ColorUIResource(255,255,255));
		UI.put("ComboBox.selectionBackground",new ColorUIResource(255,255,255));
		UI.put("ComboBox.selectionForeground",new ColorUIResource(0));

		UI.put("CheckBox.background",new ColorUIResource(0,0,0));
		UI.put("CheckBox.foreground",new ColorUIResource(255,255,255));

		UI.put("Label.background",new ColorUIResource(0,0,0));
		UI.put("Label.foreground",new ColorUIResource(255,255,255));

		UI.put("TextArea.background",new ColorUIResource(0,0,0));
		UI.put("TextArea.foreground",new ColorUIResource(255,255,255));

		UI.put("TextField.background",new ColorUIResource(0,0,0));
		UI.put("TextField.foreground",new ColorUIResource(255,255,255));

		UI.put("TextPane.font", new Font("Arial ", 12, 12));

		UI.put("ScrollBar.background",new ColorUIResource(0,0,0));
		UI.put("ScrollBar.foreground",new ColorUIResource(255,255,255));
		UI.put("ScrollBar.darkShadow",new ColorUIResource(255,255,255));
		UI.put("ScrollBar.track",new ColorUIResource(0,0,0));
		UI.put("ScrollBar.thumb",new ColorUIResource(0,0,0));
		UI.put("ScrollBar.thumbHighlight",new ColorUIResource(255,255,255));
		UI.put("ScrollBar.thumbShadow",new ColorUIResource(255,255,255));
		UI.put("ScrollBar.trackHighlight",new ColorUIResource(255,255,255));
		UI.put("ScrollBar.trackShadow",new ColorUIResource(255,255,255));
	}

}
