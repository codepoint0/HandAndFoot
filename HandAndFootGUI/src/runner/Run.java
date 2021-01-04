package runner;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import guiObjs.Data;
import guiObjs.Lobby;
import guiObjs.TeamSelect;
import guiObjs.Thread0;
import guiObjs.Welcome;
import guiObjs.Window1700;

/***
 * This launches all the needed threads to be able to run the game
 * The entry point of the program 
 * @author Tyler K. Gordon and Joshua Speckman
 *
 */
public class Run {

	public static void main(String[] args) {
		
		// Init the data
		Data.InitData();
		
		// Create and set up the logger
	    Logger logger = Logger.getLogger("MyLog");  
	    logger.setLevel(Level.WARNING);
	    FileHandler fh;  
	    
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy~HH-mm-ss");

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler(new File("Logs/" + LocalDateTime.now().format(myFormatObj) + ".txt").getAbsolutePath());  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  


	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    Data.logger = logger;
	    Data.logger.finest("Creating All Windows");
	    
	    // Create the windows
		Welcome w = new Welcome();
		Lobby l = new Lobby();
		TeamSelect ts = new TeamSelect();
		Window1700 wd = new Window1700();
		
		Data.logger.finest("Telling Data about windows created");
		
		// Lets the data class know about the windows
		Data.gameWindow = wd;
		Data.welcome = w;
		Data.l = l;
		Data.ts = ts;
		
		Data.logger.finest("Starting while loop to ensure thread does not die...");
		
		// Keep the thread alive. PRINT STATEMENT NEEDED FOR CONSTANT Synchronization
		while(!Data.connected) {
			System.out.println("");
		}
		
		Data.logger.finer("Creating Thread0");
		Thread0 th = new Thread0(Data.client); 
		Data.coms = th;
		Thread t1 = new Thread(l);
		
		Data.logger.info("Starting Lobby Window Thread");
		
		// Send and get the Code
		th.Code();
		
		t1.run();

		Data.logger.finer("Creating Thread0 Thread");
		Thread t2 = new Thread(th);
		
		Data.logger.finer("Starting Thread0, here we go.");
		t2.run();
		
		
		System.out.println("2");
	}

}
