package guiObjs;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamSelect implements Runnable{
	protected static JFrame frame;
	protected static JPanel panel;
	protected static ArrayList<JButton> players;
	
	public void TeamSelectWait() {
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1700, 950);
		panel.setBackground(new Color(153, 221, 255));
		
		
		
		for(int i = 0; i < Data.usernames.size(); i++) {
			JButton user = new JButton();
			JLabel name = new JLabel();
			name.setText(Data.username);
			name.setSize(300,20);
			name.setLocation(300, 300);
			user.setName(""+i);
			user.setText(Data.usernames.get(i));
			user.setSize(300, 30);
			user.setLocation(60, 90+(i*40));
			user.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent me) {
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
			panel.add(name);
		}
		

			JButton start = new JButton();
			start.setName("start");
			start.setText("Start GAme");
			start.setSize(500, 30);
			start.setLocation(0, 0);
			if(Data.Original) {
				start.setEnabled(true);
			}
			else
			{
				start.setEnabled(false);
				start.setText("Waiting to start... Hold Tight");
			}
			start.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent me) {
					Data.th.SendMessage("~");
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
		frame = new JFrame("My First GUI");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(panel);
		// frame.pack();
		frame.setVisible(true);
		frame.validate();
		
		
	}

	@Override
	public void run() {
		System.out.println("3");
		TeamSelectWait();
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
