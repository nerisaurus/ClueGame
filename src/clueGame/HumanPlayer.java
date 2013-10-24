package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		super();
	}

	public HumanPlayer(String name, String color, int startingRowPosition, int startingColumnPosition) {
		super(name, color, startingRowPosition, startingColumnPosition);
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> matches = new ArrayList<Card>();
		for(Card c: suggestion.getCards()){
			if(getHand().contains(c)) {
				matches.add(c);
			}
		}
		//System.out.println(matches);
		if(matches.size() == 1)
			return matches.get(0);
		else if(matches.size() > 1){
			int index = (int) (0 + (Math.random() * (matches.size())));
			System.out.println(index);
			//System.out.println(matches.get(index));
			return matches.get(index);
		}
		else
			return null;
	}

}
