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

		Solution badSuggestion = new Solution(marsCard, lightsaberCard, androidCard);
		Solution suggestionGoodRoom = new Solution(jupiterCard, lightsaberCard, androidCard);
		Solution suggestionGoodWeapon = new Solution(marsCard, miniNukeCard, androidCard);
		Solution suggestionGoodPlayer = new Solution(marsCard, lightsaberCard, spaceCadetCard);
		
		Player p = new Player();
		p.addCardToHand(jupiterCard);
		p.addCardToHand(miniNukeCard);
		p.addCardToHand(spaceCadetCard);

		assertEquals(null, p.disporveSuggestion(badSuggestion));
		assertEquals(marsCard, p.disporveSuggestion(suggestionGoodRoom));
		assertEquals(lightsaberCard, p.disporveSuggestion(suggestionGoodWeapon));
		assertEquals(androidCard, p.disporveSuggestion(suggestionGoodPlayer));
		
	}
	
}
