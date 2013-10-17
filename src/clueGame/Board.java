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

import javax.sound.sampled.Line;

import clueGame.RoomCell.DoorDirection;



public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int height,width;
	private String layout, legend;
	private boolean[] visited;
	private Set<BoardCell> targets;
	private Map<Integer, LinkedList<Integer>> adjMatx;

	public Board() {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character,String>();
		adjMatx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		layout = "ClueLayout.csv";
		legend = "ClueLegend.txt";
	}
	
	public Board(String la, String le) {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character,String>();
		adjMatx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		layout = la;
		legend = le;
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
		FileReader file = new FileReader(layout);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String[] row = reader.nextLine().split(",");
			if (height == 0) {
				width = row.length;
			}
			else {
				if (row.length != width) {
					throw new BadConfigFormatException("Some column lengths are not " +
							"the same in your board");
				}
			}
			
			int index = 0; //reset index at new row
			
			for(String cell : row) {
				if(cell.length() == 1) {					
					if(cell.charAt(0) == 'W')
						cells.add(new WalkwayCell(height, index));
					else 
						cells.add(new RoomCell(height, index, cell.charAt(0)));
				}
				else if(cell.length() == 2) {
					if(cell.charAt(1) == 'U')
						cells.add(new RoomCell(height, index, cell.charAt(0), DoorDirection.UP));
					else if(cell.charAt(1) == 'D')
						cells.add(new RoomCell(height, index, cell.charAt(0), DoorDirection.DOWN));
					else if(cell.charAt(1) == 'R')
						cells.add(new RoomCell(height, index, cell.charAt(0), DoorDirection.RIGHT));
					else if(cell.charAt(1) == 'L')
						cells.add(new RoomCell(height, index, cell.charAt(0), DoorDirection.LEFT));
					else
						cells.add(new RoomCell(height, index, cell.charAt(0)));
				}
				else {
					throw new BadConfigFormatException("Cells have an imporper format");
				}
				index++;
			}
			height++;
		}
	}
	
	public void loadLegend() throws BadConfigFormatException, FileNotFoundException {
		FileReader file = new FileReader(legend);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] individual = line.split(",");
			if(individual[0].length() == 0 || individual[1].length() == 0 || individual.length > 2) {
				throw new BadConfigFormatException("Legend is not formated properly");
			}else {
				char key = individual[0].charAt(0);
				String entry = individual[1].substring(1, individual[1].length());
				rooms.put(key,entry);
			}
		}
	}
	
	public int calcIndex(int row, int col) {
		return ((row*width) + col);
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		if(cells.get(calcIndex(row,col)).isRoom()) {
			return (RoomCell) cells.get(calcIndex(row,col));
		}else {
			return null;
		}
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
	
	public void startTargets(int row, int col, int steps)  {
		int index = calcIndex(row, col);
		targets = new HashSet<BoardCell>();
		Arrays.fill(visited, false);
		visited[index] = true;
		calcTargets(index, steps);
	}
	
	public void calcTargets(int index, int steps) {
		LinkedList<BoardCell> adjs = new LinkedList<BoardCell>();
		visited[index] = true;
		for(Integer i : getAdj(index)){
			if(!visited[i]) {
				adjs.add(cells.get(i));
			}
		}
		for(BoardCell adj : adjs) {
			boolean exited = false;
			for(int i=0; i< cells.size(); i++) {
				if(getCellAt(i).isDoorway() && visited[i]) {
					exited = true;
				}
			}
			int area = calcIndex(adj.getRow(),adj.getCol());
			visited[area] = true;
			if(area < cells.size() && area > 0) {
				if(steps == 1) {
					targets.add(adj);
				}else if(getCellAt(area).isDoorway() && !exited) {
					targets.add(adj);
				}else {
					calcTargets(area, steps-1);
				}
			}
			visited[area] = false;
		}
	}
		
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void calculateAdjacencies() {
		visited = new boolean[cells.size()];
		LinkedList<Integer> adjacentCells; 
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				adjacentCells = new LinkedList<Integer>();
				//Nathan here - why always reuse getCellAt(calcIndex(i,j))?
				BoardCell cell = getCellAt(calcIndex(i,j));
				
//sample of what i'm thinking ****************************************
					
					// NOT top row
					if(i>0) {
						if(cell.isDoorway()){
							// method call to addDoorCell here that:
							// <1> checks door direction
							// <2> add adj cell
						}
						else if(cell.isWalkway()){
							// add cell above current cell
						}
						else{ //inside a room
							
						}
					}					
					// NOT bottom row // NOT bottom row
					if(i<height-1) {
						if(cell.isDoorway()){
							// method call to addDoorCell here that:
							// <1> checks door direction
							// <2> add adj cell
						}
						else if(cell.isWalkway()){
							// add cell above current cell
						}
						else{ //inside a room
							
						}
						
					}
					// NOT left column
					if(j>0) {
						if(cell.isDoorway()){
							// method call to addDoorCell here that:
							// <1> checks door direction
							// <2> add adj cell
						}
						else if(cell.isWalkway()){
							// add cell above current cell
						}
						else{ //inside a room
							
						}
						
					}
					// NOT right column
					if(j<width-1) {
						if(cell.isDoorway()){
							// method call to addDoorCell here that:
							// <1> checks door direction
							// <2> add adj cell
						}
						else if(cell.isWalkway()){
							// add cell above current cell
						}
						else{ //inside a room
							
						}
						
					}					
// *******************************************************************
					
					
			if(cell.isDoorway() || cell.isWalkway()) {
					// NOT top row
					if(i>0) {
						if(getCellAt(calcIndex(i-1,j)).isDoorway() || getCellAt(calcIndex(i-1,j)).isWalkway()) {
							if(cell.isDoorway()) {
								if(getCellAt(calcIndex(i-1,j)).isWalkway()) {
									adjacentCells.add(calcIndex(i-1,j));
								}
							}else {
								if(getCellAt(calcIndex(i-1,j)).isDoorway()) {
									RoomCell.DoorDirection direction = getRoomCellAt(i-1,j).getDoorDirection();
									if(direction == RoomCell.DoorDirection.DOWN) {
										adjacentCells.add(calcIndex(i-1,j));
									}
								}else {
									adjacentCells.add(calcIndex(i-1,j));
								}
							}
						}
					}
					
					// NOT bottom row
					if(i<height-1) {
						if(getCellAt(calcIndex(i+1,j)).isDoorway() || getCellAt(calcIndex(i+1,j)).isWalkway()) {
							if(cell.isDoorway()) {
								if(getCellAt(calcIndex(i+1,j)).isWalkway()) {
									adjacentCells.add(calcIndex(i+1,j));
								}
							}else {
								if(getCellAt(calcIndex(i+1,j)).isDoorway()) {
									RoomCell.DoorDirection direction = getRoomCellAt(i+1,j).getDoorDirection();
									if(direction == RoomCell.DoorDirection.UP) {
										adjacentCells.add(calcIndex(i+1,j));
									}
								}else {
									adjacentCells.add(calcIndex(i+1,j));
								}
							}
						}
					}
					
					// NOT left column
					if(j>0) {
						if(getCellAt(calcIndex(i,j-1)).isDoorway() || getCellAt(calcIndex(i,j-1)).isWalkway()) {
							if(cell.isDoorway()) {
								if(getCellAt(calcIndex(i,j-1)).isWalkway()) {
									adjacentCells.add(calcIndex(i,j-1));
								}
							}else {
								if(getCellAt(calcIndex(i,j-1)).isDoorway()) {
									RoomCell.DoorDirection direction = getRoomCellAt(i,j-1).getDoorDirection();
									if(direction == RoomCell.DoorDirection.RIGHT) {
										adjacentCells.add(calcIndex(i,j-1));
									}
								}else {
									adjacentCells.add(calcIndex(i,j-1));
								}
							}
						}
					}
					
					// NOT right column
					if(j<width-1) {
						if(getCellAt(calcIndex(i,j+1)).isDoorway() || getCellAt(calcIndex(i,j+1)).isWalkway()) {
							if(cell.isDoorway()) {
								if(getCellAt(calcIndex(i,j+1)).isWalkway()) {
									adjacentCells.add(calcIndex(i,j+1));
								}
							}else {
								if(getCellAt(calcIndex(i,j+1)).isDoorway()) {
									RoomCell.DoorDirection direction = getRoomCellAt(i,j+1).getDoorDirection();
									if(direction == RoomCell.DoorDirection.LEFT) {
										adjacentCells.add(calcIndex(i,j+1));
									}
								}else {
									adjacentCells.add(calcIndex(i,j+1));
								}
							}
						}
					}
					adjMatx.put((Integer) calcIndex(i,j), adjacentCells);
				}else {
					LinkedList<Integer> empty = new LinkedList<Integer>();
					adjMatx.put((Integer) calcIndex(i,j), empty);
				}
			}
		}
	}
		
	public LinkedList<Integer> getAdj(int index) {
		return adjMatx.get(index);
	}

}
