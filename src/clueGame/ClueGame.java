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
		LinkedList<Player> emptyHumanList = new LinkedList<Player>();
		this.players.put("Human", emptyHumanList);
		LinkedList<Player> emptyComputerList = new LinkedList<Player>();
		this.players.put("Computer", emptyComputerList);
		this.playersFile = players;
		
		//load players, and cards here
		//loadConfigFiles();
	}
	
	public void loadConfigFiles() {
		// TODO Auto-generated method stub
		try {
			loadPlayers();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadPlayers() throws FileNotFoundException {
		FileReader file = new FileReader(playersFile);
		Scanner reader = new Scanner(file);
		int row = 0;
		while(reader.hasNextLine()) {
			String[] line = reader.nextLine().split(",");
			String name = line[0];
			String color = line[1];
			int startingRowPosition = Integer.parseInt(line[2]);
			int startingColumnPosition = Integer.parseInt(line[3]);
			
			Player player = new Player(name, color, startingRowPosition, startingColumnPosition);
			
			if(row == 0){
				players.get("Human").add(player);
				System.out.println("adding human");
			}
			else{
				players.get("Computer").add(player);
				System.out.println("adding computer");
			}
			row++;
		}
		reader.close();
	}
	
	// Used for testing purposes only.
	public Map<String, LinkedList<Player>> getPlayers() {
		return players;
	}
}
