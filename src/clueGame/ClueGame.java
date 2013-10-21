package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class ClueGame {
	private Board board;
	private Map<String, LinkedList<Player>> players;
	private String playersFile;

	public ClueGame() {
		this.players = new HashMap<String, LinkedList<Player>>();
	}
	
	public ClueGame(String legend, String board, String players, String people, String weapons, String rooms) {
		//board loads its own config files
		this.board = new Board(board, legend);
		this.players = new HashMap<String, LinkedList<Player>>();
		LinkedList<Player> emptyPlayerList = new LinkedList<Player>();
		this.players.put("Human", emptyPlayerList);
		this.players.put("Computer", emptyPlayerList);
		this.playersFile = players;
		
		//load players, and cards here
		loadConfigFiles();
	}
	
	public void loadConfigFiles() {
		// TODO Auto-generated method stub
		
	}
	
	// Used for testing purposes only.
	public Map<String, LinkedList<Player>> getPlayers() {
		return players;
	}
}
