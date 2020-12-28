package guiObjs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Window1700 extends Window implements Runnable {

	CardOrganizer c = new CardOrganizer();

	BufferedImage image;

	// FRAMES
	protected static JFrame frame;
	protected static JPanel panel;
	float FrameSizeX;
	float FrameSizeY;
	JLabel cardPanel;
	JLabel groupPanel;
	JLabel discardBorder;
	JLabel discardPile;
	JLabel foot;
	JLabel lightGreen;
	JLabel darkGreen;
	JLabel yellow;
	JLabel orange;
	JLabel lightRed;
	JLabel darkRed;
	JLabel ScoreBoard;

	// BUTTONS
	JButton play;
	JButton discard;
	JButton pile;
	JButton submit;
	JButton cancel;
	JButton draw;
	JButton back;

	JButton[] panelButtons = new JButton[12];
	JButton[] groupButtons = new JButton[10];
	ArrayList<JButton> playerButton = new ArrayList<JButton>();

	int[] ValidPlays = { 1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
	String[][] pictureLocations = new String[4][13];
	String[] suitLocations = { "res/images/" + Data.Resolution + "/Club.png",
			"res/images/" + Data.Resolution + "/Heart.png", "res/images/" + Data.Resolution + "/Diamond.png",
			"res/images/" + Data.Resolution + "/Spade.png" };

	ArrayList<JLabel> currentCards;
	ArrayList<JLabel> currentGroups;
	ArrayList<JLabel> currentPile;
	ArrayList<JLabel> currentPlayers;
	ArrayList<JLabel> currentScores;
	ArrayList<JLabel> cleanBooks;
	ArrayList<JLabel> dirtyBooks;

	@Override
	public void CreateWindow() {
		Create1700Window();
	}

	private void Create1700Window() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1700, 950);
		panel.setBackground(new Color(153, 221, 255));

		currentCards = new ArrayList<JLabel>();
		currentGroups = new ArrayList<JLabel>();
		currentPile = new ArrayList<JLabel>();
		currentPlayers = new ArrayList<JLabel>();
		currentScores = new ArrayList<JLabel>();
		cleanBooks = new ArrayList<JLabel>();
		dirtyBooks = new ArrayList<JLabel>();

		CreatePanelButtons();

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Wreath.png"));
			JLabel wreath = new JLabel(new ImageIcon(image));
			wreath.setSize(1000, 400);
			wreath.setLocation(350, 90);
			panel.add(wreath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}


		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/LightGreen.png"));
			lightGreen = new JLabel(new ImageIcon(image));
			lightGreen.setSize(95, 130);
			lightGreen.setLocation(797, 195);
			lightGreen.setVisible(false);
			panel.add(lightGreen);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DarkGreen.png"));
			darkGreen = new JLabel(new ImageIcon(image));
			darkGreen.setSize(95, 130);
			darkGreen.setLocation(797, 195);
			darkGreen.setVisible(false);
			panel.add(darkGreen);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Yellow.png"));
			yellow = new JLabel(new ImageIcon(image));
			yellow.setSize(95, 130);
			yellow.setLocation(797, 195);
			yellow.setVisible(false);
			panel.add(yellow);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Orange.png"));
			orange = new JLabel(new ImageIcon(image));
			orange.setSize(95, 130);
			orange.setLocation(797, 195);
			orange.setVisible(false);
			panel.add(orange);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/LightRed.png"));
			lightRed = new JLabel(new ImageIcon(image));
			lightRed.setSize(95, 130);
			lightRed.setLocation(797, 195);
			lightRed.setVisible(false);
			panel.add(lightRed);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DarkRed.png"));
			darkRed = new JLabel(new ImageIcon(image));
			darkRed.setSize(95, 130);
			darkRed.setLocation(797, 195);
			darkRed.setVisible(false);
			panel.add(darkRed);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/CardPanel.png"));
			cardPanel = new JLabel(new ImageIcon(image));
			cardPanel.setSize(1500, 210);
			cardPanel.setLocation(100, 690);
			panel.add(cardPanel);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/GroupPanel.png"));
			groupPanel = new JLabel(new ImageIcon(image));
			groupPanel.setSize(1150, 160);
			groupPanel.setLocation(275, 495);
			panel.add(groupPanel);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Discard.png"));
			discardBorder = new JLabel(new ImageIcon(image));
			discardBorder.setSize(1700, 900);
			discardBorder.setLocation(0, 0);
			discardBorder.setVisible(false);
			panel.add(discardBorder);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DiscardPile.png"));
			discardPile = new JLabel(new ImageIcon(image));
			discardPile.setSize(85, 120);
			discardPile.setLocation(802, 200);
			panel.add(discardPile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/FootIndicator.png"));
			foot = new JLabel(new ImageIcon(image));
			foot.setSize(85, 120);
			foot.setLocation(1525, 520);
			panel.add(foot);
			currentCards.add(foot);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for (int i = 1; i < Data.players.size(); i++) {
			if (Data.players.size() == 4) {
				try {
					JLabel playerImage;
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherPlayer.png"));
					playerImage = new JLabel(new ImageIcon(image));
					playerImage.setSize(70, 70);
					if (i == 1)
						playerImage.setLocation(30, 250);
					else if (i == 2)
						playerImage.setLocation(815, 5);
					else if (i == 3)
						playerImage.setLocation(1600, 250);
					panel.add(playerImage);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (Data.players.size() == 6) {
				try {
					JLabel playerImage;
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherPlayer.png"));
					playerImage = new JLabel(new ImageIcon(image));
					playerImage.setSize(70, 70);
					if (i == 1)
						playerImage.setLocation(30, 350);
					else if (i == 2)
						playerImage.setLocation(30, 150);
					else if (i == 3)
						playerImage.setLocation(815, 5);
					else if (i == 4)
						playerImage.setLocation(1600, 150);
					else if (i == 5)
						playerImage.setLocation(1600, 350);
					panel.add(playerImage);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (Data.players.size() == 8) {
				try {
					JLabel playerImage;
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherPlayer.png"));
					playerImage = new JLabel(new ImageIcon(image));
					playerImage.setSize(70, 70);
					if (i == 1)
						playerImage.setLocation(30, 350);
					else if (i == 2)
						playerImage.setLocation(30, 150);
					else if (i == 3)
						playerImage.setLocation(30, 5);
					else if (i == 4)
						playerImage.setLocation(815, 5);
					else if (i == 5)
						playerImage.setLocation(1600, 5);
					else if (i == 6)
						playerImage.setLocation(1600, 150);
					else if (i == 7)
						playerImage.setLocation(1600, 350);
					panel.add(playerImage);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		}
		
		for(int i = 0; i < 11; i++) {
			try {
				image = ImageIO.read(new File("res/images/" + Data.Resolution + "/CleanBook.png"));
				JLabel clean = new JLabel(new ImageIcon(image));
				clean.setSize(95, 130);
				clean.setLocation(5 + (105 * i), 20);
				clean.setVisible(false);
				cleanBooks.add(clean);
				groupPanel.add(clean);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			try {
				image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DirtyBook.png"));
				JLabel dirty = new JLabel(new ImageIcon(image));
				dirty.setSize(95, 130);
				dirty.setLocation(5 + (105 * i), 20);
				dirty.setVisible(false);
				dirtyBooks.add(dirty);
				groupPanel.add(dirty);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}

		for (int i = 0; i < 13; i++) {
			pictureLocations[0][i] = "res/images/" + Data.Resolution + "/Black" + (i + 1) + ".png";
			pictureLocations[3][i] = "res/images/" + Data.Resolution + "/Black" + (i + 1) + ".png";
			pictureLocations[1][i] = "res/images/" + Data.Resolution + "/Red" + (i + 1) + ".png";
			pictureLocations[2][i] = "res/images/" + Data.Resolution + "/Red" + (i + 1) + ".png";
		}

		ScoreBoard = new JLabel();
		ScoreBoard.setSize(300, 300);
		ScoreBoard.setLocation(1500, 0);
		panel.add(ScoreBoard);
		
		

		ServerUpdate();

		// frame Code
		frame = new JFrame("My First GUI");
		FrameSizeX = 1700;
		FrameSizeY = 950;
		frame.setSize(1700, 950);

		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(panel);
		// frame.pack();
		frame.setVisible(true);
		frame.validate();
	}

	/**
	 * Creates all buttons for the given panel
	 */
	private void CreatePanelButtons() {
		play = new JButton();
		play.setText("Play");
		play.setSize(100, 30);
		play.setLocation(480, 653);
		play.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (Data.phase == 2) {
					play.setEnabled(false);
					discard.setEnabled(false);
					play.setVisible(false);
					discard.setVisible(false);
					Data.phase = 3;
					submit.setEnabled(true);
					cancel.setEnabled(true);
					submit.setVisible(true);
					cancel.setVisible(true);
				}
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
		panel.add(play);

		draw = new JButton();
		draw.setText("Draw");
		draw.setSize(100, 30);
		draw.setLocation(790, 415);
		draw.setVisible(false);
		draw.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (Data.phase == 1) {
					draw.setEnabled(false);
					draw.setVisible(false);
					Data.th.SendMessage("D");
				}
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
		panel.add(draw);

		discard = new JButton();
		discard.setText("Discard");
		discard.setSize(100, 30);
		discard.setLocation(1120, 653);
		discard.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (Data.phase == 2) {
					play.setEnabled(true);
					discard.setEnabled(true);
					play.setVisible(true);
					discard.setVisible(true);
					submit.setEnabled(false);
					cancel.setEnabled(false);
					submit.setVisible(false);
					cancel.setVisible(false);
					discardBorder.setVisible(true);
					Data.IsQueued = false;
					Data.discardQueued = false;
					Data.th.SendMessage("M");
				} else if (Data.phase == 4 && Data.discardQueued) {
					Data.discardQueued = false;
					Data.QueuedIndex = -1;
					Data.Queued = new Card(0, 0, 0);
					Data.IsQueued = false;
					discardBorder.setVisible(false);
					play.setEnabled(true);
					discard.setEnabled(false);
					Data.th.SendMessage(
							Data.pile.get(Data.pile.size() - 1).value + " " + Data.pile.get(Data.pile.size() - 1).suit
									+ " " + Data.pile.get(Data.pile.size() - 1).deckID);
				}
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
		panel.add(discard);

		submit = new JButton();
		submit.setText("Submit");
		submit.setSize(100, 30);
		submit.setLocation(480, 653);
		submit.setVisible(false);
		submit.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (Data.phase == 3) {
					Data.th.SendMessage(Pipe());
					play.setEnabled(true);
					discard.setEnabled(true);
					play.setVisible(true);
					discard.setVisible(true);
					submit.setEnabled(false);
					cancel.setEnabled(false);
					submit.setVisible(false);
					cancel.setVisible(false);
				}
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
		panel.add(submit);

		cancel = new JButton();
		cancel.setText("Cancel");
		cancel.setSize(100, 30);
		cancel.setLocation(1120, 653);
		cancel.setVisible(false);
		cancel.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (Data.phase == 3) {
					cancel();
					play.setEnabled(true);
					discard.setEnabled(true);
					play.setVisible(true);
					discard.setVisible(true);
					submit.setEnabled(false);
					cancel.setEnabled(false);
					submit.setVisible(false);
					cancel.setVisible(false);
				}
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
		panel.add(cancel);

		// panelButtons
		pile = new JButton();
		pile.setName("Pile");
		pile.setSize(85, 120);
		pile.setLocation(802, 200);
		pile.setOpaque(false);
		pile.setContentAreaFilled(false);
		pile.setBorderPainted(false);
		pile.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					pile.setOpaque(true);
					pile.setContentAreaFilled(true);
					pile.setBorderPainted(true);
				}
				draw.setVisible(false);
				PilePress();
			}

			public void mouseReleased(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					pile.setOpaque(false);
					pile.setContentAreaFilled(false);
					pile.setBorderPainted(false);
				}
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {

			}
		});

		panel.add(pile);

		for (int i = 1; i < Data.players.size(); i++) {
			if (Data.players.size() == 4) {
				JButton playerButton = new JButton();
				playerButton.setName("" + i);
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				playerButton.setName("" + i);
				playerButton.setSize(70, 70);
				if (i == 1) {
					playerButton.setLocation(30, 250);
					username.setLocation(15, 325);
				} else if (i == 2) {
					playerButton.setLocation(815, 5);
					username.setLocation(800, 80);
				} else if (i == 3) {
					playerButton.setLocation(1600, 250);
					username.setLocation(1585, 325);
				}
				playerButton.setOpaque(false);
				playerButton.setContentAreaFilled(false);
				playerButton.setBorderPainted(false);
				playerButton.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent me) {
						if (Data.phase != 5) {
							playerButton.setOpaque(true);
							playerButton.setContentAreaFilled(true);
							playerButton.setBorderPainted(true);
							Data.previousPhase = Data.phase;
							Data.phase = 5;
							back.setVisible(true);
							Data.selectedPlayer = Integer.parseInt(playerButton.getName());
							DrawGroups();
							panel.repaint();
						}
					}

					public void mouseReleased(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {
							playerButton.setOpaque(false);
							playerButton.setContentAreaFilled(false);
							playerButton.setBorderPainted(false);
						}
					}

					public void mouseEntered(MouseEvent me) {
					}

					public void mouseExited(MouseEvent me) {
					}

					public void mouseClicked(MouseEvent me) {

					}
				});
				currentPlayers.add(username);
				panel.add(playerButton);
				panel.add(username);
			} else if (Data.players.size() == 6) {
				JButton playerButton = new JButton();
				playerButton.setName("" + i);
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				playerButton.setName("" + i);
				playerButton.setSize(70, 70);
				if (i == 1) {
					playerButton.setLocation(30, 350);
					username.setLocation(15, 425);
				} else if (i == 2) {
					playerButton.setLocation(30, 150);
					username.setLocation(15, 225);
				} else if (i == 3) {
					playerButton.setLocation(815, 5);
					username.setLocation(800, 75);
				} else if (i == 4) {
					playerButton.setLocation(1600, 150);
					username.setLocation(1585, 225);
				} else if (i == 5) {
					playerButton.setLocation(1600, 350);
					username.setLocation(1585, 425);
				}
				playerButton.setOpaque(false);
				playerButton.setContentAreaFilled(false);
				playerButton.setBorderPainted(false);
				playerButton.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent me) {
						if (Data.phase != 5) {
							playerButton.setOpaque(true);
							playerButton.setContentAreaFilled(true);
							playerButton.setBorderPainted(true);

							Data.previousPhase = Data.phase;
							Data.phase = 5;
							Data.selectedPlayer = Integer.parseInt(playerButton.getName());
							back.setVisible(true);
							DrawGroups();
							panel.repaint();
						}
					}

					public void mouseReleased(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {
							playerButton.setOpaque(false);
							playerButton.setContentAreaFilled(false);
							playerButton.setBorderPainted(false);
						}
					}

					public void mouseEntered(MouseEvent me) {
					}

					public void mouseExited(MouseEvent me) {
					}

					public void mouseClicked(MouseEvent me) {

					}
				});
				currentPlayers.add(username);
				panel.add(playerButton);
				panel.add(username);
			}

			else if (Data.players.size() == 8) {
				JButton playerButton = new JButton();
				playerButton.setName("" + i);
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				playerButton.setName("" + i);
				playerButton.setSize(70, 70);
				if (i == 1) {
					playerButton.setLocation(30, 350);
					username.setLocation(15, 425);
				} else if (i == 2) {
					playerButton.setLocation(30, 150);
					username.setLocation(15, 225);
				} else if (i == 3) {
					playerButton.setLocation(30, 5);
					username.setLocation(15, 80);
				} else if (i == 4) {
					playerButton.setLocation(815, 5);
					username.setLocation(800, 80);
				} else if (i == 5) {
					playerButton.setLocation(1600, 5);
					username.setLocation(1585, 80);
				} else if (i == 6) {
					playerButton.setLocation(1600, 150);
					username.setLocation(1585, 225);
				} else if (i == 7) {
					playerButton.setLocation(1600, 350);
					username.setLocation(1585, 425);
				}
				playerButton.setOpaque(false);
				playerButton.setContentAreaFilled(false);
				playerButton.setBorderPainted(false);
				playerButton.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent me) {
						if (Data.phase != 5) {
							playerButton.setOpaque(true);
							playerButton.setContentAreaFilled(true);
							playerButton.setBorderPainted(true);
							Data.previousPhase = Data.phase;
							Data.phase = 5;
							Data.selectedPlayer = Integer.parseInt(playerButton.getName());
							back.setVisible(true);
							DrawGroups();
							panel.repaint();
						}
					}

					public void mouseReleased(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON1) {
							playerButton.setOpaque(false);
							playerButton.setContentAreaFilled(false);
							playerButton.setBorderPainted(false);
						}
					}

					public void mouseEntered(MouseEvent me) {
					}

					public void mouseExited(MouseEvent me) {
					}

					public void mouseClicked(MouseEvent me) {
					}
				});
				currentPlayers.add(username);
				panel.add(playerButton);
				panel.add(username);
			}

		}

		// panelButtons
		back = new JButton();
		back.setName("Back");
		back.setSize(100, 15);
		back.setLocation(170, 490);
		back.setVisible(false);
		back.setText("Back");
		back.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				Data.phase = Data.previousPhase;
				back.setVisible(false);
				DrawGroups();
				panel.repaint();
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
		panel.add(back);

		// panelButtons
		JButton leftButton = new JButton();
		leftButton.setName("Left");
		leftButton.setSize(100, 210);
		leftButton.setLocation(0, 690);
		leftButton.setOpaque(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setBorderPainted(false);
		leftButton.setText("...");
		leftButton.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					leftButton.setOpaque(true);
					leftButton.setContentAreaFilled(true);
					leftButton.setBorderPainted(true);
				}
				Data.handIndex = Math.max(Data.handIndex - 10, 0);
				DrawHand();
				panel.repaint();
			}

			public void mouseReleased(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					leftButton.setOpaque(false);
					leftButton.setContentAreaFilled(false);
					leftButton.setBorderPainted(false);
				}
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {

			}
		});
		panelButtons[0] = leftButton;
		panel.add(leftButton);
		for (int i = 1; i < 11; i++) {
			JButton jButton = new JButton();
			jButton.setName("" + i);
			jButton.setSize(150, 210);
			jButton.setLocation((150 * i) - 50, 690);
			jButton.setOpaque(false);
			jButton.setContentAreaFilled(false);
			jButton.setBorderPainted(false);
			jButton.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent me) {
					if (me.getButton() == MouseEvent.BUTTON1) {
						jButton.setOpaque(true);
						jButton.setContentAreaFilled(true);
						jButton.setBorderPainted(true);
					}
					CardPress(Integer.parseInt(jButton.getName()));
				}

				public void mouseReleased(MouseEvent me) {
					if (me.getButton() == MouseEvent.BUTTON1) {
						jButton.setOpaque(false);
						jButton.setContentAreaFilled(false);
						jButton.setBorderPainted(false);
					}
				}

				public void mouseEntered(MouseEvent me) {
				}

				public void mouseExited(MouseEvent me) {
				}

				public void mouseClicked(MouseEvent me) {

				}
			});
			panelButtons[i] = jButton;
			panel.add(jButton);
		}
		JButton rightButton = new JButton();
		rightButton.setName("Right");
		rightButton.setSize(100, 210);
		rightButton.setLocation(1600, 690);
		rightButton.setOpaque(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setBorderPainted(false);
		rightButton.setText("...");
		rightButton.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					rightButton.setOpaque(true);
					rightButton.setContentAreaFilled(true);
					rightButton.setBorderPainted(true);
				}
				Data.handIndex = Math.min(Data.handIndex + 10, Data.hand.size() - 10);
				DrawHand();
				panel.repaint();
			}

			public void mouseReleased(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					rightButton.setOpaque(false);
					rightButton.setContentAreaFilled(false);
					rightButton.setBorderPainted(false);
				}
			}

			public void mouseEntered(MouseEvent me) {
			}

			public void mouseExited(MouseEvent me) {
			}

			public void mouseClicked(MouseEvent me) {
			}
		});
		panelButtons[11] = rightButton;
		panel.add(rightButton);

		for (int i = 0; i < 11; i++) {
			JButton groupButton = new JButton();
			groupButton.setName("" + i);
			groupButton.setSize(85, 120);
			groupButton.setLocation(285 + (105 * i), 515);
			groupButton.setOpaque(false);
			groupButton.setContentAreaFilled(false);
			groupButton.setBorderPainted(false);
			groupButton.addMouseListener(new MouseListener() {
				public void mousePressed(MouseEvent me) {
					if (me.getButton() == MouseEvent.BUTTON1) {
						groupButton.setOpaque(true);
						groupButton.setContentAreaFilled(true);
						groupButton.setBorderPainted(true);
					}
					GroupPress(Integer.parseInt(groupButton.getName()));
				}

				public void mouseReleased(MouseEvent me) {
					if (me.getButton() == MouseEvent.BUTTON1) {
						groupButton.setOpaque(false);
						groupButton.setContentAreaFilled(false);
						groupButton.setBorderPainted(false);
					}
				}

				public void mouseEntered(MouseEvent me) {
				}

				public void mouseExited(MouseEvent me) {
				}

				public void mouseClicked(MouseEvent me) {
				}
			});

			panel.add(groupButton);
		}
	}

	public String Pipe() {
		String ret = "";
		for (int i = 0; i < 11; i++) {
			ArrayList<Card> l = Data.plannedGroups.get(i);
			for (int j = 0; j < l.size(); j++) {
				Card c = l.get(j);
				ret += c.value + " " + c.suit + " " + c.deckID;
				if (j != l.size() - 1) {
					ret += ",";
				}
			}
			ret += "|";
		}
		return ret;
	}

	@Override
	public void ServerUpdate() {

		DrawHand();
		DrawPile();
		DrawGroups();
		DrawPlayers();
		DrawScoreBoard();
		if (Data.phase == 0) {
			draw.setEnabled(false);
			draw.setVisible(false);
			submit.setEnabled(false);
			submit.setVisible(false);
			cancel.setEnabled(false);
			cancel.setVisible(false);
			play.setEnabled(false);
			play.setVisible(true);
			discard.setEnabled(false);
			discard.setVisible(true);
		}
		if (Data.phase == 1) {
			draw.setEnabled(true);
			draw.setVisible(true);
			submit.setEnabled(false);
			submit.setVisible(false);
			cancel.setEnabled(false);
			cancel.setVisible(false);
			play.setEnabled(false);
			play.setVisible(true);
			discard.setEnabled(false);
			discard.setVisible(true);
		}
		if (Data.phase == 2) {
			draw.setEnabled(false);
			draw.setVisible(false);
			submit.setEnabled(false);
			submit.setVisible(false);
			cancel.setEnabled(false);
			cancel.setVisible(false);
			play.setEnabled(true);
			play.setVisible(true);
			discard.setEnabled(true);
			discard.setVisible(true);
		}
		if (Data.phase == 4) {
			draw.setEnabled(false);
			draw.setVisible(false);
			submit.setEnabled(false);
			submit.setVisible(false);
			cancel.setEnabled(false);
			cancel.setVisible(false);
			play.setEnabled(false);
			play.setVisible(true);
			discard.setEnabled(true);
			discard.setVisible(true);
		}
		panel.repaint();
	}

	/**
	 * Draws the hand at the bottom of the screen
	 */
	public void DrawHand() {
		for (JLabel j : currentCards) {
			cardPanel.remove(j);
		}
		if (!Data.players.get(Data.userID).foot) {
			foot.setVisible(false);
		}
		currentCards.clear();

		Data.hand.sort(c);

		if (Data.players.get(Data.userID).foot) {
			foot.setVisible(true);
		}

		if (Data.phase == 1) {
			draw.setVisible(true);
		} else {
			draw.setVisible(false);
		}

		for (int i = Data.handIndex; i < Math.min(Data.handIndex + 10, Data.hand.size()); i++) {
			if (Data.hand.get(i).suit != -1) {
				CreateNumberForCard(45 + (150 * (i - Data.handIndex)), 30, Data.hand.get(i).suit,
						Data.hand.get(i).value - 1, currentCards, cardPanel, false);
				CreateSuitForCard(45 + (150 * (i - Data.handIndex)), 120, Data.hand.get(i).suit, currentCards,
						cardPanel);
			} else {
				if (Data.hand.get(i).value == 52) {
					CreateNumberForCard(45 + (150 * (i - Data.handIndex)), 75, 52, -1, currentCards, cardPanel, true);
				} else {
					CreateNumberForCard(45 + (150 * (i - Data.handIndex)), 75, 53, -1, currentCards, cardPanel, true);
				}
			}
		}
	}

	/**
	 * Draws the top card of the pile in the center of the wreath
	 */
	public void DrawPile() {
		for (JLabel j : currentPile) {
			discardPile.remove(j);
		}
		lightGreen.setVisible(false);
		darkGreen.setVisible(false);
		yellow.setVisible(false);
		orange.setVisible(false);
		lightRed.setVisible(false);
		darkRed.setVisible(false);
		currentPile.clear();
		if (Data.pileNumber > 0) {

			if (Data.pileNumber > 0 && Data.pileNumber < 4) {
				lightGreen.setVisible(true);
			} else if (Data.pileNumber > 3 && Data.pileNumber < 8) {
				darkGreen.setVisible(true);
			} else if (Data.pileNumber > 7 && Data.pileNumber < 13) {
				yellow.setVisible(true);
			} else if (Data.pileNumber > 12 && Data.pileNumber < 19) {
				orange.setVisible(true);
			} else if (Data.pileNumber > 18 && Data.pileNumber < 26) {
				lightRed.setVisible(true);
			} else {
				darkRed.setVisible(true);
			}

			if (Data.pile.get(Data.pile.size() - 1).value < 52) {
				CreateNumberForCard(7, 0, Data.pile.get(Data.pile.size() - 1).suit,
						Data.pile.get(Data.pile.size() - 1).value - 1, currentPile, discardPile, false);
				CreateSuitForCard(7, 60, Data.pile.get(Data.pile.size() - 1).suit, currentPile, discardPile);
				CreateCardStock(0, 0, currentPile, discardPile);
			} else if (Data.pile.get(Data.pile.size() - 1).value == 52) {
				CreateNumberForCard(7, 30, 52, -1, currentPile, discardPile, true);
				CreateCardStock(0, 0, currentPile, discardPile);
			} else {
				CreateNumberForCard(7, 30, 53, -1, currentPile, discardPile, true);
				CreateCardStock(0, 0, currentPile, discardPile);
			}
		}
	}

	/**
	 * Draws all the groups the player (and teammate) have played
	 */
	public void DrawGroups() {
		for (JLabel j : currentGroups) {
			groupPanel.remove(j);
		}
		currentGroups.clear();
		if (Data.phase == 5) {
			System.out.println("PHASE 5");
			Player p = Data.players.get((Data.userID + Data.selectedPlayer) % Data.players.size());
			System.out.println(p.name);
			for (int i = 0; i < 11; i++) {
				p.groups.get(i).sort(c);
			}

			for (int i = 0; i < 11; i++) {
				dirtyBooks.get(i).setVisible(false);
				cleanBooks.get(i).setVisible(false);
				if (p.groups.get(i).size() > 0) {
					JLabel groupCount = new JLabel();
					if (Data.DirtyBook(p.groups.get(i))) {
						groupCount.setForeground(Color.black);
						if(p.groups.get(i).size() > 6) {
							dirtyBooks.get(i).setVisible(true);
						}
					} else {
						groupCount.setForeground(Color.red);
						if(p.groups.get(i).size() > 6) {
							cleanBooks.get(i).setVisible(true);
						}
					}
					groupCount.setText("" + p.groups.get(i).size());
					groupCount.setSize(20, 25);
					groupCount.setLocation(43 + (105 * i), 140);
					currentGroups.add(groupCount);
					groupPanel.add(groupCount);
					if (p.groups.get(i).get(p.groups.get(i).size() - 1).value < 51) {
						CreateNumberForCard(17 + (105 * i), 10, p.groups.get(i).get(p.groups.get(i).size() - 1).suit,
								p.groups.get(i).get(p.groups.get(i).size() - 1).value - 1, currentGroups, groupPanel,
								false);
						CreateSuitForCard(17 + (105 * i), 70, p.groups.get(i).get(p.groups.get(i).size() - 1).suit,
								currentGroups, groupPanel);
						CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
					} else {
						if (p.groups.get(i).get(p.groups.get(i).size() - 1).value == 52) {
							CreateNumberForCard(17 + (105 * i), 50, 52, -1, currentGroups, groupPanel, true);
							CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
						} else {
							CreateNumberForCard(17 + (105 * i), 50, 53, -1, currentGroups, groupPanel, true);
							CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
						}
					}
				}
			}

		} else {
			for (int i = 0; i < 11; i++) {
				Data.playedGroups.get(i).sort(c);
			}

			for (int i = 0; i < 11; i++) {
				JLabel groupCount = new JLabel();
				int combined = Data.plannedGroups.get(i).size() + Data.playedGroups.get(i).size();
				dirtyBooks.get(i).setVisible(false);
				cleanBooks.get(i).setVisible(false);
				if (Data.DirtyBook(Data.plannedGroups.get(i)) || Data.DirtyBook(Data.playedGroups.get(i))) {
					groupCount.setForeground(Color.black);
					if(Data.plannedGroups.get(i).size() + Data.playedGroups.get(i).size() > 6) {
						dirtyBooks.get(i).setVisible(true);
					}
				} else {
					groupCount.setForeground(Color.red);
					if(Data.plannedGroups.get(i).size() + Data.playedGroups.get(i).size() > 6) {
						cleanBooks.get(i).setVisible(true);
					}
				}
				groupCount.setText("" + combined);
				groupCount.setSize(20, 25);
				groupCount.setLocation(43 + (105 * i), 140);
				currentGroups.add(groupCount);
				groupPanel.add(groupCount);
				int n = Data.plannedGroups.get(i).size();
				int m = Data.playedGroups.get(i).size();
				if (n > 0) {
					if (Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value < 51) {
						CreateNumberForCard(17 + (105 * i), 10,
								Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit,
								Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value - 1,
								currentGroups, groupPanel, false);
						CreateSuitForCard(17 + (105 * i), 70,
								Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit, currentGroups,
								groupPanel);
						CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
					} else {
						if (Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value == 52) {
							CreateNumberForCard(17 + (105 * i), 50, 52, -1, currentGroups, groupPanel, true);
							CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
						} else {
							CreateNumberForCard(17 + (105 * i), 50, 53, -1, currentGroups, groupPanel, true);
							CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
						}
					}
				} else if (m > 0) {
					if (Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value < 51) {
						CreateNumberForCard(17 + (105 * i), 10,
								Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit,
								Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value - 1,
								currentGroups, groupPanel, false);
						CreateSuitForCard(17 + (105 * i), 70,
								Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit, currentGroups,
								groupPanel);
						CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
					} else {
						if (Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value == 52) {
							CreateNumberForCard(17 + (105 * i), 50, 52, -1, currentGroups, groupPanel, true);
							CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
						} else {
							CreateNumberForCard(17 + (105 * i), 50, 53, -1, currentGroups, groupPanel, true);
							CreateCardStock(10 + (105 * i), 20, currentGroups, groupPanel);
						}
					}

				}
			}
		}
	}

	/**
	 * Draws all players on the screen
	 */
	public void DrawPlayers() {
		for (JLabel j : currentPlayers) {
			panel.remove(j);
		}
		currentPlayers.clear();
		for (int i = 1; i < Data.players.size(); i++) {
			if (Data.players.size() == 4) {
				System.out.println("CURRENT TURN:" + (Data.CurrentTurn-Data.userID)%Data.players.size());
				JLabel currentTurnFrame = new JLabel();
				try {
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/PlayIndicator.png"));
					currentTurnFrame = new JLabel(new ImageIcon(image));
					currentTurnFrame.setSize(75, 75);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 1) {
					currentTurnFrame.setLocation(25, 245);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 2) {
					currentTurnFrame.setLocation(810, 0);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 3) {
					currentTurnFrame.setLocation(1580, 320);
				}
				currentPlayers.add(currentTurnFrame);
				panel.add(currentTurnFrame);
				
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					username.setLocation(15, 325);
				} else if (i == 2) {
					username.setLocation(800, 80);
				} else if (i == 3) {
					username.setLocation(1585, 325);
				}
				currentPlayers.add(username);
				panel.add(username);

				if (Data.players.get((Data.userID + i) % Data.players.size()).foot) {
					JLabel footIndicator = new JLabel();
					try {
						image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherFootIndicator.png"));
						footIndicator = new JLabel(new ImageIcon(image));
						footIndicator.setSize(20, 28);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					if (i == 1) {
						footIndicator.setLocation(105, 255);
					} else if (i == 2) {
						footIndicator.setLocation(900, 10);
					} else if (i == 3) {
						footIndicator.setLocation(1575, 255);
					}
					panel.add(footIndicator);
					currentPlayers.add(footIndicator);
				}

				JLabel cardCount = new JLabel();

				cardCount.setText("" + Data.players.get((Data.userID + i) % Data.players.size()).cards);
				cardCount.setSize(25, 20);
				if (i == 1) {
					cardCount.setLocation(105, 295);
				} else if (i == 2) {
					cardCount.setLocation(900, 50);
				} else if (i == 3) {
					cardCount.setLocation(1575, 295);
				}
				panel.add(cardCount);
				currentPlayers.add(cardCount);

			} else if (Data.players.size() == 6) {
				JLabel currentTurnFrame = new JLabel();
				try {
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/PlayIndicator.png"));
					currentTurnFrame = new JLabel(new ImageIcon(image));
					currentTurnFrame.setSize(75, 75);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 1) {
					currentTurnFrame.setLocation(25, 345);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 2) {
					currentTurnFrame.setLocation(25, 145);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 3) {
					currentTurnFrame.setLocation(810, 0);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 4) {
					currentTurnFrame.setLocation(1595, 145);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 5) {
					currentTurnFrame.setLocation(1595, 420);
				}
				currentPlayers.add(currentTurnFrame);
				panel.add(currentTurnFrame);
				
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					username.setLocation(15, 425);
				} else if (i == 2) {
					username.setLocation(15, 225);
				} else if (i == 3) {
					username.setLocation(800, 75);
				} else if (i == 4) {
					username.setLocation(1580, 225);
				} else if (i == 5) {
					username.setLocation(1580, 425);
				}
				panel.add(username);
				currentPlayers.add(username);

				if (Data.players.get((Data.userID + i) % Data.players.size()).foot) {
					JLabel footIndicator = new JLabel();
					try {
						image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherFootIndicator.png"));
						footIndicator = new JLabel(new ImageIcon(image));
						footIndicator.setSize(20, 28);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					if (i == 1) {
						footIndicator.setLocation(105, 355);
					} else if (i == 2) {
						footIndicator.setLocation(105, 155);
					} else if (i == 3) {
						footIndicator.setLocation(900, 10);
					} else if (i == 4) {
						footIndicator.setLocation(1575, 155);
					} else if (i == 5) {
						footIndicator.setLocation(1575, 355);
					}
					panel.add(footIndicator);
					currentPlayers.add(footIndicator);
				}

				JLabel cardCount = new JLabel();
				cardCount.setText("" + Data.players.get((Data.userID + i) % Data.players.size()).cards);
				cardCount.setSize(25, 20);
				if (i == 1) {
					cardCount.setLocation(105, 395);
				} else if (i == 2) {
					cardCount.setLocation(105, 195);
				} else if (i == 3) {
					cardCount.setLocation(900, 50);
				} else if (i == 4) {
					cardCount.setLocation(1575, 195);
				} else if (i == 5) {
					cardCount.setLocation(1575, 395);
				}
				panel.add(cardCount);
				currentPlayers.add(cardCount);

			} else if (Data.players.size() == 8) {
				JLabel currentTurnFrame = new JLabel();
				try {
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/PlayIndicator.png"));
					currentTurnFrame = new JLabel(new ImageIcon(image));
					currentTurnFrame.setSize(75, 75);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 1) {
					currentTurnFrame.setLocation(25, 420);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 2) {
					currentTurnFrame.setLocation(15, 220);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 3) {
					currentTurnFrame.setLocation(10, 75);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 4) {
					currentTurnFrame.setLocation(795, 75);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 5) {
					currentTurnFrame.setLocation(1580, 75);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 6) {
					currentTurnFrame.setLocation(1580, 220);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 7) {
					currentTurnFrame.setLocation(1580, 420);
				}				
				currentPlayers.add(currentTurnFrame);
				panel.add(currentTurnFrame);
				
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					username.setLocation(15, 425);
				} else if (i == 2) {
					username.setLocation(15, 225);
				} else if (i == 3) {
					username.setLocation(15, 80);
				} else if (i == 4) {
					username.setLocation(800, 80);
				} else if (i == 5) {
					username.setLocation(1585, 80);
				} else if (i == 6) {
					username.setLocation(1585, 225);
				} else if (i == 7) {
					username.setLocation(1585, 425);
				}
				panel.add(username);
				currentPlayers.add(username);

				if (Data.players.get((Data.userID + i) % Data.players.size()).foot) {
					JLabel footIndicator = new JLabel();
					try {
						image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherFootIndicator.png"));
						footIndicator = new JLabel(new ImageIcon(image));
						footIndicator.setSize(20, 28);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					if (i == 1) {
						footIndicator.setLocation(105, 355);
					} else if (i == 2) {
						footIndicator.setLocation(105, 155);
					} else if (i == 3) {
						footIndicator.setLocation(105, 10);
					} else if (i == 4) {
						footIndicator.setLocation(900, 10);
					} else if (i == 5) {
						footIndicator.setLocation(1575, 10);
					} else if (i == 6) {
						footIndicator.setLocation(1575, 155);
					} else if (i == 7) {
						footIndicator.setLocation(1575, 355);
					}
					panel.add(footIndicator);
					currentPlayers.add(footIndicator);
				}

				JLabel cardCount = new JLabel();
				cardCount.setText("" + Data.players.get((Data.userID + i) % Data.players.size()).cards);
				cardCount.setSize(25, 20);
				if (i == 1) {
					cardCount.setLocation(105, 395);
				} else if (i == 2) {
					cardCount.setLocation(105, 195);
				} else if (i == 3) {
					cardCount.setLocation(105, 50);
				} else if (i == 4) {
					cardCount.setLocation(900, 50);
				} else if (i == 5) {
					cardCount.setLocation(1575, 50);
				} else if (i == 6) {
					cardCount.setLocation(1575, 195);
				} else if (i == 7) {
					cardCount.setLocation(1575, 395);
				}
				panel.add(cardCount);
				currentPlayers.add(cardCount);

			}

		}
	}

	public void DrawScoreBoard() {
		for (JLabel j : currentScores) {
			ScoreBoard.remove(j);
		}
		currentScores.clear();
		JLabel title = new JLabel();
		title.setSize(200, 30);
		title.setLocation(0, 0);
		title.setText("Scores");
		currentScores.add(title);
		ScoreBoard.add(title);
		for (int i = 0; i < Data.scores.size(); i++) {
			JLabel score = new JLabel();
			score.setText(Data.teamNames.get(i) + ":" + Data.scores.get(i));
			score.setSize(100, 20);
			score.setLocation(0, 20 + (i * 20));
			currentScores.add(score);
			ScoreBoard.add(score);
		}

	}

	/**
	 * Adds the number of a card to a given JLabel, at the x, y location
	 * 
	 * @param x                     - x pos relative to the JLabel
	 * @param y                     - y pos relative to the JLabel
	 * @param locationInFirstArray  - Accessing the image double array
	 * @param locationInSecondArray - Accessing the image double array
	 * @param deleteFrom            - An ArrayList to track all of the elements
	 *                              drawn on the screen
	 * @param labelToAddTo          - the label to add the item to
	 * @param Joker                 - Determines if should be drawn as a joker
	 */
	private void CreateNumberForCard(int x, int y, int locationInFirstArray, int locationInSecondArray,
			ArrayList<JLabel> deleteFrom, JLabel labelToAddTo, boolean Joker) {
		if (Joker) {
			JLabel num = new JLabel();
			String RorB;
			if (locationInFirstArray == 52) {
				RorB = "Black";
			} else {
				RorB = "Red";
			}
			try {
				image = ImageIO
						.read(new File("res/images/" + Data.Resolution + "/" + RorB + locationInFirstArray + ".png"));
				num = new JLabel(new ImageIcon(image));
				num.setSize(60, 60);
				num.setLocation(x, y);
				deleteFrom.add(num);
				labelToAddTo.add(num);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			JLabel num = new JLabel();
			try {
				image = ImageIO.read(new File(pictureLocations[locationInFirstArray][locationInSecondArray]));
				num = new JLabel(new ImageIcon(image));
				num.setSize(60, 60);
				num.setLocation(x, y);
				deleteFrom.add(num);
				labelToAddTo.add(num);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Draws a suit on a given JLabel
	 * 
	 * @param x               - the x pos of the suit relative to the JLabel
	 * @param y               - the y pos of the suit relative to the JLabel
	 * @param locationInArray - Access to the suit array
	 * @param deleteFrom      - Array to keep track of what suits are displayed
	 * @param labelToAddTo    - the label to add the suit to
	 */
	private void CreateSuitForCard(int x, int y, int locationInArray, ArrayList<JLabel> deleteFrom,
			JLabel labelToAddTo) {
		JLabel suit = new JLabel();
		try {
			image = ImageIO.read(new File(suitLocations[locationInArray]));
			suit = new JLabel(new ImageIcon(image));
			suit.setSize(60, 60);
			suit.setLocation(x, y);
			deleteFrom.add(suit);
			labelToAddTo.add(suit);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Create the card stock on the screen at the given x,y location
	 * 
	 * @param x            - The x pos to place the card stock relative to the
	 *                     JLabel
	 * @param y            - The y pos to place the card stock relative to the
	 *                     JLabel
	 * @param deleteFrom   - An array to keep track of the card stock
	 * @param labelToAddTo - The label to add the card stock to.
	 */
	private void CreateCardStock(int x, int y, ArrayList<JLabel> deleteFrom, JLabel labelToAddTo) {
		JLabel stock = new JLabel();
		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/CardStock.png"));
			stock = new JLabel(new ImageIcon(image));
			stock.setSize(85, 120);
			stock.setLocation(x, y);
			deleteFrom.add(stock);
			labelToAddTo.add(stock);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void CardPress(int i) {
		if (Data.phase == 3) {
			Data.IsQueued = true;
			Data.Queued = Data.hand.get(i + Data.handIndex - 1);
			Data.QueuedIndex = i + Data.handIndex - 1;
		} else if (Data.phase == 4) {
			if (Data.IsQueued) {
				Data.Queued = Data.hand.get(i + Data.handIndex - 1);
				Data.QueuedIndex = i + Data.handIndex - 1;
			} else {
				Data.IsQueued = true;
				Data.Queued = Data.hand.get(i + Data.handIndex - 1);
				Data.QueuedIndex = i + Data.handIndex - 1;
			}
		}
	}

	public void GroupPress(int i) {
		if (Data.phase == 3 && Data.IsQueued) {
			if (Data.Queued.value == ValidPlays[i] || Data.Queued.value == 2 || Data.Queued.value == 52
					|| Data.Queued.value == 53) {
				Card c = new Card(Data.Queued);

				Data.plannedGroups.get(i).add(c);

				Data.hand.remove(Data.QueuedIndex);
				Data.handIndex = Math.min(Data.handIndex, Data.hand.size() - 10);
				Data.handIndex = Math.max(Data.handIndex, 0);
				DrawHand();
				DrawGroups();
				panel.repaint();
			}
			Data.QueuedIndex = -1;
			Data.IsQueued = false;
		} else if (Data.phase == 3 && !Data.IsQueued) {
			int n = Data.plannedGroups.get(i).size();
			if (n > 0) {
				Card c = new Card(Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1));
				Data.hand.add(c);
				Data.plannedGroups.get(i).remove(Data.plannedGroups.get(i).size() - 1);
				DrawHand();
				DrawGroups();
				panel.repaint();
			}
		}
	}

	public void PilePress() {
		if (Data.phase == 1) {
			Data.th.SendMessage("P");
		} else if (Data.phase == 4 && Data.IsQueued) {
			if (Data.discardQueued) {
				Data.hand.add(Data.pile.get(Data.pile.size() - 1));
				Data.pile.remove(Data.pile.size() - 1);
				Data.pileNumber--;
			}
			Card c = new Card(Data.Queued);
			Data.pile.add(c);
			Data.hand.remove(Data.QueuedIndex);
			Data.QueuedIndex = -1;
			Data.IsQueued = false;
			Data.discardQueued = true;
			Data.pileNumber++;
			DrawHand();
			DrawPile();
			panel.repaint();
		} else if (Data.phase == 4 && Data.discardQueued) {
			Data.hand.add(Data.pile.get(Data.pile.size() - 1));
			Data.pile.remove(Data.pile.size() - 1);
			Data.QueuedIndex = -1;
			Data.IsQueued = false;
			Data.discardQueued = false;
			Data.pileNumber--;
			DrawHand();
			DrawPile();
			panel.repaint();
		}
	}

	public void cancel() {
		for (int i = 0; i < 11; i++) {
			Data.hand.addAll(Data.plannedGroups.get(i));
			Data.plannedGroups.get(i).clear();
		}
		Data.phase = 2;
		DrawGroups();
		DrawHand();
		panel.repaint();
	}

	public static void resize(Component c, float scaleX, float scaleY) {
		Point s = c.getLocation();
		int x = c.getWidth();
		int y = c.getHeight();
		c.setLocation((int) scaleX * s.x, (int) scaleY * s.y);
		c.setSize((int) scaleX * x, (int) scaleY * y);
		if (c instanceof JLabel) {
			if (((JLabel) c).getIcon() != null) {
				Image img = ((ImageIcon) ((JLabel) c).getIcon()).getImage();
				Image newImg = img.getScaledInstance(c.getWidth(), c.getHeight(), java.awt.Image.SCALE_SMOOTH);
				((JLabel) c).setIcon(new ImageIcon(newImg));
			}
		}
		if (c instanceof Container) {
			Component[] b = ((Container) c).getComponents();
			for (Component t : b) {
				resize(t, scaleX, scaleY);
			}
		}

	}

	@Override
	public void run() {
		CreateWindow();
	}

}

class CardOrganizer implements Comparator<Card> {
	int[] order = { 0, 3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

	@Override
	public int compare(Card a, Card b) {
		if (a.value > 51) {
			if (b.value > 51) {
				if (a.value == b.value) {
					return a.deckID - b.deckID;
				}
				return a.value - b.value;
			}
			return -1;
		}

		if (b.value > 51) {
			return 1;
		}

		if (a.value == b.value) {
			if (a.suit == b.suit) {
				return a.deckID - b.deckID;
			} else {
				return a.suit - b.suit;
			}
		}

		return order[a.value] - order[b.value];
	}

}
