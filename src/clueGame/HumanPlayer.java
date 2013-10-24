package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		super();
	}

	public HumanPlayer(String name, String color, int startingRowPosition, int startingColumnPosition) {
		super(name, color, startingRowPosition, startingColumnPosition);
	}
	
	@Override
	public Solution makeSuggestion(Card roomCard) {
		return null; //TODO: Overwrite
	}

}
