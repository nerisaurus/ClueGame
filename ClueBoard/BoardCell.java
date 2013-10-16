package ClueBoard;

public class BoardCell {
	private int row, col;
	
	public BoardCell(int r, int c) {
		row = r;
		col = c;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}

}
