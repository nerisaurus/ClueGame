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

}
