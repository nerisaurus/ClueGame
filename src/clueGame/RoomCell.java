package clueGame;

import java.awt.Color;
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

		if(doorDirection != DoorDirection.NONE){
			int spaceStationSize = cellDimensions / 4;
			int halfSize = spaceStationSize / 2;
			int halfCell = cellDimensions / 2;
			int x_offset = halfCell - halfSize;
			int y_offset = halfCell - halfSize;
			switch(doorDirection){
			case UP:
				y_offset -= halfCell;
				break;
			case DOWN:
				y_offset += halfCell;
				break;
			case RIGHT:
				x_offset += halfCell;
				break;
			case LEFT:
				x_offset -= halfCell;
				break;
			case NONE:
				//nothing happens - doesn't occur
				break;
			}
			g.setColor(Color.WHITE);
			g.fillOval(cellDimensions * col + x_offset, cellDimensions * row + y_offset, spaceStationSize, spaceStationSize);
		}
	}
}
