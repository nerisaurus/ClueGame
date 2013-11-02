package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import clueGame.RoomCell.DoorDirection;

/*
 * The Player class is the super class for the two 
 * types of players, the ComputerPlayer and a HumanPlayer.
 * This class will have at least two fields, a name and
 * a list of cards. My player class has a method named
 * disproveSuggestion (see failing tests description).
 * Note that more fields will be added when we 
 * program the GUI.
 */
public abstract class Player {
	private String name;
	private Color color;
	private int row;
	private int col;	
	private ArrayList<Card> hand;
	
	public Player() {
		super();	
		this.hand = new ArrayList<Card>();
	}
	
	public Player(String name, String color, int startingRowPosition, int startingColumnPosition) {
		this.name = name;
		this.color = convertColor(color);
		this.row = startingRowPosition;
		this.col = startingColumnPosition;	
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
		ArrayList<Card> matches = new ArrayList<Card>();
		for(Card c: suggestion.getCards()){
			if(getHand().contains(c)) {
				matches.add(c);
			}
		}
		if(matches.size() == 1)
			return matches.get(0);
		else if(matches.size() > 1){
			int index = (int) (0 + (Math.random() * (matches.size())));
			return matches.get(index);
		}
		else
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
	
	public int getCurrentRow() {
		return row;
	}
	
	public int getCurrentColumn() {
		return col;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	
	public void setLocation(int newRow, int newColumn) {
		this.row = newRow;
		this.col = newColumn;
	}

	abstract public int pickTarget(int steps, Board board);

	public void seesCard(Card card) {
		return; //Overridden by ComputerPlayer
	}
	
	public void setUnseenCards(LinkedList<Card> cards){
		return; //Overridden by ComputerPlayer
	}

	public Solution makeSuggestion(Card roomCard) {
		return null; //Overridden by ComputerPlayer
	}
	
	
	void draw(Graphics g, int cellDimensions) {
		g.setColor(color);
		int playerSize = cellDimensions / 2; //for aesthetic's sake.
		int offset = (cellDimensions - playerSize) / 2;
		g.fillOval(cellDimensions * col + offset, cellDimensions * row + offset, playerSize, playerSize);
	}

	public Solution makeAccusation(Solution goodAccusation) {
		return null; //Overriden by Computer Player
	}
}
