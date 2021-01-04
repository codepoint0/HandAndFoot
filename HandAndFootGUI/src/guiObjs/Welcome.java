package guiObjs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Welcome {
	
	protected static JFrame frame;
	protected static JPanel panel;
	protected static JButton join;
	protected static JButton create;
	protected static JTextField players;
	protected static JTextField username;
	protected static JButton connect;
	protected static JTextField resolution;
	protected static JTextField code;
	protected static int Clicked = 0;
	boolean Connect;
	
	
	public Welcome() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1280, 720);
		panel.setBackground(new Color(153, 221, 255));
		
		players = new JTextField();
		players.setText("");
		
		JLabel waiting = new JLabel("Hand And Foot", SwingConstants.CENTER);
		waiting.setText("Hand And Foot");
		waiting.setFont(new Font("Serif", Font.BOLD, 50));
		waiting.setSize(1280,90);
		waiting.setLocation(0, 0);
		panel.add(waiting);
		
		join = new JButton();
		join.setText("Join Game");
		join.setSize(130,30);
		join.setLocation(570,120);
		join.addMouseListener(new MouseListener() {
			
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseReleased(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {
				Clicked = 1;
				create.setLocation(570, 240);

				username = new JTextField();
				username.setText("username");
				username.setSize(130, 30);
				username.setLocation(570, 160);
				username.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseReleased(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseEntered(MouseEvent me) {
					}

					public void mouseExited(MouseEvent me) {
					}

					public void mouseClicked(MouseEvent me) {
						username.setText("");
					}
				});
				panel.add(username);
				
				code = new JTextField();
				code.setText("CODE");
				code.setSize(130, 30);
				code.setLocation(570, 200);
				code.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseReleased(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseEntered(MouseEvent me) {
					}

					public void mouseExited(MouseEvent me) {
					}

					public void mouseClicked(MouseEvent me) {
						code.setText("");
					}
				});
				panel.add(code);
				
			}
		});
		panel.add(join);
		create = new JButton();
		create.setText("Create Game");
		create.setSize(130,30);
		create.setLocation(570,160);
		create.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseReleased(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {
				players = new JTextField();
				players.setText("4");
				players.setSize(130, 30);
				players.setLocation(570, 200);
				players.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseReleased(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseEntered(MouseEvent me) {
					}

					public void mouseExited(MouseEvent me) {
					}

					public void mouseClicked(MouseEvent me) {
						players.setText("");
					}
				});
				panel.add(players);
				username = new JTextField();
				username.setText("username");
				username.setSize(130, 30);
				username.setLocation(570, 240);
				username.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseReleased(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {}
					}

					public void mouseEntered(MouseEvent me) {
					}

					public void mouseExited(MouseEvent me) {
					}

					public void mouseClicked(MouseEvent me) {
						username.setText("");
					}
				});
				panel.add(username);
			}
		});
		panel.add(create);
		
		connect = new JButton();
		connect.setText("Connect");
		connect.setSize(300,60);
		connect.setLocation(500,400);
		connect.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseReleased(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {

					Connect = true;
					Jumpship();
			}
		});
		panel.add(connect);
		
		
		resolution = new JTextField();
		resolution.setText("1020");
		resolution.setSize(130, 30);
		resolution.setLocation(570, 470);
		resolution.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseReleased(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {}
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {
				resolution.setText("");
			}
		});
		panel.add(resolution);
		
		
		
		// frame Code
		frame = new JFrame("Hand And Foot");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 720);
		frame.add(panel);
		// frame.pack();
		frame.setVisible(true);
		frame.validate();
	}
	
	public void Jumpship() {
		Data.logger.info("Moving from Welcome Window to Lobby");
		frame.setVisible(false);
		Data.ip = "52.13.12.123";
		Data.port = "8080";
		Data.Resolution = Integer.parseInt(resolution.getText());
		Data.logger.finer("Connecting to ip " + Data.ip + " on port " + Data.port);
		if(Clicked == 0) {
			Data.playerNum = players.getText();
		}
		else {
			Data.playerNum = code.getText();
		}
		Data.username = username.getText();
		System.out.println(Data.username);
		String serverName = "52.13.12.123";
		int port = 8080;
		try {
			Data.logger.info("Establishing connection");
			Socket client = new Socket(serverName, port);
			Data.client = client;
			

		} catch (IOException e) {
			Data.logger.severe("Failed to establish connection! Check the internet (and Google).");
			e.printStackTrace();
		}
		
		Data.connected = true;
	}
}
