package clueGame;

import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection {RIGHT, LEFT, UP, DOWN, NONE}
	public DoorDirection doorDirection;
	private char roomInitial;
	
	@Override
	public Boolean isRoom() {
		return true;
	}
	
	@Override
	public Boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) {
			return true;
		}else {
			return false;
		}
	}
	
	public RoomCell(int row, int col, char roomInitial) {
		super(row, col);
		this.roomInitial = roomInitial;
		doorDirection = DoorDirection.NONE;
	}
	
	public RoomCell(int row, int col, char roomInitial, char doorDirection) {
		super(row, col);
		this.roomInitial = roomInitial;
		if(doorDirection == 'U')
			this.doorDirection = DoorDirection.UP;
		else if(doorDirection == 'D')
			this.doorDirection = DoorDirection.DOWN;
		else if(doorDirection == 'L')
			this.doorDirection = DoorDirection.LEFT;
		else if(doorDirection == 'R')
			this.doorDirection = DoorDirection.RIGHT;
		else
			this.doorDirection = DoorDirection.NONE;
	}
	
	public char getInitial() {
		return roomInitial;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	@Override
	void draw(Graphics g) {
		g.setColor(color);
		//g.drawRect(cellDimensions * col, cellDimensions * row, cellDimensions, cellDimensions);
		g.fillRect(cellDimensions * col, cellDimensions * row, cellDimensions, cellDimensions);
	}
}
