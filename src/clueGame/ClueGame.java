package clueGame;

public class ClueGame {
	private Board board;

	public ClueGame() {
		// TODO Auto-generated constructor stub
	}
	
	public ClueGame(String legend, String board, String players, String people, String weapons, String rooms) {
		//board loads its own config files
		this.board = new Board(board, legend);
		
		//load players, and cards here
		loadConfigFiles();
	}
	
	public void loadConfigFiles() {
		// TODO Auto-generated method stub
		
	}
}
