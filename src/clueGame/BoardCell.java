package clueGame;

public class BoardCell {
	private int row, col;
	
	public BoardCell(int r, int c) {
		row = r;
		col = c;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}
	public Boolean isWalkway(){ //is the cell a walkway cell?
		return false;
	}
	
	public Boolean isRoom(){ //is the cell a room cell?
		return false;
	}
	
	public Boolean isDoorway(){ //is this cell a doorway?
		return false;
	}
	
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}
	
	//TODO: add draw function
	//I would put in a stub, but even the arguments and type are unknown at this point.

}
