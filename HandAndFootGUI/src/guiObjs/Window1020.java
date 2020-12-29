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

public class Window1020 extends Window implements Runnable {

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
	JLabel discardConfirm;
	JLabel justDrew;

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
	ArrayList<JLabel> justDrewLabels;

	@Override
	public void CreateWindow() {
		Create1020Window();
	}

	private void Create1020Window() {
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
		justDrewLabels = new ArrayList<JLabel>();
		

		CreatePanelButtons();
		


		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Wreath.png"));
			JLabel wreath = new JLabel(new ImageIcon(image));
			wreath.setSize(600, 240);
			wreath.setLocation(210, 54);
			panel.add(wreath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		

		
		

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/LightGreen.png"));
			lightGreen = new JLabel(new ImageIcon(image));
			lightGreen.setSize(57, 78);
			lightGreen.setLocation(478, 117);
			lightGreen.setVisible(false);
			panel.add(lightGreen);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DarkGreen.png"));
			darkGreen = new JLabel(new ImageIcon(image));
			darkGreen.setSize(57, 78);
			darkGreen.setLocation(478, 117);
			darkGreen.setVisible(false);
			panel.add(darkGreen);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Yellow.png"));
			yellow = new JLabel(new ImageIcon(image));
			yellow.setSize(57, 78);
			yellow.setLocation(478, 117);
			yellow.setVisible(false);
			panel.add(yellow);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Orange.png"));
			orange = new JLabel(new ImageIcon(image));
			orange.setSize(57, 78);
			orange.setLocation(478, 117);
			orange.setVisible(false);
			panel.add(orange);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/LightRed.png"));
			lightRed = new JLabel(new ImageIcon(image));
			lightRed.setSize(57, 78);
			lightRed.setLocation(478, 117);
			lightRed.setVisible(false);
			panel.add(lightRed);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DarkRed.png"));
			darkRed = new JLabel(new ImageIcon(image));
			darkRed.setSize(57, 78);
			darkRed.setLocation(478, 117);
			darkRed.setVisible(false);
			panel.add(darkRed);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/CardPanel.png"));
			cardPanel = new JLabel(new ImageIcon(image));
			cardPanel.setSize(900, 126);
			cardPanel.setLocation(60, 414);
			panel.add(cardPanel);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		justDrew = new JLabel();
		justDrew.setSize(51, 82);
		justDrew.setLocation(860, 312);
		justDrew.setVisible(false);
		panel.add(justDrew);

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/GroupPanel.png"));
			groupPanel = new JLabel(new ImageIcon(image));
			groupPanel.setSize(690, 96);
			groupPanel.setLocation(165, 297);
			panel.add(groupPanel);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/Discard.png"));
			discardBorder = new JLabel(new ImageIcon(image));
			discardBorder.setSize(1020, 540);
			discardBorder.setLocation(0, 0);
			discardBorder.setVisible(false);
			panel.add(discardBorder);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DiscardPile.png"));
			discardPile = new JLabel(new ImageIcon(image));
			discardPile.setSize(51, 72);
			discardPile.setLocation(481, 120);
			panel.add(discardPile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/FootIndicator.png"));
			foot = new JLabel(new ImageIcon(image));
			foot.setSize(51, 72);
			foot.setLocation(915, 312);
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
					playerImage.setSize(42, 42);
					if (i == 1)
						playerImage.setLocation(18, 150);
					else if (i == 2)
						playerImage.setLocation(489, 3);
					else if (i == 3)
						playerImage.setLocation(960, 150);
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
					playerImage.setSize(42, 42);
					if (i == 1)
						playerImage.setLocation(18, 210);
					else if (i == 2)
						playerImage.setLocation(18, 90);
					else if (i == 3)
						playerImage.setLocation(489, 3);
					else if (i == 4)
						playerImage.setLocation(960, 90);
					else if (i == 5)
						playerImage.setLocation(960, 210);
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
					playerImage.setSize(42, 42);
					if (i == 1)
						playerImage.setLocation(18, 210);
					else if (i == 2)
						playerImage.setLocation(18, 90);
					else if (i == 3)
						playerImage.setLocation(18, 3);
					else if (i == 4)
						playerImage.setLocation(489, 3);
					else if (i == 5)
						playerImage.setLocation(960, 3);
					else if (i == 6)
						playerImage.setLocation(960, 90);
					else if (i == 7)
						playerImage.setLocation(960, 210);
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
				clean.setSize(54, 74);
				clean.setLocation(3 + (63 * i), 12);
				clean.setVisible(false);
				cleanBooks.add(clean);
				groupPanel.add(clean);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			try {
				image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DirtyBook.png"));
				JLabel dirty = new JLabel(new ImageIcon(image));
				dirty.setSize(54, 74);
				dirty.setLocation(3 + (63 * i), 12);
				dirty.setVisible(false);
				dirtyBooks.add(dirty);
				groupPanel.add(dirty);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}
		
		try {
			image = ImageIO.read(new File("res/images/" + Data.Resolution + "/DiscardConfirm.png"));
			discardConfirm = new JLabel(new ImageIcon(image));
			discardConfirm.setSize(96, 26);
			discardConfirm.setLocation(669, 389);
			panel.add(discardConfirm);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		for (int i = 0; i < 13; i++) {
			pictureLocations[0][i] = "res/images/" + Data.Resolution + "/Black" + (i + 1) + ".png";
			pictureLocations[3][i] = "res/images/" + Data.Resolution + "/Black" + (i + 1) + ".png";
			pictureLocations[1][i] = "res/images/" + Data.Resolution + "/Red" + (i + 1) + ".png";
			pictureLocations[2][i] = "res/images/" + Data.Resolution + "/Red" + (i + 1) + ".png";
		}

		ScoreBoard = new JLabel();
		ScoreBoard.setSize(180, 180);
		ScoreBoard.setLocation(840, 0);
		panel.add(ScoreBoard);
		ServerUpdate();

		// frame Code
		frame = new JFrame("My First GUI");
		frame.setSize(1020, 570);

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
		play.setSize(90, 20);
		play.setLocation(288, 392);
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
		draw.setSize(90, 20);
		draw.setLocation(474, 249);
		draw.setVisible(false);
		draw.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				if (Data.phase == 1) {
					draw.setEnabled(false);
					draw.setVisible(false);
					Data.th.SendMessage("D");
					justDrew.setVisible(true);
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
		discard.setSize(90, 20);
		discard.setLocation(672, 392);
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
		submit.setSize(90, 20);
		submit.setLocation(288, 392);
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
		cancel.setSize(90, 20);
		cancel.setLocation(672, 392);
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
		pile.setSize(51, 72);
		pile.setLocation(481, 120);
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
				playerButton.setSize(42, 42);
				if (i == 1) {
					playerButton.setLocation(18, 150);
					username.setLocation(0, 197);
				} else if (i == 2) {
					playerButton.setLocation(489, 3);
					username.setLocation(460, 50);
				} else if (i == 3) {
					playerButton.setLocation(960, 150);
					username.setLocation(931, 197);
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
			} else if (Data.players.size() == 6) {
				JButton playerButton = new JButton();
				playerButton.setName("" + i);
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				playerButton.setName("" + i);
				playerButton.setSize(42, 42);
				if (i == 1) {
					playerButton.setLocation(18, 210);
					username.setLocation(0, 257);
				} else if (i == 2) {
					playerButton.setLocation(18, 90);
					username.setLocation(0, 135);
				} else if (i == 3) {
					playerButton.setLocation(489, 3);
					username.setLocation(460, 50);
				} else if (i == 4) {
					playerButton.setLocation(960, 90);
					username.setLocation(931, 137);
				} else if (i == 5) {
					playerButton.setLocation(960, 210);
					username.setLocation(931, 257);
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
				playerButton.setSize(42, 42);
				if (i == 1) {
					playerButton.setLocation(18, 210);
					username.setLocation(0, 257);
				} else if (i == 2) {
					playerButton.setLocation(18, 90);
					username.setLocation(0, 137);
				} else if (i == 3) {
					playerButton.setLocation(18, 3);
					username.setLocation(0, 50);
				} else if (i == 4) {
					playerButton.setLocation(489, 3);
					username.setLocation(460, 50);
				} else if (i == 5) {
					playerButton.setLocation(960, 3);
					username.setLocation(931, 50);
				} else if (i == 6) {
					playerButton.setLocation(960, 90);
					username.setLocation(931, 137);
				} else if (i == 7) {
					playerButton.setLocation(960, 210);
					username.setLocation(931, 257);
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
							Data.selectedPlayer = Integer.parseInt(playerButton.getName());
							Data.phase = 5;
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
		back.setSize(90, 20);
		back.setLocation(102, 294);
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
		leftButton.setSize(60, 126);
		leftButton.setLocation(0, 414);
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
			jButton.setSize(90, 126);
			jButton.setLocation((90 * i) - 30, 414);
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
		rightButton.setSize(60, 126);
		rightButton.setLocation(960, 414);
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
			groupButton.setSize(51, 72);
			groupButton.setLocation(171 + (63 * i), 309);
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
			discardConfirm.setVisible(false);
			justDrew.setVisible(false);
			Data.drewCard = false;
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
		if(Data.endGame) {
			panel.setVisible(false);
			JPanel finalPanel = new JPanel();
			finalPanel.setLayout(null);
			finalPanel.setSize(1020, 570);
			finalPanel.setBackground(new Color(153, 221, 255));
			ScoreBoard.setLocation(285, 510);
			finalPanel.add(ScoreBoard);
			frame.add(finalPanel);
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
		
		if(Data.drewCard) {
			justDrew.setVisible(true);
			for(JLabel j : justDrewLabels) {
				justDrew.remove(j);
			}
			justDrewLabels.clear();
			JLabel justDrewTitle = new JLabel();
			justDrewTitle.setText("Drew: ");
			justDrewTitle.setSize(50, 10);
			justDrewTitle.setLocation(0, 0);
			justDrew.add(justDrewTitle);
			if(Data.drew.value != 52 && Data.drew.value != 53) {
				CreateNumberForCard(0, 10, Data.drew.suit, Data.drew.value-1, justDrewLabels, justDrew, false);
				CreateSuitForCard(0, 46, Data.drew.suit, justDrewLabels, justDrew);
			}
			else {
				if (Data.drew.value == 52) {
					CreateNumberForCard(0, 36, 52, -1, justDrewLabels, justDrew, true);
				} else {
					CreateNumberForCard(0, 36, 53, -1, justDrewLabels, justDrew, true);
				}
			}
			
			CreateCardStock(0, 10, justDrewLabels, justDrew);
		}

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
				CreateNumberForCard(27 + (90 * (i - Data.handIndex)), 18, Data.hand.get(i).suit,
						Data.hand.get(i).value - 1, currentCards, cardPanel, false);
				CreateSuitForCard(27 + (90 * (i - Data.handIndex)), 72, Data.hand.get(i).suit, currentCards, cardPanel);
			} else {
				if (Data.hand.get(i).value == 52) {
					CreateNumberForCard(27 + (90 * (i - Data.handIndex)), 45, 52, -1, currentCards, cardPanel, true);
				} else {
					CreateNumberForCard(27 + (90 * (i - Data.handIndex)), 45, 53, -1, currentCards, cardPanel, true);
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
				CreateNumberForCard(4, 0, Data.pile.get(Data.pile.size() - 1).suit,
						Data.pile.get(Data.pile.size() - 1).value - 1, currentPile, discardPile, false);
				CreateSuitForCard(4, 36, Data.pile.get(Data.pile.size() - 1).suit, currentPile, discardPile);
				CreateCardStock(0, 0, currentPile, discardPile);
			} else if (Data.pile.get(Data.pile.size() - 1).value == 52) {
				CreateNumberForCard(4, 18, 52, -1, currentPile, discardPile, true);
				CreateCardStock(0, 0, currentPile, discardPile);
			} else {
				CreateNumberForCard(4, 18, 53, -1, currentPile, discardPile, true);
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
			Player p = Data.players.get((Data.userID + Data.selectedPlayer) % Data.players.size());
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
						else {
							dirtyBooks.get(i).setVisible(false);
						}
					} else {
						groupCount.setForeground(Color.red);
						if(p.groups.get(i).size() > 6) {
							cleanBooks.get(i).setVisible(true);
						}
						else {
							cleanBooks.get(i).setVisible(false);
						}
					}
					groupCount.setText("" + p.groups.get(i).size());
					groupCount.setSize(15, 15);
					groupCount.setLocation(26 + (63 * i), 84);
					currentGroups.add(groupCount);
					groupPanel.add(groupCount);
					if (p.groups.get(i).get(p.groups.get(i).size() - 1).value < 51) {
						CreateNumberForCard(10 + (63 * i), 6, p.groups.get(i).get(p.groups.get(i).size() - 1).suit,
								p.groups.get(i).get(p.groups.get(i).size() - 1).value - 1, currentGroups, groupPanel,
								false);
						CreateSuitForCard(10 + (63 * i), 42, p.groups.get(i).get(p.groups.get(i).size() - 1).suit,
								currentGroups, groupPanel);
						CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
					} else {
						if (p.groups.get(i).get(p.groups.get(i).size() - 1).value == 52) {
							CreateNumberForCard(10 + (63 * i), 30, 52, -1, currentGroups, groupPanel, true);
							CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
						} else {
							CreateNumberForCard(10 + (63 * i), 30, 53, -1, currentGroups, groupPanel, true);
							CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
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
				groupCount.setSize(15, 15);
				groupCount.setLocation(26 + (63 * i), 84);
				currentGroups.add(groupCount);
				groupPanel.add(groupCount);
				int n = Data.plannedGroups.get(i).size();
				int m = Data.playedGroups.get(i).size();
				if (n > 0) {
					if (Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value < 51) {
						CreateNumberForCard(10 + (63 * i), 6,
								Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit,
								Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value - 1,
								currentGroups, groupPanel, false);
						CreateSuitForCard(10 + (63 * i), 42,
								Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit, currentGroups,
								groupPanel);
						CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
					} else {
						if (Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value == 52) {
							CreateNumberForCard(10 + (63 * i), 30, 52, -1, currentGroups, groupPanel, true);
							CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
						} else {
							CreateNumberForCard(10 + (63 * i), 30, 53, -1, currentGroups, groupPanel, true);
							CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
						}
					}
				} else if (m > 0) {
					if (Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value < 51) {
						CreateNumberForCard(10 + (63 * i), 6,
								Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit,
								Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value - 1,
								currentGroups, groupPanel, false);
						CreateSuitForCard(10 + (63 * i), 42,
								Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit, currentGroups,
								groupPanel);
						CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
					} else {
						if (Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value == 52) {
							CreateNumberForCard(10 + (63 * i), 30, 52, -1, currentGroups, groupPanel, true);
							CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
						} else {
							CreateNumberForCard(10 + (63 * i), 30, 53, -1, currentGroups, groupPanel, true);
							CreateCardStock(6 + (63 * i), 12, currentGroups, groupPanel);
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
				JLabel currentTurnFrame = new JLabel();
				try {
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/PlayIndicator.png"));
					currentTurnFrame = new JLabel(new ImageIcon(image));
					currentTurnFrame.setSize(45, 45);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 1) {
					currentTurnFrame.setLocation(15, 147);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 2) {
					currentTurnFrame.setLocation(486, 0);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 3) {
					currentTurnFrame.setLocation(957, 147);
				}				
				currentPlayers.add(currentTurnFrame);
				panel.add(currentTurnFrame);
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					username.setLocation(0, 197);
				} else if (i == 2) {
					username.setLocation(460, 50);
				} else if (i == 3) {
					username.setLocation(931, 197);
				}
				currentPlayers.add(username);
				panel.add(username);
				if (Data.players.get((Data.userID + i) % Data.players.size()).foot) {
					JLabel footIndicator = new JLabel();
					try {
						image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherFootIndicator.png"));
						footIndicator = new JLabel(new ImageIcon(image));
						footIndicator.setSize(12, 17);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					if (i == 1) {
						footIndicator.setLocation(63, 150);
					} else if (i == 2) {
						footIndicator.setLocation(534, 3);
					} else if (i == 3) {
						footIndicator.setLocation(945, 150);
					}
					panel.add(footIndicator);
					currentPlayers.add(footIndicator);
				}

				JLabel cardCount = new JLabel();
				cardCount.setText("" + Data.players.get((Data.userID + i) % Data.players.size()).cards);
				cardCount.setSize(20, 25);
				if (i == 1) {
					cardCount.setLocation(63, 177);
				} else if (i == 2) {
					cardCount.setLocation(534, 30);
				} else if (i == 3) {
					cardCount.setLocation(937, 177);
				}
				panel.add(cardCount);
				currentPlayers.add(cardCount);

			} else if (Data.players.size() == 6) {
				JLabel currentTurnFrame = new JLabel();
				try {
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/PlayIndicator.png"));
					currentTurnFrame = new JLabel(new ImageIcon(image));
					currentTurnFrame.setSize(45, 45);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 1) {
					currentTurnFrame.setLocation(15, 207);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 2) {
					currentTurnFrame.setLocation(15, 87);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 3) {
					currentTurnFrame.setLocation(486, 0);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 4) {
					currentTurnFrame.setLocation(957, 87);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 5) {
					currentTurnFrame.setLocation(957, 207);
				}		
				currentPlayers.add(currentTurnFrame);
				panel.add(currentTurnFrame);
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					username.setLocation(0, 257);
				} else if (i == 2) {
					username.setLocation(0, 137);
				} else if (i == 3) {
					username.setLocation(460, 50);
				} else if (i == 4) {
					username.setLocation(931, 137);
				} else if (i == 5) {
					username.setLocation(931, 257);
				}
				currentPlayers.add(username);
				panel.add(username);
				if (Data.players.get((Data.userID + i) % Data.players.size()).foot) {
					JLabel footIndicator = new JLabel();
					try {
						image = ImageIO.read(new File("res/images/" + Data.Resolution + "/OtherFootIndicator.png"));
						footIndicator = new JLabel(new ImageIcon(image));
						footIndicator.setSize(12, 17);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					if (i == 1) {
						footIndicator.setLocation(63, 210);
					} else if (i == 2) {
						footIndicator.setLocation(63, 90);
					} else if (i == 3) {
						footIndicator.setLocation(534, 3);
					} else if (i == 4) {
						footIndicator.setLocation(945, 90);
					} else if (i == 5) {
						footIndicator.setLocation(945, 210);
					}
					panel.add(footIndicator);
					currentPlayers.add(footIndicator);
				}

				JLabel cardCount = new JLabel();
				cardCount.setText("" + Data.players.get((Data.userID + i) % Data.players.size()).cards);
				cardCount.setSize(20, 25);
				if (i == 1) {
					cardCount.setLocation(63, 237);
				} else if (i == 2) {
					cardCount.setLocation(63, 117);
				} else if (i == 3) {
					cardCount.setLocation(540, 30);
				} else if (i == 4) {
					cardCount.setLocation(937, 117);
				} else if (i == 5) {
					cardCount.setLocation(937, 237);
				}
				panel.add(cardCount);
				currentPlayers.add(cardCount);

			} else if (Data.players.size() == 8) {
				JLabel currentTurnFrame = new JLabel();
				try {
					image = ImageIO.read(new File("res/images/" + Data.Resolution + "/PlayIndicator.png"));
					currentTurnFrame = new JLabel(new ImageIcon(image));
					currentTurnFrame.setSize(45, 45);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 1) {
					currentTurnFrame.setLocation(15, 207);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 2) {
					currentTurnFrame.setLocation(15, 87);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 3) {
					currentTurnFrame.setLocation(15, 0);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 4) {
					currentTurnFrame.setLocation(486, 0);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 5) {
					currentTurnFrame.setLocation(957, 0);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 6) {
					currentTurnFrame.setLocation(957, 87);
				} else if ((Data.CurrentTurn-Data.userID+Data.players.size())%Data.players.size() == 7) {
					currentTurnFrame.setLocation(957, 207);
				}	
				currentPlayers.add(currentTurnFrame);
				panel.add(currentTurnFrame);
				JLabel username = new JLabel();
				username.setText(Data.players.get((Data.userID + i) % Data.players.size()).name);
				username.setSize(100, 15);
				username.setHorizontalAlignment(SwingConstants.CENTER);
				if (i == 1) {
					username.setLocation(0, 257);
				} else if (i == 2) {
					username.setLocation(0, 137);
				} else if (i == 3) {
					username.setLocation(0, 50);
				} else if (i == 4) {
					username.setLocation(460, 50);
				} else if (i == 5) {
					username.setLocation(931, 50);
				} else if (i == 6) {
					username.setLocation(931, 137);
				} else if (i == 7) {
					username.setLocation(931, 257);
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
						footIndicator.setLocation(63, 210);
					} else if (i == 2) {
						footIndicator.setLocation(63, 90);
					} else if (i == 3) {
						footIndicator.setLocation(63, 3);
					} else if (i == 4) {
						footIndicator.setLocation(540, 3);
					} else if (i == 5) {
						footIndicator.setLocation(945, 3);
					} else if (i == 6) {
						footIndicator.setLocation(945, 90);
					} else if (i == 7) {
						footIndicator.setLocation(945, 210);
					}
					panel.add(footIndicator);
					currentPlayers.add(footIndicator);
				}

				JLabel cardCount = new JLabel();
				cardCount.setText("" + Data.players.get((Data.userID + i) % Data.players.size()).cards);
				cardCount.setSize(20, 25);
				if (i == 1) {
					cardCount.setLocation(63, 237);
				} else if (i == 2) {
					cardCount.setLocation(63, 117);
				} else if (i == 3) {
					cardCount.setLocation(63, 30);
				} else if (i == 4) {
					cardCount.setLocation(540, 30);
				} else if (i == 5) {
					cardCount.setLocation(945, 30);
				} else if (i == 6) {
					cardCount.setLocation(937, 117);
				} else if (i == 7) {
					cardCount.setLocation(937, 237);
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
				num.setSize(36, 36);
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
				num.setSize(36, 36);
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
			suit.setSize(36, 36);
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
			stock.setSize(51, 72);
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
			discardConfirm.setVisible(true);
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
			discardConfirm.setVisible(false);
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
