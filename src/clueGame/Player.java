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
	//private Color color; //string for now...
	private String color;
	private int startingRow;
	private int startingColumn;
	
	public Player(String name, String color, int startingRowPosition, int startingColumnPosition) {
		this.name = name;
		this.color = color;
		this.startingRow = startingRowPosition;
		this.startingColumn = startingColumnPosition;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getStartingRow() {
		return startingRow;
	}
	
	public int getStartingColumn() {
		return startingColumn;
	}
}
