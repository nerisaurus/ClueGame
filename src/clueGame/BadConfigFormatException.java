package clueGame;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


public class BadConfigFormatException extends Exception {
	private String logFileName = "logfile.txt";
	
	public BadConfigFormatException() {
		super();
		writeExceptionLog(null);
	}
	public BadConfigFormatException(String message) {
		super(message);
		writeExceptionLog(message);
	}
	
	public String toString() {
		return "The configuration file is in an invalid format";
	}
	
	private void writeExceptionLog(String message) {
		try {
			PrintWriter writeLog = new PrintWriter(new FileWriter(logFileName, true));
			writeLog.print("[" + (new Date()).toString() + "]" +
					" ERROR: BadConfigFormatException\n" + super.getMessage() + "\n");
			writeLog.close();
		}catch (FileNotFoundException e) {
			System.out.println("Log file not found.");
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO Exception when writing to error log.");
			e.printStackTrace();
		}
	}

}
