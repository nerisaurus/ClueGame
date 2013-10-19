package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int height,width;
	private String layoutFile, legend;
	private boolean[] visited;
	private Set<BoardCell> targets;
	private Map<Integer, LinkedList<Integer>> adjacencyMatrix;
	
	private String walkway_char;   //Tells us which character maps to "Space" or "Walkway"
	//tells us which element of the legend is 'space' or 'walkway'
	private static final int WHICH_LINE_IS_WALKWAY = 11;
	//We assume the 11th element (1-indexed, not 0-indexed) of the legend 
	//is the 'walkway' object.  Adjust if you need to.

	public Board() {
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character,String>();
		this.adjacencyMatrix = new HashMap<Integer, LinkedList<Integer>>();
		this.targets = new HashSet<BoardCell>();
		this.layoutFile = "ClueLayout.csv";
		this.legend = "ClueLegend.txt";
	}
	
	public Board(String layout, String legend) {
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character,String>();
		this.adjacencyMatrix = new HashMap<Integer, LinkedList<Integer>>();
		this.targets = new HashSet<BoardCell>();
		this.layoutFile = layout;
		this.legend = legend;
	}

	public void loadConfigFiles() {
		try {
			loadLegend();
			loadBoard();
		}catch(BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void loadBoard() throws BadConfigFormatException, FileNotFoundException {
		FileReader file = new FileReader(layoutFile);
		Scanner reader = new Scanner(file);
		int row = 0;
		int col = 0;
		while(reader.hasNextLine()) {
			col = 0;
			for(String layoutCell: reader.nextLine().split(",")){
				BoardCell cell;
				if(rooms.containsKey(layoutCell.charAt(0))) {
					if(layoutCell.equals(walkway_char))
						cell = new WalkwayCell(row, col);
					else if(layoutCell.length() == 2) //a door
						cell = new RoomCell(row, col, layoutCell.charAt(0), layoutCell.charAt(1));
					else
						cell = new RoomCell(row, col, layoutCell.charAt(0));
					//now to actually put the cell into our cell collection
					cells.add(cell);
					//increment the column
					col++;
				}
				else
					//throw new BadConfigFormatException();
					throw new BadConfigFormatException("'" + layoutCell.charAt(0) + "' is not a recognized room. Error in: " + layoutFile);
			}
			//Logic to keep the board rectangular
			if(row == 0){ //if this is the first row, we want to know how many columns it has
				this.width = col;
			} else { //if it's not - we want to test to make sure that it has the same number of columns
				if(col != this.width){
					throw new BadConfigFormatException("Line " + row + " of the file: " 
							+ layoutFile + " does not have the same dimensions as previous lines.");
				}
			}

			//increment the row
			row++;
		}
		this.height = row;
		reader.close(); //closes the scanner for the board
	}
				
				
	
	public void loadLegend() throws BadConfigFormatException, FileNotFoundException {
		FileReader file = new FileReader(legend);
		Scanner reader = new Scanner(file);
		int counter = 1;
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] individual = line.split(", ");
			if(individual.length == 2 && individual[0].length() != 0 && individual[1].length() != 0) {
				char key = individual[0].charAt(0);
				String entry = individual[1];
				rooms.put(key,entry);
				if(counter == WHICH_LINE_IS_WALKWAY){
					walkway_char = individual[0];
				}
				counter++;
			}else {
				throw new BadConfigFormatException("Cannot process line " + counter + " of " + legend + " - too many or too few entries");
			}		
			
		}
		reader.close();
	}
	
	public int calcIndex(int row, int col) {
		return ((row*width) + col);
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		return getRoomCellAt(calcIndex(row,col));
	}
	
	public RoomCell getRoomCellAt(int index){
		if(cells.get(index).isRoom()) {
			return (RoomCell) cells.get(index);
		}else {
			return null;
		}
	}
	
	public void startTargets(int row, int col, int steps)  {
		int index = calcIndex(row, col);
		targets = new HashSet<BoardCell>();
		Arrays.fill(visited, false);
		visited[index] = true;
		calcTargets(index, steps);
		visited[index] = false;
	}
	
	public void calcTargets(int index, int steps) {
		LinkedList<Integer> adjacent_squares = getAdjList(index);
		for(int adjacent : adjacent_squares){
			if(steps == 1) { //all steps have been taken
				targets.add(getCellAt(adjacent));
			} else if( getCellAt(adjacent).isRoom() && getRoomCellAt(adjacent).isDoorway()) { //we enter a room
				targets.add(getCellAt(adjacent));
			} else {
				visited[adjacent] = true;
				calcTargets(adjacent, steps - 1);
			}
		}
		visited[index] = false;
	}
		
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	
	public void calculateAdjacencies() {
		visited = new boolean[cells.size()];
		for(int row=0; row < height; row++) {
			for(int column=0; column < width; column++) {
				LinkedList<Integer> adjacentCells = new LinkedList<Integer>();
				BoardCell cell = getCellAt(calcIndex(row,column));
				
				if(cell.isRoom() && cell.isDoorway()) {
					RoomCell door = (RoomCell) cell;
					//add the cell that the door opens to
					switch(door.getDoorDirection()){
					case DOWN:
						adjacentCells.add(calcIndex(row + 1, column));
						break;
					case UP:
						adjacentCells.add(calcIndex(row - 1, column));
						break;
					case LEFT:
						adjacentCells.add(calcIndex(row, column - 1));	
						break;
					case RIGHT:
						adjacentCells.add(calcIndex(row, column + 1));
						break;
					default:
						break;								
					}
				} else {
					// NOT top row
					if(row > 0) {
						if(cell.isWalkway()){
							if(getCellAt(row - 1, column).isWalkway() || 
									getRoomCellAt(row - 1, column).getDoorDirection() == DoorDirection.DOWN)
							adjacentCells.add(calcIndex(row - 1, column));
						}
						else{ //inside a room
							
						}
					}					
					// NOT bottom row
					if(row < height-1) {
						if(cell.isWalkway()){
							if(getCellAt(row + 1, column).isWalkway() || 
									getRoomCellAt(row + 1, column).getDoorDirection() == DoorDirection.UP)
							adjacentCells.add(calcIndex(row + 1, column));
						}
						else{ //inside a room
							
						}
						
					}
					// NOT left column
					if(column > 0) {
						if(cell.isWalkway()){
							if(getCellAt(row, column - 1).isWalkway() || 
									getRoomCellAt(row, column -1).getDoorDirection() == DoorDirection.RIGHT)
							adjacentCells.add(calcIndex(row, column - 1));
						}
						else{ //inside a room
							
						}
						
					}
					// NOT right column
					if(column < width - 1) {
						if(cell.isWalkway()){
							if(getCellAt(row, column + 1).isWalkway() || 
									getRoomCellAt(row, column + 1).getDoorDirection() == DoorDirection.LEFT)
							adjacentCells.add(calcIndex(row, column + 1));
						}
						else{ //inside a room
							
						}	
					}	
				}
				adjacencyMatrix.put(calcIndex(row,column), adjacentCells);
			}
		}
	}
		
	public LinkedList<Integer> getAdjList(int index) {
		LinkedList<Integer> adjacent_squares = new LinkedList<Integer>();
		for(int square : adjacencyMatrix.get(index)){
			if(!visited[square]) {
				adjacent_squares.add(square);
			}
		}
		return adjacent_squares;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return getCellAt(calcIndex(row,col)); //to override for multi-input
	}
	
	public BoardCell getCellAt(int index) {
		return cells.get(index);
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getNumColumns() {
		return width;
	}

	public int getNumRows() {
		return height;
	}

}
