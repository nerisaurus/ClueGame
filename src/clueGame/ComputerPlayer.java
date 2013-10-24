package clueGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

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
	private char lastVisited;

	public ComputerPlayer(String name, String color, int startingRowPosition, int startingColumnPosition, LinkedList<Card> deck) {
		super(name, color, startingRowPosition, startingColumnPosition);
		unseenCards = deck;
	}

	public ComputerPlayer() {
		super();
		unseenCards = new LinkedList<Card>();
	}

	@Override
	public void seesCard(Card card) {
		unseenCards.remove(card);
	}

	@Override
	public Solution makeSuggestion(Card roomCard) {
		return null; //TODO: Overwrite
	}

	@Override
	public void setUnseenCards(LinkedList<Card> cards){
		unseenCards = cards;
	}

	//pickTarget: takes a number of steps (dice roll) and Board object
	//(the surrounding board) and returns the index of the square it
	//(semi-randomly) chooses to move to
	@Override
	public int pickTarget(int steps, Board board) {
		Random random = new Random();
		Set<BoardCell> potentialTargets;
		ArrayList<Integer> potentialTargetLocations = new ArrayList<Integer>();

		board.startTargets(getStartingRow(), getStartingColumn(), steps);
		potentialTargets = board.getTargets();

		for(BoardCell target : potentialTargets) {
			if(target.isRoom()){
				RoomCell roomTarget = (RoomCell) target;
				if(roomTarget.getInitial() != lastVisited) {
					lastVisited = roomTarget.getInitial();
					return board.calcIndex(roomTarget.getRow(), roomTarget.getCol());
				}
			}
			potentialTargetLocations.add(board.calcIndex(target.getRow(), target.getCol()));
		}

		int pick = random.nextInt(potentialTargetLocations.size());

		return potentialTargetLocations.get(pick);
	}
	
	public char getLastVisited(){
		return lastVisited;
	}
	
	public void setLastVisited(char room) {
		lastVisited = room;	
	}

}
