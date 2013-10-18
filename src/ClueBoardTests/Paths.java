package ClueBoardTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class Paths {
	private static Board board;
	public static final int NUM_ROOMS = 11; //9 "Rooms" + 1 "Walkway" + 1 "Closet" = 11 Entries in the rooms map
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLS = 25;
	
	//Correct Config File Names
	public static final String BOARD_FILE_STR = "board.csv";
	public static final String LEGEND_FILE_STR = "legend.csv";

	@Before
	public void setUp() {
		board = new Board(BOARD_FILE_STR, LEGEND_FILE_STR);
		board.loadConfigFiles();
		board.calculateAdjacencies();
	}	
	
	//Adjacency tests for (note that point value = minimum number of tests):
	//(1) Locations with only walkways as adjacent locations	
	@Test
	public void locationWithOnlyWalkwaysAsAdjacentLocations(){
		LinkedList testList = board.getAdjList(401);
		Assert.assertTrue(testList.contains(400));
		Assert.assertTrue(testList.contains(376));
		Assert.assertTrue(testList.contains(402));
		Assert.assertTrue(testList.contains(426));
		Assert.assertEquals(4, testList.size());
	}
	
	//(4) Locations that are at each edge of the board	
	@Test
	public void locationOnLeftEdge(){
		LinkedList testList = board.getAdjList(375);
		Assert.assertTrue(testList.contains(376));
		Assert.assertTrue(testList.contains(400));
		Assert.assertEquals(2, testList.size());
	}	
	@Test
	public void locationOnRightEdge(){
		LinkedList testList = board.getAdjList(424);
		Assert.assertTrue(testList.contains(423));
		Assert.assertEquals(1, testList.size());
	}	
	@Test
	public void locationOnTopEdge(){
		LinkedList testList = board.getAdjList(13);
		Assert.assertTrue(testList.contains(12));
		Assert.assertTrue(testList.contains(38));
		Assert.assertEquals(2, testList.size());
	}	
	@Test
	public void locationOnBottomEdge(){
		LinkedList testList = board.getAdjList(613);
		Assert.assertTrue(testList.contains(612));
		Assert.assertTrue(testList.contains(588));
		Assert.assertEquals(2, testList.size());
	}
	
	//(2) Locations that are beside a room cell that is not a doorway	
	@Test
	public void locationBesideARoomNotADoorway_a(){
		LinkedList testList = board.getAdjList(233);
		Assert.assertTrue(testList.contains(232));
		Assert.assertTrue(testList.contains(208));
		Assert.assertEquals(2, testList.size());
	}	
	@Test
	public void locationBesideARoomNotADoorway_b(){
		LinkedList testList = board.getAdjList(420);
		Assert.assertTrue(testList.contains(419));
		Assert.assertTrue(testList.contains(421));
		Assert.assertEquals(2, testList.size());
	}
	
	//(2) Locations that are adjacent to a doorway with needed direction (i.e., the adjacency list will include the doorway)
	@Test
	public void locationBesideDoorway_a(){
		LinkedList testList = board.getAdjList(456);
		Assert.assertTrue(testList.contains(455));
		Assert.assertTrue(testList.contains(431));
		Assert.assertTrue(testList.contains(457));
		Assert.assertEquals(3, testList.size());
	}	
	@Test
	public void locationBesideDoorway_b(){
		LinkedList testList = board.getAdjList(170);
		Assert.assertTrue(testList.contains(171));
		Assert.assertTrue(testList.contains(169));
		Assert.assertTrue(testList.contains(145));
		Assert.assertTrue(testList.contains(195));
		Assert.assertEquals(4, testList.size());
	}
	
	//(2) Locations that are doorways	
	@Test
	public void locationThatIsADoorway_a(){
		LinkedList testList = board.getAdjList(128);
		Assert.assertTrue(testList.contains(153));
		Assert.assertEquals(1, testList.size());
	}	
	@Test
	public void locationThatIsADoorway_b(){
		LinkedList testList = board.getAdjList(274);
		Assert.assertTrue(testList.contains(249));
		Assert.assertEquals(1, testList.size());
	}
	
	//Target tests for (note that point value = minimum number of tests):
	//(4) Targets along walkways, at various distances
	@Test
	public void test_Targets_483_2(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(8, 19, 2);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(433));
		Assert.assertTrue(targets.contains(459));
		Assert.assertTrue(targets.contains(457));
		Assert.assertTrue(targets.contains(507));
		Assert.assertTrue(targets.contains(533));
		Assert.assertTrue(targets.contains(509));
		Assert.assertTrue(targets.contains(485));
		Assert.assertEquals(7, targets.size());
	}

	@Test
	public void test_Targets_391_2(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(16, 15, 2);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(341));
		Assert.assertTrue(targets.contains(365));
		Assert.assertTrue(targets.contains(367));
		Assert.assertTrue(targets.contains(389));
		Assert.assertTrue(targets.contains(415));
		Assert.assertTrue(targets.contains(441));
		Assert.assertTrue(targets.contains(417));
		Assert.assertTrue(targets.contains(393));
		Assert.assertEquals(8, targets.size());
	}

	@Test
	public void test_Targets_167_4(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(17, 6, 4);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(163));
		Assert.assertTrue(targets.contains(189));
		Assert.assertTrue(targets.contains(215));
		Assert.assertTrue(targets.contains(241));
		Assert.assertTrue(targets.contains(171));
		Assert.assertTrue(targets.contains(195));
		Assert.assertTrue(targets.contains(219));
		Assert.assertTrue(targets.contains(243));
		Assert.assertTrue(targets.contains(145));
		Assert.assertTrue(targets.contains(193));
		Assert.assertTrue(targets.contains(191));
		Assert.assertTrue(targets.contains(267));
		Assert.assertTrue(targets.contains(139));
		Assert.assertTrue(targets.contains(141));
		Assert.assertTrue(targets.contains(169));
		Assert.assertTrue(targets.contains(165));
		Assert.assertEquals(16, targets.size());
	}

	@Test
	public void test_Targets_181_2(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(6, 7, 2);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(155));
		Assert.assertTrue(targets.contains(157));
		Assert.assertTrue(targets.contains(183));
		Assert.assertTrue(targets.contains(207));
		Assert.assertTrue(targets.contains(179));
		Assert.assertTrue(targets.contains(205));
		Assert.assertTrue(targets.contains(231));
		Assert.assertTrue(targets.contains(131));
		Assert.assertEquals(8, targets.size());
	}
	
	//(2) Targets that allow the user to enter a room
	@Test
	public void test_Targets_306_2(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(6, 12, 2);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(304));
		Assert.assertTrue(targets.contains(280));
		Assert.assertTrue(targets.contains(256));
		Assert.assertTrue(targets.contains(282));
		Assert.assertTrue(targets.contains(332));
		Assert.assertTrue(targets.contains(256));
		Assert.assertTrue(targets.contains(330));
		Assert.assertEquals(7, targets.size());
	}

	@Test
	public void test_Targets_163_3(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(13, 6, 3);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(160));
		Assert.assertTrue(targets.contains(136));
		Assert.assertTrue(targets.contains(186));
		Assert.assertTrue(targets.contains(188));
		Assert.assertTrue(targets.contains(166));
		Assert.assertTrue(targets.contains(140));
		Assert.assertTrue(targets.contains(190));
		Assert.assertTrue(targets.contains(214));
		Assert.assertTrue(targets.contains(114));
		Assert.assertTrue(targets.contains(138));
		Assert.assertTrue(targets.contains(88));
		Assert.assertEquals(11, targets.size());
	}
	
	//(2) Targets calculated when leaving a room
	@Test
	public void test_Targets_560_1(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(10, 22, 1);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(563));
		Assert.assertEquals(1, targets.size());
	}

	@Test
	public void test_Targets_85_1(){ // Targetz_x_y means testing startTargetz(x.x, x.y, y);
		board.startTargets(10, 3, 1);
		Set targets= board.getTargets();
		Assert.assertTrue(targets.contains(81));
		Assert.assertTrue(targets.contains(106));
		Assert.assertTrue(targets.contains(161));
		Assert.assertTrue(targets.contains(88));
		Assert.assertTrue(targets.contains(11));
		Assert.assertEquals(5, targets.size());
	}

}
