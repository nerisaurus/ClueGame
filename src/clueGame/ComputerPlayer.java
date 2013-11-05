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
		unseenCards = (LinkedList<Card>) deck.clone();
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
		Random random = new Random(); //set up random number generation
		Solution suggestion = new Solution(); //and our suggestion
		
		//Figure out which cards we have to pick between
		ArrayList<Card> unseenWeapons = new ArrayList<Card>(), unseenPeople = new ArrayList<Card>();
		for(Card unseen : unseenCards){
			if(unseen.getType() == CardType.WEAPON){
				unseenWeapons.add(unseen);
			} else if(unseen.getType() == CardType.PERSON){
				unseenPeople.add(unseen);
			}
		}
		
		suggestion.setWeapon(unseenWeapons.get(random.nextInt(unseenWeapons.size())));
		suggestion.setPerson(unseenPeople.get(random.nextInt(unseenPeople.size())));
		suggestion.setRoom(roomCard);
		
		return suggestion;
	}

	@Override
	public void setUnseenCards(LinkedList<Card> cards){
		unseenCards = (LinkedList<Card>) cards.clone();
	}

	//pickTarget: takes a number of steps (dice roll) and Board object
	//(the surrounding board) and returns the index of the square it
	//(semi-randomly) chooses to move to. (also moves the player there, of course)
	@Override
	public int pickTarget(int steps, Board board) {
		Random random = new Random();
		Set<BoardCell> potentialTargets;
		ArrayList<Integer> potentialTargetLocations = new ArrayList<Integer>();

		board.startTargets(getCurrentRow(), getCurrentColumn(), steps);
		potentialTargets = board.getTargets(); 

		for(BoardCell target : potentialTargets) {
			if(target.isRoom()){
				RoomCell roomTarget = (RoomCell) target;
				if(roomTarget.getInitial() != lastVisited) {
					lastVisited = roomTarget.getInitial();
					setLocation(roomTarget.getCol(), roomTarget.getRow());
					return board.calcIndex(roomTarget.getRow(), roomTarget.getCol());
				}
			}
			potentialTargetLocations.add(board.calcIndex(target.getRow(), target.getCol()));
		}
		
		//Randomly pick a target:
		int pick = random.nextInt(potentialTargetLocations.size());

		//Find the location that that target is at:
		int pickIndex = potentialTargetLocations.get(pick);
		setLocation(board.getCellAt(pickIndex).getCol(), board.getCellAt(pickIndex).getRow());
		
		return pickIndex;
	}
	
	@Override
	public Solution makeAccusation(Solution goodAccusation) {
		//TODO: AI Smartening. Add logic for other Accusation possibilities.
		return goodAccusation;
	}
	
	public char getLastVisited(){
		return lastVisited;
	}
	
	public void setLastVisited(char room) {
		lastVisited = room;	
	}

}
