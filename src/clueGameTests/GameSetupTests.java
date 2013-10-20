package clueGameTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ClueGame;

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
}
