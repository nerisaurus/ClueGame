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
	public int pickTarget(int steps, Board board) { //Calculates possible targets so that the human can pick them:
		board.startTargets(getCurrentRow(), getCurrentColumn(), steps);
		return -1;
	}
	
	public String getCard(CardType type){
		String s = "";
		for (Card c : getHand())
			if (c.getType().equals(type))
				s += c.getName() + "\n";
		s.trim();
		return s;
	}

}
