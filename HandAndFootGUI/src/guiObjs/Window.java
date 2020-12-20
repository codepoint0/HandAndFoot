package guiObjs;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Window {
	
	CardOrganizer c = new CardOrganizer();
	
	BufferedImage image;
	
	// FRAMES
	protected static JFrame frame;
	protected static JPanel panel;
	
	
	// BUTTONS
	protected static JButton button1;
	protected static JButton button2;
	JButton play;
	JButton discard;
	JButton pile;
	JButton submit;
	JButton cancel;
	JButton draw;
	
	JButton[] panelButtons = new JButton[12];
	JButton[] groupButtons = new JButton[10];
	
	JLabel cardPanel;
	JLabel groupPanel;
	JLabel discardBorder;
	JLabel discardPile;
	
	int[] ValidPlays = {1,4,5,6,7,8,9,10,11,12,13};	
	String[][] pictureLocations = new String[4][13];
	String[] suitLocations = {"res/images/Club.png", "res/images/Heart.png", 
			"res/images/Diamond.png", "res/images/Spade.png"};
	
	ArrayList<JLabel> currentCards;
	ArrayList<JLabel> currentGroups;
	ArrayList<JLabel> currentPile;
	
    public void CreateWindow(){
    	
    	Data.InitData();
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1700, 950);
		panel.setBackground(new Color(153,221,255));
		
		CreatePanelButtons();
		
    	try {
    	    image = ImageIO.read(new File("res/images/Wreath.png"));
    	    JLabel wreath = new JLabel(new ImageIcon(image));
    		wreath.setSize(1000, 400);
    		wreath.setLocation(350, 90);
    	    panel.add(wreath);
    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	}
		
		
    	try {
    	    image = ImageIO.read(new File("res/images/CardPanel.png"));
    	    cardPanel = new JLabel(new ImageIcon(image));
    	    cardPanel.setSize(1500, 210);
    	    cardPanel.setLocation(100, 690);
    	    panel.add(cardPanel);
    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	}
    	
    	try {
    	    image = ImageIO.read(new File("res/images/GroupPanel.png"));
    	    groupPanel = new JLabel(new ImageIcon(image));
    	    groupPanel.setSize(1150, 140);
    	    groupPanel.setLocation(275, 495);
    	    panel.add(groupPanel);
    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	}
    	
    	try {
    	    image = ImageIO.read(new File("res/images/Discard.png"));
    	    discardBorder = new JLabel(new ImageIcon(image));
    	    discardBorder.setSize(1700, 900);
    	    discardBorder.setLocation(0, 0);
    	    discardBorder.setVisible(false);
    	    panel.add(discardBorder);
    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	}
    	
       	try {
    	    image = ImageIO.read(new File("res/images/DiscardPile.png"));
    	    discardPile = new JLabel(new ImageIcon(image));
    	    discardPile.setSize(85, 120);
    	    discardPile.setLocation(802, 200);
    	    panel.add(discardPile);
    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	}
    	
    	for(int i = 0; i < 13; i++) {
    		pictureLocations[0][i] = "res/images/Black" + (i+1) + ".png";
    		pictureLocations[3][i] = "res/images/Black" + (i+1) + ".png";
    		pictureLocations[1][i] = "res/images/Red" + (i+1) + ".png";
    		pictureLocations[2][i] = "res/images/Red" + (i+1) + ".png";
    	}
    	
    	
		ServerUpdate();
    	// frame Code
		frame = new JFrame("My First GUI");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1700, 950);
		frame.add(panel);
		//frame.pack();
		frame.setVisible(true);
		frame.validate();
  }


	private void CreatePanelButtons() {
		play = new JButton();
		play.setText("Play");
		play.setSize(100,30);
		play.setLocation(480, 653);
		play.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { 
	        	if(Data.phase == 2) {
	        		play.setEnabled(false);
	        		discard.setEnabled(false);
	        		play.setVisible(false);
	        		discard.setVisible(false);
	        		submit.setEnabled(true);
	        		cancel.setEnabled(true);
		        	submit.setVisible(true);
		        	cancel.setVisible(true);
		        	Data.phase = 3;
	        	}
	        }
	    });
		panel.add(play);
		
		
		draw = new JButton();
		draw.setText("Draw");
		draw.setSize(100,30);
		draw.setLocation(790, 415);
		draw.setVisible(true);
		draw.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { 
	        	if(Data.phase == 1) {
		        	draw.setEnabled(false);
		        	draw.setVisible(false);
		        	Draw();
		        	Data.phase = 2;
	        	}
	        }
	    });
		panel.add(draw);
		
		discard = new JButton();
		discard.setText("Discard");
		discard.setSize(100,30);
		discard.setLocation(1120, 653);
		discard.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { 
	        	if(Data.phase == 2) {
	        		play.setEnabled(true);
	        		discard.setEnabled(true);
	        		play.setVisible(true);
	        		discard.setVisible(true);
	        		submit.setEnabled(false);
	        		cancel.setEnabled(false);
		        	submit.setVisible(false);
		        	cancel.setVisible(false);
		        	discardBorder.setVisible(true);
		        	Data.phase = 4;
	        	}
	        	else if(Data.phase == 4 && Data.discardQueued) {
	        		Data.discardQueued = false;
	        		Data.QueuedIndex = -1;
	        		Data.Queued = new Card(0, 0, 0);
	        		Data.IsQueued = false;
	        		discardBorder.setVisible(false);
	        		Data.phase = 0;
	        	}
	        }
	    });
		panel.add(discard);
		
		submit = new JButton();
		submit.setText("Submit");
		submit.setSize(100,30);
		submit.setLocation(480, 653);
		submit.setVisible(false);
		submit.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) {
	        	if(Data.phase == 3) {
	        		submit();
	        		play.setEnabled(true);
	        		discard.setEnabled(true);
	        		play.setVisible(true);
	        		discard.setVisible(true);
	        		submit.setEnabled(false);
	        		cancel.setEnabled(false);
		        	submit.setVisible(false);
		        	cancel.setVisible(false);
		        	Data.phase = 2;
	        	}
	        }
	    });
		panel.add(submit);
		
		
		cancel = new JButton();
		cancel.setText("Cancel");
		cancel.setSize(100,30);
		cancel.setLocation(1120, 653);
		cancel.setVisible(false);
		cancel.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) {
	        	if(Data.phase == 3) {
	        		cancel();
	        		play.setEnabled(true);
	        		discard.setEnabled(true);
	        		play.setVisible(true);
	        		discard.setVisible(true);
	        		submit.setEnabled(false);
	        		cancel.setEnabled(false);
		        	submit.setVisible(false);
		        	cancel.setVisible(false);
		        	Data.phase = 2;
	        	}
	        }
	    });
		panel.add(cancel);
		
		
		currentCards = new ArrayList<JLabel>();
		currentGroups = new ArrayList<JLabel>();
		currentPile = new ArrayList<JLabel>();
    	
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
	          if(me.getButton() == MouseEvent.BUTTON1) {
	        	  	 pile.setOpaque(true);
	        	  	pile.setContentAreaFilled(true);
	        	  	pile.setBorderPainted(true);
		          }
	        }
	        public void mouseReleased(MouseEvent me) { 
	          if(me.getButton() == MouseEvent.BUTTON1) {
	        	  pile.setOpaque(false);
	        	  pile.setContentAreaFilled(false);
	        	  pile.setBorderPainted(false);
		          }
	        }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) {
	        	draw.setVisible(false);
	        	PilePress();
	        }
	    });
		
		panel.add(pile);
		
		
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
	          if(me.getButton() == MouseEvent.BUTTON1) {
		        	  leftButton.setOpaque(true);
		        	  leftButton.setContentAreaFilled(true);
		        	  leftButton.setBorderPainted(true);
		          }
	        }
	        public void mouseReleased(MouseEvent me) { 
	          if(me.getButton() == MouseEvent.BUTTON1) {
		        	  leftButton.setOpaque(false);
		        	  leftButton.setContentAreaFilled(false);
		        	  leftButton.setBorderPainted(false);
		          }
	        }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { 
	        	Data.handIndex = Math.max(Data.handIndex - 10, 0);
	        	DrawHand();
	        	panel.repaint();
	        }
	    });
		panelButtons[0] = leftButton;
		panel.add(leftButton);
		for(int i = 1; i < 11; i++) {
			JButton jButton = new JButton();
			jButton.setName(""+i);
			jButton.setSize(150, 210);
			jButton.setLocation((150*i)-50, 690);
			jButton.setOpaque(false);
			jButton.setContentAreaFilled(false);
			jButton.setBorderPainted(false);
			jButton.addMouseListener(new MouseListener() {
		        public void mousePressed(MouseEvent me) { 
		          if(me.getButton() == MouseEvent.BUTTON1) {
			  			jButton.setOpaque(true);
						jButton.setContentAreaFilled(true);
						jButton.setBorderPainted(true);
			          }
		        }
		        public void mouseReleased(MouseEvent me) { 
		          if(me.getButton() == MouseEvent.BUTTON1) {
			  			jButton.setOpaque(false);
						jButton.setContentAreaFilled(false);
						jButton.setBorderPainted(false);
			          }
		        }
		        public void mouseEntered(MouseEvent me) { }
		        public void mouseExited(MouseEvent me) { }
		        public void mouseClicked(MouseEvent me) { 
		        	CardPress(Integer.parseInt(jButton.getName()));
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
	          if(me.getButton() == MouseEvent.BUTTON1) {
	        	  	rightButton.setOpaque(true);
	        	  	rightButton.setContentAreaFilled(true);
	        	  	rightButton.setBorderPainted(true);
		          }
	        }
	        public void mouseReleased(MouseEvent me) { 
	          if(me.getButton() == MouseEvent.BUTTON1) {
	        	  	rightButton.setOpaque(false);
	        	  	rightButton.setContentAreaFilled(false);
	        	  	rightButton.setBorderPainted(false);
		          }
	        }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) {
	        	Data.handIndex = Math.min(Data.handIndex + 10, Data.hand.size() - 10);
	        	DrawHand();
	        	panel.repaint();
	        }
	    });
		panelButtons[11] = rightButton;
		panel.add(rightButton);
		
		
		for(int i = 0; i < 11; i++) {
			JButton groupButton = new JButton();
			groupButton.setName(""+i);
			groupButton.setSize(85, 120);
			groupButton.setLocation(285+(105*i), 505);
			groupButton.setOpaque(false);
			groupButton.setContentAreaFilled(false);
			groupButton.setBorderPainted(false);
			groupButton.addMouseListener(new MouseListener() {
		        public void mousePressed(MouseEvent me) { 
		          if(me.getButton() == MouseEvent.BUTTON1) {
			        	  groupButton.setOpaque(true);
			        	  groupButton.setContentAreaFilled(true);
			        	  groupButton.setBorderPainted(true);
			          }
		        }
		        public void mouseReleased(MouseEvent me) { 
		          if(me.getButton() == MouseEvent.BUTTON1) {
			        	  groupButton.setOpaque(false);
			        	  groupButton.setContentAreaFilled(false);
			        	  groupButton.setBorderPainted(false);
			          }
		        }
		        public void mouseEntered(MouseEvent me) { }
		        public void mouseExited(MouseEvent me) { }
		        public void mouseClicked(MouseEvent me) { 
		        	GroupPress(Integer.parseInt(groupButton.getName()));
		        }
		    });
			
			panel.add(groupButton);
		}
	}
    
    
    public void ServerUpdate() {
    	DrawHand();
    	DrawPile();
    	DrawGroups();
    	DrawPlayers();
    	panel.repaint();
    }
    
    public void DrawHand() {
    	for(JLabel j : currentCards){
    		cardPanel.remove(j);
    	}
    	currentCards.clear();
    	
    	Data.hand.sort(c);
    	
    	for(int i = Data.handIndex; i < Math.min(Data.handIndex+10, Data.hand.size()); i++) {
    		if(Data.hand.get(i).suit != -1) {
    			CreateNumberForCard(45+(150*(i-Data.handIndex)), 30, 
    					Data.hand.get(i).suit, 
    					Data.hand.get(i).value - 1, 
    					currentCards, cardPanel, false);
    			CreateSuitForCard(45+(150*(i-Data.handIndex)), 120, Data.hand.get(i).suit, currentCards, cardPanel);
    		}
    		else {
    			if(Data.hand.get(i).value == 52) {
        			CreateNumberForCard(45+(150*(i-Data.handIndex)), 75, 52, - 1, currentCards, cardPanel, true);
    			}
    			else {
    				CreateNumberForCard(45+(150*(i-Data.handIndex)), 75, 53, - 1, currentCards, cardPanel, true);
    			}
    		}
    	}
    }
    
    public void DrawPile() {
    	for(JLabel j : currentPile){
    		discardPile.remove(j);
    	}
    	currentPile.clear();
    	if(Data.pile.size() > 0) {
    		if(Data.pile.get(Data.pile.size() - 1).value < 52) {
        		CreateNumberForCard(7, 0, Data.pile.get(Data.pile.size() - 1).suit, Data.pile.get(Data.pile.size() - 1).value - 1, currentPile, discardPile, false);
        		CreateSuitForCard(7, 60, Data.pile.get(Data.pile.size() - 1).suit, currentPile, discardPile);
            	CreateCardStock(0, 0, currentPile, discardPile);
    		}
    		else if(Data.pile.get(Data.pile.size() - 1).value == 52) {
    			CreateNumberForCard(7, 30, 52, -1, currentPile, discardPile, true);
            	CreateCardStock(0, 0, currentPile, discardPile);
    		}
    		else {
    			CreateNumberForCard(7, 30, 53, -1, currentPile, discardPile, true);
            	CreateCardStock(0, 0, currentPile, discardPile);
    		}
    	}
	}
    
    public void DrawGroups() {
    	for(JLabel j : currentGroups){
    		groupPanel.remove(j);
    	}
    	currentGroups.clear();
    	
    	for(int i = 0; i < 11; i++) {
    		Data.playedGroups.get(i).sort(c);
    	}
    	
    	for(int i = 0; i < 11; i++) {
    		int n = Data.plannedGroups.get(i).size();
    		int m = Data.playedGroups.get(i).size();
    		if(n > 0) {
    			if(Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size()-1).value < 51) {
    				CreateNumberForCard(17+(105*i), 0, 
    						Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit, 
    						Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value - 1, 
    						currentGroups, groupPanel, false);
    				CreateSuitForCard(17+(105*i), 60, Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit, currentGroups, groupPanel);
	    	    	CreateCardStock(10+(105*i), 10, currentGroups, groupPanel);
    			}
    			else {
    				if(Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size()-1).value == 52) {
        				CreateNumberForCard(17+(105*i), 40, 52, -1, currentGroups, groupPanel, true);
            	    	CreateCardStock(10+(105*i), 10, currentGroups, groupPanel);
        			}
        			else {
        				CreateNumberForCard(17+(105*i), 40, 53, -1, currentGroups, groupPanel, true);
    	    	    	CreateCardStock(10+(105*i), 10, currentGroups, groupPanel);
        			}
    			}
    		}
    		else if(m > 0) {
    			if(Data.playedGroups.get(i).get(Data.playedGroups.get(i).size()-1).value < 51) {
    				CreateNumberForCard(17+(105*i), 0, 
    						Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit, 
    						Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value - 1, 
    						currentGroups, groupPanel, false);
    				CreateSuitForCard(17+(105*i), 60, Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit, currentGroups, groupPanel);
	    	    	CreateCardStock(10+(105*i), 10, currentGroups, groupPanel);
    			}
    			else {
    				if(Data.playedGroups.get(i).get(Data.playedGroups.get(i).size()-1).value == 52) {
    					CreateNumberForCard(17+(105*i), 40, 52, -1, currentGroups, groupPanel, true);
            	    	CreateCardStock(10+(105*i), 10, currentGroups, groupPanel);
        			}
        			else {
        				CreateNumberForCard(17+(105*i), 40, 53, -1, currentGroups, groupPanel, true);
    	    	    	CreateCardStock(10+(105*i), 10, currentGroups, groupPanel);
        			}
    			}
    			
    		}
    	}	
    }
    
    public void DrawPlayers() {
    	
    }
    
	private void CreateNumberForCard(int x, int y, int locationInFirstArray, int locationInSecondArray, ArrayList<JLabel> deleteFrom, JLabel labelToAddTo, boolean Joker) {
		if(Joker) {
			JLabel num = new JLabel();
			String RorB;
			if(locationInFirstArray == 52) {
				RorB = "Black";
			}
			else {
				RorB = "Red";
			}
	    	try {
	    	    image = ImageIO.read(new File("res/images/"+ RorB + locationInFirstArray +".png"));
	    	    num = new JLabel(new ImageIcon(image));
	    	    num.setSize(60, 60);
	    	    num.setLocation(x, y);
	    	    deleteFrom.add(num);
	    	    labelToAddTo.add(num);
	    	} catch (IOException ex) {
	    	    ex.printStackTrace();
	    	}
		}
		else {
			JLabel num = new JLabel();
			try {
			    image = ImageIO.read(new File(
			    		pictureLocations[locationInFirstArray][locationInSecondArray]));
			    num = new JLabel(new ImageIcon(image));
			    num.setSize(60, 60);
			    num.setLocation(x,y);
			    deleteFrom.add(num);
			    labelToAddTo.add(num);
			} catch (IOException ex) {
			    ex.printStackTrace();
			}
		}
	}
	
	private void CreateSuitForCard(int x, int y, int locationInArray, ArrayList<JLabel> deleteFrom, JLabel labelToAddTo) {
		JLabel suit = new JLabel();
    	try {
    	    image = ImageIO.read(new File(
    	    		suitLocations
    	    		[locationInArray]));
    	    suit = new JLabel(new ImageIcon(image));
    	    suit.setSize(60, 60);
    	    suit.setLocation(x, y);
    	    deleteFrom.add(suit);
    	    labelToAddTo.add(suit);
    	} catch (IOException ex) {
    	    ex.printStackTrace();
    	}
	}
    
	private void CreateCardStock(int x, int y, ArrayList<JLabel> deleteFrom, JLabel labelToAddTo) {
		JLabel stock = new JLabel();
		try {
		    image = ImageIO.read(new File("res/images/CardStock.png"));
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
    	if(Data.phase == 3) {
    		Data.IsQueued = true;
    		Data.Queued = Data.hand.get(i+Data.handIndex-1);
    		Data.QueuedIndex = i+Data.handIndex-1;
    	}
    	else if(Data.phase == 4) {
    		if(Data.IsQueued) {
    			Data.Queued = Data.hand.get(i+Data.handIndex-1);
        		Data.QueuedIndex = i+Data.handIndex-1;
    		}
    		else {
        		Data.IsQueued = true;
        		Data.Queued = Data.hand.get(i+Data.handIndex-1);
        		Data.QueuedIndex = i+Data.handIndex-1;
    		}
    	}
    }
    
    public void GroupPress(int i) {
    	if(Data.phase == 3 && Data.IsQueued) {
    		if(Data.Queued.value == ValidPlays[i] || Data.Queued.value == 2 || Data.Queued.value == 52 || Data.Queued.value == 53) {
        		Card c = new Card(Data.Queued);
        		
        		Data.plannedGroups.get(i).add(c);
        		
        		Data.hand.remove(Data.QueuedIndex);
        		Data.handIndex = Math.min(Data.handIndex, Data.hand.size() - 10);
        		DrawHand();
        		DrawGroups();
        		panel.repaint();
    		}
    		Data.QueuedIndex = -1;
    		Data.IsQueued = false;
    	}
    	else if(Data.phase == 3 && !Data.IsQueued) {
    		int n = Data.plannedGroups.get(i).size();
    		if(n > 0) {
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
    	if(Data.phase == 1) {
    		Data.hand.addAll(Data.pile);
    		Data.pile.clear();
    		Data.phase = 2;
    		DrawHand();
    		DrawPile();
    		panel.repaint();
    	}
    	else if(Data.phase == 4 && Data.IsQueued) {
    		if(Data.discardQueued) {
        		Data.hand.add(Data.pile.get(Data.pile.size() - 1));
        		Data.pile.remove(Data.pile.size() - 1);
    		}
    		Card c = new Card(Data.Queued);
    		Data.pile.add(c);
    		Data.hand.remove(Data.QueuedIndex);
    		Data.QueuedIndex = -1;
    		Data.IsQueued = false;
    		Data.discardQueued = true;
    		DrawHand();
    		DrawPile();
    		panel.repaint();
    	}
    	else if(Data.phase == 4 && Data.discardQueued) {
    		Data.hand.add(Data.pile.get(Data.pile.size() - 1));
    		Data.pile.remove(Data.pile.size() - 1);
    		Data.QueuedIndex = -1;
    		Data.IsQueued = false;
    		Data.discardQueued = false;
    		DrawHand();
    		DrawPile();
    		panel.repaint();
    	}
    }
    
    
    public void submit() {
    	for(int i = 0; i < 11; i++) {
    		Data.playedGroups.get(i).addAll(Data.plannedGroups.get(i));
    		Data.plannedGroups.get(i).clear();
    	}
    	DrawGroups();
    	panel.repaint();
    }
    
    public void cancel() {
    	for(int i = 0; i < 11; i++) {
    		Data.hand.addAll(Data.plannedGroups.get(i));
    		Data.plannedGroups.get(i).clear();
    	}
    	
    	DrawGroups();
    	DrawHand();
    	panel.repaint();
    }
    
    
    public void Draw() {
    	Data.hand.add(new Card(2, 0, 0));
    	DrawHand();
    	panel.repaint();
    }
    
}

class CardOrganizer implements Comparator<Card>{
	int[] order = {0, 3, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	@Override
	public int compare(Card a, Card b) {
		if(a.value > 51) {
			if(b.value > 51) {
				if(a.value == b.value) {
					return a.deckID - b.deckID;
				}
				return a.value - b.value;
			}
			return -1;
		}
		
		if(b.value > 51) {
			return 1;
		}
		
		if(a.value == b.value) {
			if(a.suit == b.suit) {
				return a.deckID - b.deckID;
			}
			else {
				return a.suit - b.suit;
			}
		}
		
		return order[a.value] - order[b.value];
	}
	
}
