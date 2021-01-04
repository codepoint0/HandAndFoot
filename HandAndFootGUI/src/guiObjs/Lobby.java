package guiObjs;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/***
 * The lobby window which displays right after the
 * user connects or fails to connect. This window
 * will display the Room Code.
 * 
 * @author Tyler K. Gordon and Joshua Speckman
 *
 */
public class Lobby implements Runnable{
	protected static JFrame frame;
	protected static JPanel panel;
	
	/***
	 * Displays the window with the
	 * given information and waits
	 * to go further.
	 */
	public void LobbyWait() {
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1700, 950);
		panel.setBackground(new Color(153, 221, 255));
		
		// If the player is connected show the room code
		// If not display the error.
		if(Data.client.isConnected()) {
			Data.logger.finest("Telling user we are waiting...");
			JLabel waiting = new JLabel("Waiting...", SwingConstants.CENTER);
			waiting.setFont(new Font("Serif", Font.PLAIN, 25));
			waiting.setText("Waiting...");
			waiting.setSize(350,30);
			waiting.setLocation(0, 0);
			panel.add(waiting);
			
			JLabel code = new JLabel("Room Code: " + Data.code, SwingConstants.CENTER);
			code.setFont(new Font("Serif", Font.PLAIN, 25));
			code.setText("Room Code: " + Data.code);
			code.setSize(350,30);
			code.setLocation(0, 50);
			panel.add(code);
		}
		else {
			Data.logger.info("Telling user we cannot connect...");
			JLabel waiting = new JLabel();
			waiting.setText("Failed to establish a connection!");
			waiting.setSize(300,300);
			waiting.setLocation(0, 0);
			panel.add(waiting);
		}

		
		
		// frame Code
		frame = new JFrame("Waiting...");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 350);
		frame.add(panel);
		// frame.pack();
		frame.setVisible(true);
		frame.validate();
		
		
	}
	
	/***
	 * This is a thread so runs the wait in a new thread and
	 * waits for further commands.
	 */
	@Override
	public void run() {
		Data.logger.finest("Starting LobbyWait Process... waiting for more players");
		LobbyWait();
	}
	
	
	/***
	 * Launches the next phase, moving all players into the team select.
	 */
	public void start() {
		Data.logger.fine("Moving from Lobby to TeamSelect");
		TeamSelect ts = new TeamSelect();
		Data.ts = ts;
		Data.logger.finer("Starting TeamSelect Thread");
		Thread t1 = new Thread(ts);
		frame.setVisible(false);
		t1.run();
	}

}
