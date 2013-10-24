package clueGame;

public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		super();
	}

	public HumanPlayer(String name, String color, int startingRowPosition, int startingColumnPosition) {
		super(name, color, startingRowPosition, startingColumnPosition);
	}
	
	public Card disporveSuggestion(Solution suggestion) {
		return null;
	}

}
