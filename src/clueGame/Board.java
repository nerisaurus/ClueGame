package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private Map<Character,Color> roomColors;
	private Map<Character,Integer> roomLabelVerticalLocation;
	private Map<Character,Integer> roomLabelHorizontalLocation;
	private int height,width;
	private String layoutFile, legend;
	private boolean[] visited;
	private Set<BoardCell> targets;
	private Map<Integer, LinkedList<Integer>> adjacencyMatrix;

	//For Drawing Purposes Only:
	private Map<String, LinkedList<Player>> players;

	//Handy Size Variables for Drawing Purposes:
	private int panelHeight, panelWidth;
	private int cellDimensions = 24;
	
	//Turn logic: (package variable, since it is used primarily in ClueGame)
	boolean highlightTargets;

	//Logic to Allow for different Legends:
	//Tells us which character maps to "Space" or "Walkway"
	private String walkwayChar;
	//Tells us which element of the legend is 'space' or 'walkway'
	private static final int WHICH_LINE_IS_WALKWAY = 11;
	//We assume the 11th line of the legend file is the 'walkway'.
	//Adjust if you need to.

	public Board() {
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character,String>();
		this.roomColors = new HashMap<Character, Color>();
		this.roomLabelVerticalLocation = new HashMap<Character, Integer>();
		this.roomLabelHorizontalLocation = new HashMap<Character, Integer>();
		this.adjacencyMatrix = new HashMap<Integer, LinkedList<Integer>>();
		this.targets = new HashSet<BoardCell>();
		this.layoutFile = "ClueLayout.csv";
		this.legend = "ClueLegend.txt";
		loadConfigFiles();
		visited = new boolean[cells.size()];
		calculateAdjacencies();
	}

	public Board(String layout, String legend) {
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character,String>();
		this.roomColors = new HashMap<Character, Color>();
		this.roomLabelVerticalLocation = new HashMap<Character, Integer>();
		this.roomLabelHorizontalLocation = new HashMap<Character, Integer>();
		this.adjacencyMatrix = new HashMap<Integer, LinkedList<Integer>>();
		this.targets = new HashSet<BoardCell>();
		this.layoutFile = layout;
		this.legend = legend;
		loadConfigFiles();
		visited = new boolean[cells.size()];
		calculateAdjacencies();
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

	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		drawPlayers(g);
		drawLabels(g);
		if(highlightTargets){
			drawTargets(g);
		}
	}

	public void drawGrid(Graphics g) {
		for(BoardCell cell : cells) {
			cell.draw(g, cellDimensions);
		}
		//then, to draw on doors:
		for(BoardCell cell : cells) {
			if(cell.isDoorway()){
				cell.draw(g, cellDimensions);
			}
		}
	}

	public void drawPlayers(Graphics g) {
		int test_counter = 0;
		for(Player player : players.get("Human")){
			player.draw(g, cellDimensions);
		}
		for(Player player : players.get("Computer")){
			player.draw(g, cellDimensions);
		}
	}

	public void drawLabels(Graphics g) {
		Graphics2D g2;
		for(Character roomInitial: rooms.keySet()){
			if(roomLabelVerticalLocation.containsKey(roomInitial)){
				g2 = (Graphics2D)g;
				g2.setColor(Color.BLACK);
				g2.drawString(rooms.get(roomInitial),roomLabelVerticalLocation.get(roomInitial),roomLabelHorizontalLocation.get(roomInitial)); 
			}
		}
	}
	
	public void drawTargets(Graphics g) {
		for(BoardCell target : targets){
			target.highlight(g, cellDimensions);
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
					if(layoutCell.equals(walkwayChar))
						cell = new WalkwayCell(row, col);
					else if(layoutCell.length() == 2) //a door
						cell = new RoomCell(row, col, layoutCell.charAt(0), layoutCell.charAt(1));
					else
						cell = new RoomCell(row, col, layoutCell.charAt(0));
					//now to set the color
					cell.setColor(roomColors.get(layoutCell.charAt(0)));
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

		//and calculate the size of the board:
		this.panelHeight = height * cellDimensions;
		this.panelWidth = width * cellDimensions;

		reader.close(); //closes the scanner for the board
	}	

	public void loadLegend() throws BadConfigFormatException, FileNotFoundException {
		FileReader file = new FileReader(legend);
		Scanner reader = new Scanner(file);
		int counter = 1;
		while(reader.hasNextLine()) {
			String line = reader.nextLine();
			String[] individual = line.split(", ");
			if((individual.length == 2 || individual.length == 3 || individual.length == 5 ) && individual[0].length() != 0 && individual[1].length() != 0) {
				char key = individual[0].charAt(0);
				String entry = individual[1];
				rooms.put(key,entry);
				if(individual.length == 3) {
					Color color = convertColor(individual[2]);
					if(color == null) { 
						throw new BadConfigFormatException(individual[2] + " cannot be converted to a proper Color.");
					}
					roomColors.put(key,color);
				}
				if(individual.length == 5) {
					Color color = convertColor(individual[2]);
					if(color == null) { 
						throw new BadConfigFormatException(individual[2] + " cannot be converted to a proper Color.");
					}
					roomColors.put(key,color); 
					Double vertical = (Double.parseDouble(individual[3]) * cellDimensions);
					Double horizontal = (Double.parseDouble(individual[4]) * cellDimensions);
					roomLabelVerticalLocation.put(key, vertical.intValue());
					roomLabelHorizontalLocation.put(key, horizontal.intValue());
				}
				if(counter == WHICH_LINE_IS_WALKWAY){
					walkwayChar = individual[0];
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

	public void startTargets(int vertical, int horizontal, int steps){
		targets = new HashSet<BoardCell>(); //a blank sheet
		Arrays.fill(visited,false); //to ensure no issues
		int start = calcIndex(vertical,horizontal);
		//System.out.println("width => " + width);
		//System.out.println("calcIndex => " + vertical + ", " + horizontal);
		//System.out.println("startTargets, calcIndex => " + start);
		visited[start] = true;
		calcTargets(start, steps);
		visited[start] = false; //cleanup, just to be safe
	}

	//The recursive meat for startTargets()
	public void calcTargets(int index, int steps) {
		LinkedList<Integer> adjacentSquares = getAdjList(index);
		//System.out.println("adj List => " + getAdjList(index));
		for(int adjacent : adjacentSquares){
			if(steps == 1) { //all steps have been taken
				targets.add(getCellAt(adjacent));
				//System.out.println("Added target => " + getCellAt(adjacent));
			} else if( getCellAt(adjacent).isRoom() && getRoomCellAt(adjacent).isDoorway()) { //we enter a room
				targets.add(getCellAt(adjacent));
				//System.out.println("Added target => " + getCellAt(adjacent));
			} else {
				visited[adjacent] = true;
				calcTargets(adjacent, steps - 1);
			}
		}
		visited[index] = false;
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

	//Getters and Setters (rearrange later)
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

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getBoardHeight() {
		return height;
	}

	public int getBoardWidth() {
		return width;
	}

	public int getNumColumns() {
		return width;
	}

	public int getNumRows() {
		return height;
	}

	public Map<Character, Color> getRoomColors() {
		return roomColors;
	}

	public void setRoomColors(Map<Character, Color> roomColors) {
		this.roomColors = roomColors;
	}

	public void setPlayerMap(Map<String, LinkedList<Player>> players) {
		this.players = players;
	}
	
	public void setCellDimensions(int cellDimensions){
		this.cellDimensions = cellDimensions;
	}

	public int getPanelHeight() {
		return panelHeight;
	}

	public int getPanelWidth() {
		return panelWidth;
	}
	
	public int getCellDimensions() {
		return cellDimensions;
	}

	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor){
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(strColor.trim());        
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}

}
