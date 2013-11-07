package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class BoardCell {
	protected int row, col;
	protected Color color;
	
	public BoardCell(int r, int c) {
		row = r;
		col = c;
	}
	
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column =" + col + "]";
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public abstract void draw(Graphics g, int cellDimensions);
	
	public void highlight(Graphics g, int cellDimensions){
		g.setColor(Color.WHITE);
		g.drawOval(cellDimensions * col, cellDimensions * row, cellDimensions, cellDimensions);
	}

}
