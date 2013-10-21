package clueGameTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ClueGame;
import clueGame.Player;

public class GameSetupTests {
	private static ClueGame clue;
	public static final String BOARD = "board.csv";
	public static final String LEGEND = "legend.csv";
	//players.csv format:
	//	name,color,starting row,starting column
	//the first player in the list is the human player.
	public static final String PLAYERS = "players.csv";
	//person_cards.csv format:
	//	
	public static final String PERSON_CARDS = "person_cards.csv";
	//weapon_cards.csv format:
	//	
	public static final String WEAPON_CARDS = "weapon_cards.csv";
	//room_cards.csv format:
	//	
	public static final String ROOM_CARDS = "room_cards.csv";
	
	@BeforeClass
	public static void setUp() {
		clue = new ClueGame(BOARD, LEGEND, PLAYERS, 
							PERSON_CARDS, WEAPON_CARDS, ROOM_CARDS);
		clue.loadConfigFiles();
	}
	
	@Test
	public void setupPlayers() {
		//ClueGame will store players in a hash table of the form
		//players = { "Human" => Player, "Computer" => Player[] }

		//test 1: value of "Human" should be 1 Player
		assertEquals(1, clue.getPlayers().get("Human").size());
		
		//test 2: value of "Computer" should be 5 Players
		assertEquals(5, clue.getPlayers().get("Computer").size());
		
		//test 3: each player should have a name, color, and starting location
		assertEquals("red", clue.getPlayers().get("Human").get(0).getName());
		assertEquals(java.awt.Color.RED, clue.getPlayers().get("Human").get(0).getColor());
		assertEquals(15, clue.getPlayers().get("Human").get(0).getStartingRow()); //row
		assertEquals(2, clue.getPlayers().get("Human").get(0).getStartingColumn()); //column
		
		//we could test everything with something like this...
		/*
		for(Player player: clue.getPlayers().get("Computer")){
				
		}
		*/
		//but all we need to do is this...
		assertEquals("blue", clue.getPlayers().get("Computer").get(3).getName());
		assertEquals(java.awt.Color.BLUE, clue.getPlayers().get("Computer").get(3).getColor());
		assertEquals(17, clue.getPlayers().get("Computer").get(3).getStartingRow());
		assertEquals(17, clue.getPlayers().get("Computer").get(3).getStartingColumn());
		assertEquals("yellow", clue.getPlayers().get("Computer").get(4).getName());
		assertEquals(java.awt.Color.YELLOW, clue.getPlayers().get("Computer").get(4).getColor());
		assertEquals(22, clue.getPlayers().get("Computer").get(4).getStartingRow());
		assertEquals(8, clue.getPlayers().get("Computer").get(4).getStartingColumn());
	}
	
	@Test
	public void setupDeck() {
		// The ClueGame deck should have 21 cards
		assertEquals(21, clue.getDeck().size());
		// 6 People, 6 Weapons, 9 Rooms
		int people = 0;
		int weapons = 0;
		int rooms = 0;
		for(Card c: clue.getDeck()){
			switch(c.getType()){
				case PERSON:
					people++;
					break;
				case WEAPON: 
					weapons++;
					break;
				case ROOM:
					rooms++;
					break;
				default:
					break;
			}
		}
		assertEquals(6, people);
		assertEquals(6, weapons);
		assertEquals(9, rooms);
		
		// The deck should have these cards
		assertEquals("white", clue.getDeck().get(1).getName());
		assertEquals(CardType.PERSON, clue.getDeck().get(1).getType());
		assertEquals("Lightsaber", clue.getDeck().get(9).getName());
		assertEquals(CardType.WEAPON, clue.getDeck().get(9).getType());
		assertEquals("Jupiter", clue.getDeck().get(16).getName());
		assertEquals(CardType.ROOM, clue.getDeck().get(16).getType());
	}
	
	@Test
	public void allCardsAreDealt() {
		
	}
}

/*players.csv
red,RED,15,2
white,WHITE,1,20
green,GREEN,8,8
purple,PURPLE,8,15
blue,BLUE,17,17
yellow,YELLOW,22,8
 */