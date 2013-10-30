package clueGame;

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
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
