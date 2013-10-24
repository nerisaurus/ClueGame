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
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
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

		//three more sets of cards for testing
		Card asteroidCard = new Card("Asteroid Miner Magenta", CardType.PERSON);
		Card cosmonautCard = new Card("Cosmonaut Crimson", CardType.PERSON);
		Card senatorCard = new Card("Galactic Senator Cyan", CardType.PERSON);
		Card phaserCard = new Card("Phaser", CardType.WEAPON);
		Card rifleCard = new Card("M41A Pulse Rifle", CardType.WEAPON);
		Card protonCard = new Card("Proton Pack", CardType.WEAPON);
		Card neptuneCard = new Card("Neptune", CardType.ROOM);
		Card earthCard = new Card("Earth", CardType.ROOM);
		Card saturnCard = new Card("Saturn", CardType.ROOM);
		
		//cards to test suggestion
		Card marsCard = new Card("Mars", CardType.ROOM);
		Card lightsaberCard = new Card("Lightsaber", CardType.WEAPON);
		Card androidCard = new Card("Android Orange", CardType.PERSON);		
		
		Player p = new Player();
		
		//Test for one player, one correct match
		Solution badSuggestion = new Solution(androidCard, lightsaberCard, marsCard);
		Solution suggestionGoodRoom = new Solution(androidCard, lightsaberCard, jupiterCard);
		Solution suggestionGoodWeapon = new Solution(androidCard, miniNukeCard, marsCard);
		Solution suggestionGoodPlayer = new Solution(spaceCadetCard, lightsaberCard, marsCard);	
		p.addCardToHand(spaceCadetCard);
		p.addCardToHand(miniNukeCard);
		p.addCardToHand(jupiterCard);
		assertEquals(null, p.disproveSuggestion(badSuggestion));
		assertEquals(jupiterCard, p.disproveSuggestion(suggestionGoodRoom));
		assertEquals(miniNukeCard, p.disproveSuggestion(suggestionGoodWeapon));
		assertEquals(spaceCadetCard, p.disproveSuggestion(suggestionGoodPlayer));	
		
		//Test for one player, multiple possible matches
		Solution allMatchingSuggestion = new Solution(spaceCadetCard, miniNukeCard, jupiterCard);
		//these guys will keep track of how many time a card is picked from
		// the player's hand and returned.
		int personPick = 0;
		int weaponPick = 0;
		int roomPick = 0;
		//loop to test randomness
		for(int i = 0; i<100; i++){
			Card pick = p.disproveSuggestion(allMatchingSuggestion);
			if(pick.equals(spaceCadetCard)) {
				assertEquals(spaceCadetCard, pick); //just to make sure
				personPick++;
			}
			else if(pick.equals(miniNukeCard)) {
				assertEquals(miniNukeCard, pick); //just to make sure
				weaponPick++;
			}
			else if(pick.equals(jupiterCard)) {
				assertEquals(jupiterCard, pick); //just to make sure
				roomPick++;
			}
			else
				//a catch all in case something goes wrong, should always catch something!
				assertTrue(false);
		}
		
		// testing to see that each card was picked, that is randomly
		assertTrue(personPick > 1);
		assertTrue(weaponPick > 1);
		assertTrue(roomPick > 1);
				
		//Test that all players are queried
		//we need some computer players, and a human player
		HumanPlayer human = new HumanPlayer();
		human.addCardToHand(asteroidCard);
		human.addCardToHand(phaserCard);
		human.addCardToHand(neptuneCard);
		ComputerPlayer hal = new ComputerPlayer();
		hal.addCardToHand(cosmonautCard);
		hal.addCardToHand(rifleCard);
		hal.addCardToHand(earthCard);
		ComputerPlayer skynet = new ComputerPlayer();
		skynet.addCardToHand(spaceCadetCard);
		skynet.addCardToHand(miniNukeCard);
		skynet.addCardToHand(jupiterCard);
		ComputerPlayer marvin = new ComputerPlayer();
		marvin.addCardToHand(senatorCard);
		marvin.addCardToHand(protonCard);
		marvin.addCardToHand(saturnCard);
		
		clue.setPlayers();
		clue.addPlayer("Human", human);
		clue.addPlayer("Computer", hal);
		clue.addPlayer("Computer", skynet);
		clue.addPlayer("Computer", marvin);
		
		//made a suggestion which no players could disprove, and ensured that null was returned
		//Solution badSuggestion = new Solution(androidCard, lightsaberCard, marsCard);
		assertEquals(null, clue.handleSuggestion(human, badSuggestion));
		assertEquals(null, clue.handleSuggestion(skynet, badSuggestion));
		//made a suggestion that only the human could disprove, 
		//and ensured that the correct Card was returned.
		Solution onlyHumanSuggestion = new Solution(asteroidCard, lightsaberCard, marsCard);
		assertEquals(asteroidCard, clue.handleSuggestion(marvin, onlyHumanSuggestion));
		assertEquals(asteroidCard, clue.handleSuggestion(skynet, onlyHumanSuggestion));
		
		//by changing the starting player here and letting the chosen card always come from
		//the same person (in this case human) shoud insure that we go though all palyers.
		//current player suggests card in the next player's hand only
		Solution nextPlayersSuggestion = new Solution(androidCard, lightsaberCard, neptuneCard);
		assertEquals(neptuneCard, clue.handleSuggestion(marvin, nextPlayersSuggestion));
		//number of players searched should be 1
		
		//current player suggests card in the last player's hand only
		Solution lastPlayersSuggestion = new Solution(androidCard, lightsaberCard, neptuneCard);
		assertEquals(neptuneCard, clue.handleSuggestion(hal, lastPlayersSuggestion));
		//number of players searched should be 3
	}
	
}
