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
import clueGame.Solution;
import static org.junit.Assert.*;

import org.junit.Test;

public class GameActionTests {
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
		clue = new ClueGame(LEGEND, BOARD,  
				PERSON_CARDS, WEAPON_CARDS, ROOM_CARDS);
		clue.loadConfigFiles();
	}

	@Test
	public void testAccusation() {
		//We first set a Solution
		Solution realSolution = new Solution(clue.getDeck().get(1), clue.getDeck().get(6), clue.getDeck().get(13));
		clue.setSolution(realSolution);
		//First, a correct accusation:
		Solution correctAccusation = new Solution(clue.getDeck().get(1), clue.getDeck().get(6), clue.getDeck().get(13));
		assertTrue(clue.checkAccusation(correctAccusation));
		//now a wrong Person Card
		Solution wrongSuspect = new Solution(clue.getDeck().get(4), clue.getDeck().get(6), clue.getDeck().get(13));
		assertFalse(clue.checkAccusation(wrongSuspect));
		//now for a wrong Weapon Card
		Solution wrongWeapon = new Solution(clue.getDeck().get(1), clue.getDeck().get(8), clue.getDeck().get(13));
		assertFalse(clue.checkAccusation(wrongWeapon));
		//now for a wrong Room Card
		Solution wrongRoom = new Solution(clue.getDeck().get(1), clue.getDeck().get(6), clue.getDeck().get(18));
		assertFalse(clue.checkAccusation(wrongRoom));		
	}

	/*
	 * Part II is worth 54 points.

(5) Put clear comments in your tests so the grader can easily 
	tell that you've met the requirements.
(17 total) Disprove a suggestion. Tests include: (2 pts) tests 
	that one player returns the only possible card 
	(one room, one person, one weapon), (2 pts) a test that one 
	player randomly chooses between two possible cards, (4 pts) a 
	test that players are queried in order, (2 pts) tests involving 
	the human player, and (2 pts) a test that the player whose turn 
	it is does not return a card. Also correct implementation of 
	all tests (5 pts).
(12) Select a target. Tests include: a set of targets that include 
	a room, a random selection from a set of targets that don't include 
	a room, and a test that considers the last visited room.
(10) Computer player makes a suggestion. Tests include: one test 
	where there only suggestion is possible (because the player has 
	seen all but one person and weapon card, and the room must be the 
	current location) and one with some random possibilities (e.g., the 
	player has not seen Miss Scarlet or Professor Plum... some suggestions 
	should include Miss Scarlet, some should include Professor Plum).
(10) Git log that clearly shows failing/passing process was followed. 
	Include the name of your repository.
	 */

	@Test
	public void disprovingASuggestion() {
		//cards for the player
		Card jupiterCard = new Card("Jupiter", CardType.ROOM);
		Card miniNukeCard = new Card("Mini-Nuke", CardType.WEAPON);
		Card spaceCadetCard = new Card("Space Cadet Grey", CardType.PERSON);		

		//cards to test suggestion
		Card marsCard = new Card("Mars", CardType.ROOM);
		Card lightsaberCard = new Card("Lightsaber", CardType.WEAPON);
		Card androidCard = new Card("Android Orange", CardType.PERSON);


		Solution badSuggestion = new Solution(androidCard, lightsaberCard, marsCard);
		Solution suggestionGoodRoom = new Solution(androidCard, lightsaberCard, jupiterCard);
		Solution suggestionGoodWeapon = new Solution(androidCard, miniNukeCard, marsCard);
		Solution suggestionGoodPlayer = new Solution(spaceCadetCard, lightsaberCard, marsCard);
		
		Player p = new Player();
		p.addCardToHand(jupiterCard);
		p.addCardToHand(miniNukeCard);
		p.addCardToHand(spaceCadetCard);

		assertEquals(null, p.disproveSuggestion(badSuggestion));
		assertEquals(marsCard, p.disproveSuggestion(suggestionGoodRoom));
		assertEquals(lightsaberCard, p.disproveSuggestion(suggestionGoodWeapon));
		assertEquals(androidCard, p.disproveSuggestion(suggestionGoodPlayer));

	}


	@Test
	public void testTargets_RoomPriority() {
		Board board = clue.getBoard();
		Player testSubject = new Player();
		
		//Target includes a room: 2-step 281 (should pick 279)
		testSubject.setLocation(11, 6);
		testSubject.setLastVisited('M'); //just to make sure it knows it is far away
		int target1 = testSubject.pickTarget(2, board);
		System.out.println(target1);
		assertEquals('N', board.getRoomCellAt(target1).getInitial());
		assertEquals('N',testSubject.getLastVisited()); //tests that lastVisited is set properly

		//Target includes a room that's closer than other targets (target logic terminates properly): 4-step 281 (should be in Neptune)
		testSubject.setLocation(11, 6);
		testSubject.setLastVisited('M'); //just to make sure it knows it is far away
		int target4 = testSubject.pickTarget(4, board);
		assertEquals('N', board.getRoomCellAt(target4).getInitial());
		assertEquals('N',testSubject.getLastVisited());
	}
	
	@Test
	public void testTargets_Randomness() {
		Player testSubject = new Player();
		Board board = clue.getBoard();
		
		//Random target (no rooms possible): 1-step 181 - should randomly pick between 180, 182, 156, 206
		int counter180 = 0, counter182 =0, counter156 = 0, counter206 = 0, counterOther = 0;
		for(int i = 1; i < 100; i++) {
			testSubject.setLocation(7, 6);
			int target = testSubject.pickTarget(1, board);
			switch(target){
			case 180:
				counter180++;
				break;
			case 182:
				counter182++;
				break;
			case 156:
				counter156++;
				break;
			case 206:
				counter206++;
				break;
			default:
				counterOther++;
				break;								
			}
		}
		boolean randomness = false;
		if(counter180 > 10 && counter182 > 10 && counter156 > 10 && counter206 > 10) {
			randomness = true; //making statisticians cry
		}	
		assertEquals(0, counterOther);
		assertTrue(randomness);
		
		//Random target (room was last visited): 1-step 249 - should randomly pick between 248 and 274
		testSubject.setLastVisited('V'); //Making sure it believes this was the last visited room
		int counter248 = 0, counter274 = 0;
		counterOther = 0; //reusing the old counter for the same purpose

		for(int i = 1; i < 100; i++) {
			testSubject.setLocation(9, 24);
			int target = testSubject.pickTarget(1, board);
			switch(target){
			case 248:
				counter248++;
				break;
			case 274:
				counter274++;
				break;
			default:
				counterOther++;
				break;								
			}
		}
		randomness = false;
		if(counter248 > 20 && counter274 > 20) {
			randomness = true; //making statisticians cry
		}
		assertEquals(0, counterOther);
		assertTrue(randomness);
	}
}
