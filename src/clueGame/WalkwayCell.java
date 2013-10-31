package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int row, int col) {
		super(row,col);
	}
	
	@Override
	public Boolean isWalkway() {
		return true;
	}

	@Override
	public void draw(Graphics g, int cellDimensions) {
		g.setColor(color);
		g.fillRect(cellDimensions * col, cellDimensions * row, cellDimensions, cellDimensions);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(cellDimensions * col, cellDimensions * row, cellDimensions, cellDimensions);
	}

}
