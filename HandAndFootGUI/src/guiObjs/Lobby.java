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
		
		JLabel waiting = new JLabel();
		waiting.setText("Waiting...");
		waiting.setSize(300,300);
		waiting.setLocation(0, 0);
		panel.add(waiting);
		
		
		// frame Code
		frame = new JFrame("My First GUI");
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
		System.out.println("3");
		LobbyWait();
		System.out.println("4");
		
	}
	
	public void start() {
		Window w = null;
		if(Data.Resolution == 1700) {
			w = new Window1700();
		}
		else if(Data.Resolution == 1020) {
			w = new Window1020();
		}
		
		Data.w = w;
		Thread t1 = new Thread(w);
		t1.run();
	}

}
