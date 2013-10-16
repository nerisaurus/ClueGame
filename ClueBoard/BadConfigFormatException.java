package ClueBoard;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class BadConfigFormatException extends Exception {
	private String msg;
	
	public BadConfigFormatException(String message) {
		super();
		writeExceptionLog(message);
		msg = message;
	}
	
	public String getMessage() {
		return msg;
	}
	
	public String toString() {
		return "The configuration file is in an invalid format";
	}
	
	public void writeExceptionLog(String msag) {
		try {
			PrintWriter writter = new PrintWriter("log.txt");
			writter.println(msag);
			writter.close();
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
