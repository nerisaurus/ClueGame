package ClueBoardTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.RoomCell;

public class LoadBoard {
	private static Board board;
	public static final int NUM_ROOMS = 11; //9 "Rooms" + 1 "Walkway" + 1 "Closet" = 11 Entries in the rooms map
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLS = 25;
	
	//Correct Config File Names
	public static final String BOARD_FILE_STR = "board.csv";
	public static final String LEGEND_FILE_STR = "legend.csv";
	
	//"Bad" Config File Names (for Exception Tests)
	public static final String BAD_ROOM_BOARD_FILE_STR = "BAD_ROOM_board.csv";
	public static final String BAD_COLUMN_BOARD_FILE_STR = "BAD_COLUMN_board.csv";
	public static final String BAD_LEGEND_FILE_STR = "BAD_legend.csv";
	
	@BeforeClass
	public static void setUp() {
		board = new Board(BOARD_FILE_STR, LEGEND_FILE_STR);
		board.loadConfigFiles();
	}
	
	//Proper Configuration Tests:
	@Test
	public void boardShouldBeTheCorrectSize() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());
	}
	
	@Test
	public void correctNumberOfRooms() {
		assertEquals(NUM_ROOMS, board.getRooms().size());
	}
	
	@Test
	public void roomLegendShouldBeCorrect() {
		Map<Character, String> rooms = board.getRooms();
		
		//Some Planets
		assertEquals("Mercury", rooms.get('H'));
		assertEquals("Venus", rooms.get('V'));
		assertEquals("Mars", rooms.get('M'));
		assertEquals("Uranus", rooms.get('U'));
		//Other Legend Keys
		assertEquals("Sun", rooms.get('X'));
		assertEquals("Space", rooms.get('O'));
		//This tests a variety of the legend keys, including
		//the first and last lines of the file.
	}

	@Test
	public void doorsShouldExistHaveCorrectDirectionAndInitial() {
		//Tests that:
		//1. Doors Exist
		//2. Doors Have the Correct Direction (UP/DOWN/LEFT/RIGHT)
		//3. Rooms Have the Correct Initials (a cell in the "Earth" room has the initial 'E', for example)
		
		RoomCell room_cell = board.getRoomCellAt(1,11);
		assertTrue(room_cell.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room_cell.getDoorDirection());
		assertEquals('E', room_cell.getInitial());
		
		room_cell = board.getRoomCellAt(5,11);
		assertTrue(room_cell.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room_cell.getDoorDirection());
		assertEquals('E', room_cell.getInitial());
		
		room_cell = board.getRoomCellAt(11,4); 
		assertTrue(room_cell.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room_cell.getDoorDirection());
		assertEquals('N', room_cell.getInitial());
		
		room_cell = board.getRoomCellAt(19,15); 
		assertTrue(room_cell.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room_cell.getDoorDirection());
		assertEquals('J', room_cell.getInitial());
		
		room_cell = board.getRoomCellAt(7,16);  // hallway (not a room, technically, so no room cell)
		assertEquals(null, room_cell); 
		
		room_cell = board.getRoomCellAt(24,23);  // room (but not a doorway)
		assertFalse(room_cell.isDoorway());
	}

	@Test
	public void shouldHaveCorrectNumberOfDoors() {
		int doorTotal = 0;
		for(int i=0; i<NUM_ROWS; i++) {
			for(int j=0; j<NUM_COLS; j++) {
				if(board.getCellAt(i, j).isDoorway())
					doorTotal++;
			}
		}
		assertEquals(23, doorTotal);
	}

	@Test
	public void calcIndexShouldWork() {
		assertEquals(0, board.calcIndex(0,0));
		assertEquals(24,board.calcIndex(0,24));
		assertEquals(20, board.calcIndex(0,20));
		assertEquals(409, board.calcIndex(16,9));
		assertEquals(624, board.calcIndex(24,24));
	}
	
	//Proper Throwing of Exceptions Tests:
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board(BAD_COLUMN_BOARD_FILE_STR, LEGEND_FILE_STR); //Rows don't all have the same number of columns
		b.loadLegend();
		b.loadBoard();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board(BAD_ROOM_BOARD_FILE_STR, LEGEND_FILE_STR); //Incorrect "room" at some point (not listed in LEGEND_FILE_STR)
		b.loadLegend();
		b.loadBoard();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board(BOARD_FILE_STR, BAD_LEGEND_FILE_STR); //Bad Legend File (one line has less than or greater than 2 entries)
		b.loadLegend();
		b.loadBoard();
	}
}

/*
 * 
Tests related to loading the board configuration include:

Ensure that the number of rows and columns is correct
Test that Doors have the correct DoorDirection, 
	that a piece that is not a door returns false for isDoorway, 
	and that the program identified the correct number of doors 
	(you'll probably put a loop in your test method to query all 
	BoardCell objects and count the number that return true for isDoorway).
Test that rooms have the correct room initial
Test that the calcIndex function works properly
Test that an Exception is thrown when appropriate
 */
