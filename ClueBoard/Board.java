package ClueBoard;
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

import ClueBoard.RoomCell.DoorDirection;


public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows,numCols;
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
			loadLegendConfig();
			loadBoardConfig();
		}catch(BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader file = new FileReader(layout);
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] individual = line.split(",");
			if (numRows == 0) {
				numCols = individual.length;
			} else {
				if (individual.length != numCols) {
					throw new BadConfigFormatException("Some column lengths are not the same in your board");
				}
			}
			int index = 0;
			for(String part : individual) {
				if(!rooms.containsKey(part.charAt(0))) {
					throw new BadConfigFormatException("A room is not formated properly");
				}
				if(index == numCols) {
					index = 0;
				}
				if(part.charAt(0) == 'W') {
					cells.add(new WalkwayCell(numRows, index));
					index++;
				}else if(part.length() == 1) {
					cells.add(new RoomCell(numRows, index, part.charAt(0)));
					index++;
				}else if(part.length() == 2) {
					if(part.charAt(1) == 'U') {
						cells.add(new RoomCell(numRows, index, part.charAt(0), DoorDirection.UP));
					}else if(part.charAt(1) == 'D') {
						cells.add(new RoomCell(numRows, index, part.charAt(0), DoorDirection.DOWN));
					}else if(part.charAt(1) == 'R') {
						cells.add(new RoomCell(numRows, index, part.charAt(0), DoorDirection.RIGHT));
					}else if(part.charAt(1) == 'N') {
						cells.add(new RoomCell(numRows, index, part.charAt(0)));
					}else {
						cells.add(new RoomCell(numRows, index, part.charAt(0), DoorDirection.LEFT));
					}
					index++;
				}else {
					throw new BadConfigFormatException("Cells have an imporper format");
				}
			}
			numRows++;
		}
	}
	
	public void loadLegendConfig() throws BadConfigFormatException, FileNotFoundException {
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
		return ((row*numCols) + col);
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

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void calcTargets(int row, int col, int steps) {
		int index = calcIndex(row, col);
		setupTargets(index, steps);
	}
	
	public void setupTargets(int index, int steps) {
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
	
	public void calcAdjacencies() {
		visited = new boolean[cells.size()];
		LinkedList<Integer> theAdjs; 
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				theAdjs = new LinkedList<Integer>();
				if(getCellAt(calcIndex(i,j)).isDoorway() || getCellAt(calcIndex(i,j)).isWalkway()) {
					if(i>0) {
						if(getCellAt(calcIndex(i-1,j)).isDoorway() || getCellAt(calcIndex(i-1,j)).isWalkway()) {
							if(getCellAt(calcIndex(i,j)).isDoorway()) {
								if(getCellAt(calcIndex(i-1,j)).isWalkway()) {
									theAdjs.add(calcIndex(i-1,j));
								}
							}else {
								if(getCellAt(calcIndex(i-1,j)).isDoorway()) {
									RoomCell.DoorDirection dir = getRoomCellAt(i-1,j).getDoorDirection();
									if(dir == RoomCell.DoorDirection.DOWN) {
										theAdjs.add(calcIndex(i-1,j));
									}
								}else {
									theAdjs.add(calcIndex(i-1,j));
								}
							}
						}
					}
					if(i<numRows-1) {
						if(getCellAt(calcIndex(i+1,j)).isDoorway() || getCellAt(calcIndex(i+1,j)).isWalkway()) {
							if(getCellAt(calcIndex(i,j)).isDoorway()) {
								if(getCellAt(calcIndex(i+1,j)).isWalkway()) {
									theAdjs.add(calcIndex(i+1,j));
								}
							}else {
								if(getCellAt(calcIndex(i+1,j)).isDoorway()) {
									RoomCell.DoorDirection dir = getRoomCellAt(i+1,j).getDoorDirection();
									if(dir == RoomCell.DoorDirection.UP) {
										theAdjs.add(calcIndex(i+1,j));
									}
								}else {
									theAdjs.add(calcIndex(i+1,j));
								}
							}
						}
					}
					if(j>0) {
						if(getCellAt(calcIndex(i,j-1)).isDoorway() || getCellAt(calcIndex(i,j-1)).isWalkway()) {
							if(getCellAt(calcIndex(i,j)).isDoorway()) {
								if(getCellAt(calcIndex(i,j-1)).isWalkway()) {
									theAdjs.add(calcIndex(i,j-1));
								}
							}else {
								if(getCellAt(calcIndex(i,j-1)).isDoorway()) {
									RoomCell.DoorDirection dir = getRoomCellAt(i,j-1).getDoorDirection();
									if(dir == RoomCell.DoorDirection.RIGHT) {
										theAdjs.add(calcIndex(i,j-1));
									}
								}else {
									theAdjs.add(calcIndex(i,j-1));
								}
							}
						}
					}
					if(j<numCols-1) {
						if(getCellAt(calcIndex(i,j+1)).isDoorway() || getCellAt(calcIndex(i,j+1)).isWalkway()) {
							if(getCellAt(calcIndex(i,j)).isDoorway()) {
								if(getCellAt(calcIndex(i,j+1)).isWalkway()) {
									theAdjs.add(calcIndex(i,j+1));
								}
							}else {
								if(getCellAt(calcIndex(i,j+1)).isDoorway()) {
									RoomCell.DoorDirection dir = getRoomCellAt(i,j+1).getDoorDirection();
									if(dir == RoomCell.DoorDirection.LEFT) {
										theAdjs.add(calcIndex(i,j+1));
									}
								}else {
									theAdjs.add(calcIndex(i,j+1));
								}
							}
						}
					}
					adjMatx.put((Integer) calcIndex(i,j), theAdjs);
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
