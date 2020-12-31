package guiObjs;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Welcome {
	
	protected static JFrame frame;
	protected static JPanel panel;
	protected static JButton join;
	protected static JButton create;
	protected static JTextField ip;
	protected static JTextField port;
	protected static JTextField players;
	protected static JTextField username;
	protected static JButton connect;
	protected static JTextField resolution;
	boolean Connect;
	
	public Welcome() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1280, 720);
		panel.setBackground(new Color(153, 221, 255));
		
		players = new JTextField();
		players.setText("");
		
		JLabel waiting = new JLabel();
		waiting.setText("Hand And Foot");
		waiting.setSize(130,10);
		waiting.setLocation(570, 0);
		panel.add(waiting);
		
		join = new JButton();
		join.setText("Join Game");
		join.setSize(130,30);
		join.setLocation(570,30);
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
				create.setLocation(570, 190);
				ip = new JTextField();
				ip.setText("35.155.50.243");
				ip.setSize(130, 30);
				ip.setLocation(570, 70);
				ip.addMouseListener(new MouseListener() {
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
						ip.setText("");
					}
				});
				panel.add(ip);
				port = new JTextField();
				port.setText("8080");
				port.setSize(130, 30);
				port.setLocation(570, 110);
				port.addMouseListener(new MouseListener() {
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
						port.setText("");
					}
				});
				panel.add(port);

				username = new JTextField();
				username.setText("username");
				username.setSize(130, 30);
				username.setLocation(570, 150);
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
		panel.add(join);
		create = new JButton();
		create.setText("Create Game");
		create.setSize(130,30);
		create.setLocation(570,70);
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
				ip = new JTextField();
				ip.setText("35.155.50.243");
				ip.setSize(130, 30);
				ip.setLocation(570, 110);
				ip.addMouseListener(new MouseListener() {
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
						ip.setText("");
					}
				});
				panel.add(ip);
				port = new JTextField();
				port.setText("8080");
				port.setSize(130, 30);
				port.setLocation(570, 150);
				port.addMouseListener(new MouseListener() {
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
						port.setText("");
					}
				});
				panel.add(port);
				players = new JTextField();
				players.setText("2");
				players.setSize(130, 30);
				players.setLocation(570, 190);
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
				username.setLocation(570, 230);
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
		connect.setLocation(500,300);
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
				if(ip.getText() != null && port.getText() != null) {
					System.out.println("HERE!");
					Connect = true;
					Jumpship();
					
				}
			}
		});
		panel.add(connect);
		
		
		resolution = new JTextField();
		resolution.setText("1020");
		resolution.setSize(130, 30);
		resolution.setLocation(570, 370);
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
		frame = new JFrame("My First GUI");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 720);
		frame.add(panel);
		// frame.pack();
		frame.setVisible(true);
		frame.validate();
	}
	
	public void Jumpship() {
		
		frame.setVisible(false);
		Data.ip = ip.getText();
		Data.port = port.getText();
		Data.Resolution = Integer.parseInt(resolution.getText());
		if(players.getText() != null) {
			Data.playerNum = players.getText();
		}
		else {
			Data.playerNum = "-1";
		}
		Data.username = username.getText();
		String serverName = ip.getText();
		int port = Integer.parseInt(this.port.getText());
		System.out.print("Connecting...");
		try {
			Socket client = new Socket(serverName, port);
			Data.client = client;

		} catch (IOException e) {
			System.out.println("uhh");
			e.printStackTrace();
		}
		System.out.println("uhh");
		
		Data.connected = true;
	}
	

}
