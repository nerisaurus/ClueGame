package Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class TestBoardSetup {
		private static Board board;
		public static final int ROOMS = 11;
		public static final int ROWS = 21;
		public static final int COLS = 22;

		@BeforeClass
		public static void setUp() {
			board = new Board("clueLayoutCSV.csv", "clueLegendTXT.txt");
			board.loadConfigFiles();
		}
		@Test
		public void testRooms() {
			Map<Character, String> rooms = board.getRooms();
			assertEquals(ROOMS, rooms.size());
			assertEquals("Living Room", rooms.get('T'));
			assertEquals("Porch", rooms.get('P'));
			assertEquals("Attic", rooms.get('A'));
			assertEquals("Den", rooms.get('N'));
			assertEquals("Kitchen", rooms.get('K'));
		}

		@Test
		public void testBoardDimensions() {
			assertEquals(ROWS, board.getHeight());
			assertEquals(COLS, board.getWidth());		
		}

		@Test
		public void TestDoorDirections() {
			RoomCell room = board.getRoomCellAt(7, 18);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
			room = board.getRoomCellAt(17, 2);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
			room = board.getRoomCellAt(4, 3);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
			room = board.getRoomCellAt(2, 14);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
			room = board.getRoomCellAt(3, 20);
			assertFalse(room.isDoorway());	
			BoardCell cell = board.getCellAt(board.calcIndex(13, 13));
			assertFalse(cell.isDoorway());		
		}

		@Test
		public void testNumberOfDoorways() {
			int numDoors = 0;
			int totalCells = board.getWidth() * board.getHeight();
			Assert.assertEquals(462, totalCells);
			for (int i=0; i<totalCells; i++) {
				BoardCell cell = board.getCellAt(i);
				if (cell.isDoorway())
					numDoors++;
			}
			Assert.assertEquals(12, numDoors);
		}

		@Test
		public void testCalcIndex() {
			assertEquals(0, board.calcIndex(0, 0));
			assertEquals(COLS-1, board.calcIndex(0, COLS-1));
			assertEquals(440, board.calcIndex(ROWS-1, 0));
			assertEquals(461, board.calcIndex(ROWS-1, COLS-1));
			assertEquals(98, board.calcIndex(4, 10));
			assertEquals(253, board.calcIndex(11, 11));		
		}

		@Test
		public void testRoomInitials() {
			assertEquals('N', board.getRoomCellAt(0, 0).getInitial());
			assertEquals('K', board.getRoomCellAt(0, 9).getInitial());
			assertEquals('B', board.getRoomCellAt(20, 9).getInitial());
			assertEquals('T', board.getRoomCellAt(10, 1).getInitial());
			assertEquals('A', board.getRoomCellAt(10, 15).getInitial());
		}

		@Test (expected = BadConfigFormatException.class)
		public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
			Board b = new Board("clueLayoutBadColumnsCSV.csv", "clueLegendTXT.txt");
			b.loadLegend();
			b.loadBoard();
		}

		@Test (expected = BadConfigFormatException.class)
		public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
			Board b = new Board("clueLayoutBadRoomCSV.csv", "clueLegendTXT.txt");
			b.loadLegend();
			b.loadBoard();
		}

		@Test (expected = BadConfigFormatException.class)
		public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
			Board b = new Board("clueLayoutCSV.csv", "ClueLegendBadFormatTXT.txt");
			b.loadLegend();
			b.loadBoard();
		}

}
