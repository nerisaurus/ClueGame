package clueGame;

/*
 * The Card class will represent an individual card. 
 * It will have two instance variables to represent 
 * the name (e.g., "Pipe") and the card type. The card 
 * type will be an enumerated type that represents 
 * PERSON, WEAPON and ROOM. The Card class will require 
 * getters and setters and should have an equals method 
 * (google to see how to write equals).
 */

public class Card {
	private String name;
	private CardType type;

	public Card(String name, CardType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public CardType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + ", type=" + type + "]";
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Card)) {
			return false;
		} else {
			Card otherCard = (Card) other;
			if(otherCard.getName().equals(name) && otherCard.getType().equals(type)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	
}
