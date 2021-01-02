package runner;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
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

public class Run {

	public static void main(String[] args) {
		Data.InitData();
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
		Welcome w = new Welcome();
		Lobby l = new Lobby();
		TeamSelect ts = new TeamSelect();
		Window1700 wd = new Window1700();
		
		Data.logger.finest("Telling Data about windows created");
		Data.w = wd;
		Data.s = w;
		Data.l = l;
		Data.ts = ts;
		
		int i = 0;
		
		Data.logger.finest("Starting while loop to ensure thread does not die...");
		while(!Data.connected) {
			System.out.println("");
		}
		
		Data.logger.finer("Creating Thread0");
		Thread0 th = new Thread0(Data.client);
		Data.th = th;
		Thread t1 = new Thread(l);
		
		Data.logger.info("Starting Lobby Window Thread");
		t1.run();

		Data.logger.finer("Creating Thread0 Thread");
		Thread t2 = new Thread(th);
		
		Data.logger.finest("Player must have selected create game because Data.playerNum is " + Data.playerNum);
		Data.th.SendMessage("" + Data.playerNum);

		Data.logger.finer("Starting Thread0, here we go.");
		t2.run();
		
		
		System.out.println("2");
	}

}
