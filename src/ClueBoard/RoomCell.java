package ClueBoard;

public class RoomCell extends BoardCell {
	public enum DoorDirection {RIGHT, LEFT, UP, DOWN, NONE}
	public DoorDirection doorDirection;
	private char roomInit;
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if(doorDirection != DoorDirection.NONE) {
			return true;
		}else {
			return false;
		}
	}
	
	public RoomCell(int row, int col, char rI) {
		super(row, col);
		roomInit = rI;
		doorDirection = DoorDirection.NONE;
	}
	
	public RoomCell(int row, int col, char rI, DoorDirection dD) {
		super(row, col);
		roomInit = rI;
		doorDirection = dD;
	}
	
	public Object getInitial() {
		return roomInit;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
}
