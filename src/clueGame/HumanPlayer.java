package clueGame;

public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		super();
	}

	public HumanPlayer(String name, String color, int startingRowPosition, int startingColumnPosition) {
		super(name, color, startingRowPosition, startingColumnPosition);
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		for(Card c: suggestion.getCards()){
			if(getHand().contains(c)) {
				return c;
			}
		}
		return null;
	}

}
