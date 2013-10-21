package clueGame;
/*
 * The Player class is the super class for the two 
 * types of players, the ComputerPlayer and a HumanPlayer.
 * This class will have at least two fields, a name and
 * a list of cards. My player class has a method named
 * disproveSuggestion (see failing tests description).
 * Note that more fields will be added when we 
 * program the GUI.
 */
public class Player {
	private String name;
	private Color color;
	private int[] startingPosition;
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int[] getStartingPosition() {
		return startingPosition;
	}
}
