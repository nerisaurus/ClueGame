package Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;


public class testsForAdjAndTargets {

	private static Board board;
	@BeforeClass
	public static void setUp() throws IOException, BadConfigFormatException {
		board = new Board("clueLayoutCSV.csv", "clueLegendTXT.txt");
		board.loadConfigFiles();
		board.calculateAdjacencies();
	}
	@Test
	public void testAdjacencies227(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(10,17));
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(11, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 18)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 16)));
	}
	
	@Test
	public void testAdjacencies126(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(6,0));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(5, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 1)));
	}
	
	@Test
	public void testAdjacencies3(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(0,3));
		Assert.assertEquals(0, testList.size());
	}
	@Test
	public void testAdjacencies252(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(11,21));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(10, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 20)));
	}
	@Test
	public void testAdjacencies431(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(20, 11));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(20,12)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 11)));
	}
	@Test
	public void testAdjacencies49(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(2,7));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(3, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 7)));
	}
	@Test
	public void testAdjacencies374(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(17,17));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(16,17)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 17)));
	}	
	@Test
	public void testAdjacencies237(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(11,6));
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(12, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(10,6)));
		Assert.assertTrue(testList.contains(board.calcIndex(11,7)));
		Assert.assertTrue(testList.contains(board.calcIndex(11,5)));
	}
	@Test
	public void testAdjacencies182(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(13,13));
		Assert.assertEquals(3, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(13,12)));
		Assert.assertTrue(testList.contains(board.calcIndex(12,13)));
		Assert.assertTrue(testList.contains(board.calcIndex(14,13)));
	}
	@Test
	public void testAdjacencies322(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(15,7));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(15,6)));
		Assert.assertTrue(testList.contains(board.calcIndex(14,7)));
	}
	
	@Test
	public void testAdjacencies56(){
		LinkedList<Integer> testList = board.getAdj(board.calcIndex(2,14));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(3,14)));
		Assert.assertTrue(testList.contains(board.calcIndex(2,15)));
	}
	@Test
	public void testTargets362_2Steps(){
		board.startTargets(17,5, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16,4))));
	}
	@Test
	public void testTargets15_3Steps(){
		board.startTargets(0,16, 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0,15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2,15))));
	}
	@Test
	public void testTargets181_4Steps(){
		board.startTargets(8,13, 4);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(15, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11,12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4,13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10,13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6,13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5,12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5,14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9,12))));
	}
	@Test
	public void testTargets437_5Steps(){
		board.startTargets(20,17, 5);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16,16))));
	}
	@Test
	public void testTargets249_enterRoom(){
		board.startTargets(11,18, 4);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13,20))));
	}
	@Test
	public void testTargets152_enterRoom(){
		board.startTargets(7,5, 5);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4,3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10,5))));
	}
	@Test
	public void testTargetsLeaveRoom287_2Steps(){
		board.startTargets(8,13, 2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10,13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6,13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9,12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7,12))));
	}
	@Test
	public void testTargetsLeaveRoom431_1Steps(){
		board.startTargets(19,13, 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19,12))));
	}
	
}
