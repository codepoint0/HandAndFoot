package guiObjs;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/***
 * A screen that shows buttons to select the user's team.
 * 
 * @author Tyler K. Gordon and Joshua Speckman
 *
 */
public class TeamSelect implements Runnable {
	protected static JFrame frame;
	protected static JPanel panel;
	protected static ArrayList<JButton> players;

	/***
	 * The window will display the given buttons and wait for further instructions
	 * from the Server
	 */
	public void TeamSelectWait() {

		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1700, 950);
		panel.setBackground(new Color(153, 221, 255));

		// Creates buttons for each of the users
		for (int i = 0; i < Data.usernames.size(); i++) {
			JButton user = new JButton();

			// The button is set to the user's ID
			// so that we can determine who the user
			// clicked
			user.setName("" + i);
			user.setText(Data.usernames.get(i));
			user.setSize(300, 30);
			user.setLocation(60, 90 + (i * 40));
			user.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent me) {
					Data.logger.finest("User selected " + user.getName());

					// Sets the user's choice to the button's 'name'
					Data.selectedTeam = Integer.parseInt(user.getName());
				}

				public void mouseReleased(MouseEvent me) {
				}

				public void mouseEntered(MouseEvent me) {
				}

				public void mouseExited(MouseEvent me) {
				}

				public void mouseClicked(MouseEvent me) {
				}
			});
			panel.add(user);
		}

		JButton start = new JButton();
		start.setName("start");
		start.setText("Start Game");
		start.setSize(500, 30);
		start.setLocation(0, 0);
		
		// If the player is the one that created the game
		// then set the start button enabled.
		// If not then tell the user that the game is waiting
		if (Data.Original) {
			start.setEnabled(true);
		} else {
			start.setEnabled(false);
			start.setText("Waiting to start... Hold Tight");
		}
		start.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				Data.logger.fine("User who created the game has started the game.");
				Data.coms.SendMessage("~");
			}

			public void mouseReleased(MouseEvent me) {
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {
			}
		});
		panel.add(start);

		// frame Code
		frame = new JFrame("Team selection");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
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
		Data.logger.finer("Waiting for players to select their teams and Creator to start game");
		TeamSelectWait();
	}

	/***
	 * Launches the next phase (if the original player clicks start game), moving all players into the
	 * game phase 
	 */
	public void start() {
		Data.logger.info("Game is starting");
		Window w = null;
		
		// Set the resolution to the correct window
		if (Data.Resolution == 1700) {
			Data.logger.finest("Resolution has been set to 1700");
			w = new Window1700();
		} else if (Data.Resolution == 1020) {
			Data.logger.finest("Resolution has been set to 1020");
			w = new Window1020();
		}
		frame.setVisible(false);
		Data.gameWindow = w;
		Data.logger.info("Starting game window thread now!");
		Thread t1 = new Thread(w);
		t1.run();

	}

}
