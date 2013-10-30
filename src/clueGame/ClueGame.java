package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class ClueGame extends JFrame{
	public static final String BOARD = "board.csv";
	public static final String LEGEND = "legend.csv";
	//person_cards.csv format:
	//	name,color,starting row,starting column
	//the first player in the list is the human player.	
	public static final String PERSON_CARDS = "person_cards.csv";
	//weapon_cards.csv format:
	//	
	public static final String WEAPON_CARDS = "weapon_cards.csv";
	//room_cards.csv format:
	//	
	public static final String ROOM_CARDS = "room_cards.csv";
	
	
	private Board board;
	private Map<String, LinkedList<Player>> players; // see note in constructor
	private LinkedList<Card> deck;
	private Solution solution;
	private int numPeople, numWeapons, numRooms;

	//file strings
	private String peopleCardsFile;
	private String weaponCardsFile;
	private String roomCardsFile;
	
	private DetectiveNotesDialog dNotes;

	public ClueGame() {
		this.players = new HashMap<String, LinkedList<Player>>();
		this.board = new Board();
		setupFrame();
	}
	
	public ClueGame(String legend, String board, String people, String weapons, String rooms) {
		//files
		this.peopleCardsFile = people;
		this.weaponCardsFile = weapons;
		this.roomCardsFile = rooms;
		
		//board loads its own config files
		this.board = new Board(board, legend);
		
		//setup empty players hash
		// NOTE: There may be some coding overhead to maintain this but
		// it insures that we always know where the human player is
		// also creates flexibility in the number of computer players
		// versus human players in the game.
		// Added a getAllPlayers() method to make it a little easer
		// to, well, get all of the players whether or not they 
		// are human or computer.
		setPlayers();
		
		this.deck = new LinkedList<Card>();
		this.solution = new Solution();
		
		loadConfigFiles();
		buildSolution();
		dealCards();
		setupFrame();
	}
	
	//JFrame initialization methods
	private void setupFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SpaceClue");
		setSize(500, 500);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());	
		
		add(board,BorderLayout.CENTER);
		dNotes = new DetectiveNotesDialog(getAllPlayers());
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
	
	public Card handleSuggestion(Player accusingPlayer, Solution suggestion) {
		for(Player p: getPlayersSansCurrent(accusingPlayer)) {
			Card c = p.disproveSuggestion(suggestion);
			if(c != null)
				return c;
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
			deck.add(card);
			this.numPeople++;
		}
		reader.close();
	}
	
	public void loadWeaponCards() throws FileNotFoundException {
		FileReader file = new FileReader(weaponCardsFile);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			Card card = new Card(reader.nextLine(), CardType.WEAPON);
			deck.add(card);
			this.numWeapons++;
		}
		reader.close();
	}
	
	public void loadRoomCards() throws FileNotFoundException {
		FileReader file = new FileReader(roomCardsFile);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			Card card = new Card(reader.nextLine(), CardType.ROOM);
			deck.add(card);
			this.numRooms++;
		}
		reader.close();
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
			menu.add(createFileExitItem());
			menu.add(createFileNotesItem());
			return menu;
		}
		
		private JMenuItem createFileExitItem()
		{
			JMenuItem exit = new JMenuItem("Exit");
			class MenuItemListener implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String message = "Exit";
					JOptionPane.showMessageDialog(null, message);
				}
			}
			exit.addActionListener(new MenuItemListener());
			return exit;
		}		
		private JMenuItem createFileNotesItem()
		{
			JMenuItem notes = new JMenuItem("Detective notes");
			class MenuItemListener implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					dNotes.setVisible(true);
				}
			}
			notes.addActionListener(new MenuItemListener());
			return notes;
		}
	

	
	public static void main(String[] args) {
		//ClueGame clue = new ClueGame();
		ClueGame clue = new ClueGame(LEGEND, BOARD,
							PERSON_CARDS, WEAPON_CARDS, ROOM_CARDS);
		clue.setVisible(true);
	}
}
