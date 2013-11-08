package clueGame;

import java.util.ArrayList;

public class Solution {
	private Card person, weapon, room;
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Solution)) {
			return false;
		} else {
			Solution guess = (Solution) other;
			if(guess.getPerson().equals(person) && guess.getWeapon().equals(weapon) && guess.getRoom().equals(room)) {
				return true;
			} else {
				return false;
			}
		}
	}
	public Solution() {
		super();
	}
	
	public Solution(Card person, Card weapon, Card room) {
		super();
		setPerson(person);
		setWeapon(weapon);
		setRoom(room);
	}

	public Card getPerson() {
		return person;
	}

	public void setPerson(Card person) {
		if(person.getType() == CardType.PERSON)
			this.person = person;
		else
			throw new RuntimeException(person + " is not a person.");
	}

	public Card getWeapon() {
		return weapon;
	}

	public void setWeapon(Card weapon) {
		if(weapon.getType() == CardType.WEAPON)
			this.weapon = weapon;
		else
			throw new RuntimeException(weapon + " is not a weapon.");
	}

	public Card getRoom() {
		return room;
	}

	public void setRoom(Card room) {
		if(room.getType() == CardType.ROOM)
			this.room = room;
		else
			throw new RuntimeException(room + " is not a room.");
	}

	public ArrayList<Card> getCards() {
		ArrayList<Card> soln = new ArrayList<Card>();
		soln.add(person);
		soln.add(room);
		soln.add(weapon);
		return soln;
	}
	
	public String toString(){
		return getPerson() + "||" + getWeapon() + "||" + getRoom();
	}
	
}
