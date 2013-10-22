package clueGameTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
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
	
	@Before
	public void setUp() {
		clue = new ClueGame(BOARD, LEGEND,  
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
		assertEquals("Cosmonaut Crimson", clue.getPlayers().get("Human").get(0).getName());
		assertEquals(java.awt.Color.RED, clue.getPlayers().get("Human").get(0).getColor());
		assertEquals(15, clue.getPlayers().get("Human").get(0).getStartingRow()); //row
		assertEquals(2, clue.getPlayers().get("Human").get(0).getStartingColumn()); //column
		
		//we could test everything with something like this...
		/*
		for(Player player: clue.getPlayers().get("Computer")){
				
		}
		*/
		//but all we need to do is this...
		assertEquals("Galactic Senator Cyan", clue.getPlayers().get("Computer").get(3).getName());
		assertEquals(java.awt.Color.CYAN, clue.getPlayers().get("Computer").get(3).getColor());
		assertEquals(17, clue.getPlayers().get("Computer").get(3).getStartingRow());
		assertEquals(17, clue.getPlayers().get("Computer").get(3).getStartingColumn());
		assertEquals("Captain Fuschia", clue.getPlayers().get("Computer").get(4).getName());
		assertEquals(java.awt.Color.PINK, clue.getPlayers().get("Computer").get(4).getColor());
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
		assertEquals("Android Orange", clue.getDeck().get(1).getName());
		assertEquals(CardType.PERSON, clue.getDeck().get(1).getType());
		assertEquals("Lightsaber", clue.getDeck().get(9).getName());
		assertEquals(CardType.WEAPON, clue.getDeck().get(9).getType());
		assertEquals("Jupiter", clue.getDeck().get(16).getName());
		assertEquals(CardType.ROOM, clue.getDeck().get(16).getType());
	}

	@Test
	public void goodSolutionFormed() {
		clue.buildSolution();
		// Just want to make sure that the solution has exactly
		// one person, one weapon, and one room.
		// To test this we'll look at each card in the solution,
		// and see if it is the proper type.
		// We should have exactly one of each type and a total of three cards
		// (that is, the deck should have 21 - 3 = 18 cards after building the solution)
		
		assertEquals(CardType.PERSON, clue.getSolution().getPerson().getType());
		assertEquals(CardType.WEAPON, clue.getSolution().getWeapon().getType());
		assertEquals(CardType.ROOM, clue.getSolution().getRoom().getType());
		assertEquals(18, clue.getDeck().size());
	}
	
	@Test
	public void allCardsAreDealt() {
		clue.buildSolution();
		clue.dealCards();
		// this is the deck BEFORE cards are handed out...
		Set<Card> deckActual = new HashSet<Card>(clue.getDeck());
		//...there should be 0 cards in the deck now
		assertEquals(0, deckActual.size());
		// now to build a new deck from the cards that were passed out
		ArrayList<Card> deckCollected = new ArrayList<Card>();
		// just making sure we get inside of the loop with a test
		assertEquals(6, clue.getAllPlayers().size());
		for(Player p: clue.getAllPlayers()){
			// while we are in here let's make sure that the player
			// has either 3 or 4 cards
			boolean fairDeal = false;
			if(p.getHand().size() == 4 || p.getHand().size() == 3)
				fairDeal = true;
			assertTrue(fairDeal);
			// now collect the cards
			for(Card c: p.getHand()){
				deckCollected.add(c);
			}
		}
		// setup the collected deck as a set to remove duplicates...
		Set<Card> deckCollectedNoDup = new HashSet<Card>(deckCollected);
		// deckCollectedNoDup should have 18 cards in it if all cards 
		// were passed out (including 3 in the solution)
		assertEquals(18, deckCollectedNoDup.size());
		
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