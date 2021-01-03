package guiObjs;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Lobby implements Runnable{
	protected static JFrame frame;
	protected static JPanel panel;
	
	public void LobbyWait() {
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1700, 950);
		panel.setBackground(new Color(153, 221, 255));
		
		if(Data.client.isConnected()) {
			Data.logger.finest("Telling user we are waiting...");
			JLabel waiting = new JLabel();
			waiting.setText("Waiting...");
			waiting.setSize(300,20);
			waiting.setLocation(0, 0);
			panel.add(waiting);
			JLabel code = new JLabel();
			code.setText(""+Data.code);
			code.setSize(300,20);
			code.setLocation(0, 30);
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

	@Override
	public void run() {
		Data.logger.finest("Starting LobbyWait Process... waiting for more players");
		LobbyWait();
	}
	
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
