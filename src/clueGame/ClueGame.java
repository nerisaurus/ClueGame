package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ClueGame {
	private Board board;
	private Map<String, LinkedList<Player>> players; // see note in constructor
	private LinkedList<Card> deck;
	private ArrayList<Card> solution;
	private int numPeople, numWeapons, numRooms;

	//file strings
	private String peopleCardsFile;
	private String weaponCardsFile;
	private String roomCardsFile;

	public ClueGame() {
		this.players = new HashMap<String, LinkedList<Player>>();
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
		this.players = new HashMap<String, LinkedList<Player>>();
		LinkedList<Player> emptyHumanList = new LinkedList<Player>();
		this.players.put("Human", emptyHumanList);
		LinkedList<Player> emptyComputerList = new LinkedList<Player>();
		this.players.put("Computer", emptyComputerList);
		
		this.deck = new LinkedList<Card>();
		this.solution = new ArrayList<Card>();
		
		//loadConfigFiles();
		//buildSolution();
		//dealCards();
	}
	
	public void loadConfigFiles() {
		try {
			loadPlayers();
			loadDeck();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void buildSolution() {
		//prepare deck index for each type of card
		//then add card to solution and remove from the deck
		int personIndex = (int) (0 + (Math.random() * (5))); // 6 possible people - 1
		this.solution.add(deck.get(personIndex));
		deck.remove(personIndex);
		int weaponIndex = (int) (5 + (Math.random() * (5))); // 6 possible weapons - 1
		this.solution.add(deck.get(weaponIndex));
		deck.remove(weaponIndex);
		int roomIndex = (int) (10 + (Math.random() * (8))); // 9 possible rooms - 1
		this.solution.add(deck.get(roomIndex));
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
						startingRowPosition, startingColumnPosition);
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
	
	public ArrayList<Card> getSolution() {
		return solution;
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
}
