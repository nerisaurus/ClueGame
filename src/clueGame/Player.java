package clueGame;

import java.awt.Color;
import java.text.Format.Field;

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
	private int startingRow;
	private int startingColumn;
	
	public Player(String name, String color, int startingRowPosition, int startingColumnPosition) {
		this.name = name;
		this.color = convertColor(color);
		this.startingRow = startingRowPosition;
		this.startingColumn = startingColumnPosition;
	}
	
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getStartingRow() {
		return startingRow;
	}
	
	public int getStartingColumn() {
		return startingColumn;
	}
}
