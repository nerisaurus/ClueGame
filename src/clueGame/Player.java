package clueGame;

import java.awt.Color;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

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
	private ArrayList<Card> hand;
	
	public Player() {
		super();	
		this.hand = new ArrayList<Card>();
	}
	
	public Player(String name, String color, int startingRowPosition, int startingColumnPosition) {
		this.name = name;
		this.color = convertColor(color);
		this.startingRow = startingRowPosition;
		this.startingColumn = startingColumnPosition;	
		this.hand = new ArrayList<Card>();
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
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public void addCardToHand(Card c) {
		hand.add(c);
		seesCard(c);
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
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	
	
	public void setLocation(int startingRow, int startingColumn) {
		this.startingRow = startingRow;
		this.startingColumn = startingColumn;
	}

	public int pickTarget(int steps, Board board) {
		return 0; //Overridden by ComputerPlayer and HumanPlayer
	}

	public void seesCard(Card card) {
		return; //Overridden by ComputerPlayer
	}
	
	public void setUnseenCards(LinkedList<Card> cards){
		return; //Overridden by ComputerPlayer
	}

	public Solution makeSuggestion(Card roomCard) {
		return null; //Overridden by both HumanPlayer and ComputerPlayer
	}
}
