package clueGame;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * The ComputerPlayer class will need a method to 
 * pick a location from a list of targets (remember
 * that you've already tested creating a
 * list of targets). I called my method pickLocation.
 * This method is described in more detail in the
 * Test Selecting a Target Location section.
 */
public class ComputerPlayer extends Player {
	LinkedList<Card> unseenCards;

	public ComputerPlayer(String name, String color, int startingRowPosition, int startingColumnPosition, LinkedList<Card> deck) {
		super(name, color, startingRowPosition, startingColumnPosition);
		unseenCards = deck;
	}
	
	@Override
	public void seesCard(Card card) {
		return; //TODO: Overwrite
	}
	
	@Override
	public Solution makeSuggestion(Card roomCard) {
		return null; //TODO: Overwrite
	}
	
	@Override
	public void setUnseenCards(LinkedList<Card> cards){
		return; //TODO: Overwrite
	}

}
