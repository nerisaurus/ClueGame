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
		board.startTargets(getStartingRow(), getStartingColumn(), steps);
		return -1;
	}

}
