package clueGame;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class BadConfigFormatException extends RuntimeException {
	private String logFileName = "logfile.txt";
	
	public BadConfigFormatException() {
		super();
		logExceptions(null);
	}

	public BadConfigFormatException(String message) {
		super(message);
		logExceptions(message);
	}
	
	private void logExceptions(String message) {		
		try{
			PrintWriter writeLog = new PrintWriter(new FileWriter(logFileName, true));
			writeLog.print("[" + (new Date()).toString() + "]" +
					" ERROR: BadConfigFormatException\n" + super.getMessage() + "\n");
			writeLog.close();
		}catch(FileNotFoundException e){
			System.out.println("Log file not found.");
		} catch (IOException e) {
			System.out.println("IO Exception when writing to file.");
			e.printStackTrace();
		}
	}
}
