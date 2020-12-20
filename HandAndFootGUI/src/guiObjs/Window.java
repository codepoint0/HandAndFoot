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
	
	// FRAMES
	protected static JFrame frame;
	protected static JPanel panel;
	
	
	// BUTTONS
	protected static JButton button1;
	protected static JButton button2;
	
	JButton play;
	JButton discard;
	
	JButton[] panelButtons = new JButton[12];
	JButton[] groupButtons = new JButton[10];
	
	BufferedImage image;
	JLabel cardPanel;
	JLabel groupPanel;
	
	int[] ValidPlays = {1,4,5,6,7,8,9,10,11,12,13};
	
	String[][] pictureLocations = new String[4][13];
	String[] suitLocations = {"res/images/Club.png", "res/images/Heart.png", 
			"res/images/Diamond.png", "res/images/Spade.png"};
	
	ArrayList<JLabel> currentCards;
	ArrayList<JLabel> currentGroups;
	
    public void CreateWindow(){
    	
    	Data.InitData();
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(1700, 950);
		panel.setBackground(new Color(106, 190, 48));
		
		play = new JButton();
		play.setText("Play");
		play.setSize(100,30);
		play.setLocation(480, 653);
		play.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { }
	    });
		panel.add(play);
		
		discard = new JButton();
		discard.setText("Discard");
		discard.setSize(100,30);
		discard.setLocation(1120, 653);
		discard.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { }
	    });
		panel.add(discard);
		
		
		currentCards = new ArrayList<JLabel>();
		currentGroups = new ArrayList<JLabel>();
		
		// panelButtons
		JButton leftButton = new JButton();
		leftButton.setName("Left");
		leftButton.setSize(100, 210);
		leftButton.setLocation(0, 690);
		leftButton.setOpaque(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setBorderPainted(false);
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
    			
    			JLabel num = new JLabel();
    	    	try {
    	    	    image = ImageIO.read(new File(
    	    	    		pictureLocations[Data.hand.get(i).suit][Data.hand.get(i).value - 1]));
    	    	    num = new JLabel(new ImageIcon(image));
    	    	    num.setSize(60, 60);
    	    	    num.setLocation(45+(150*(i-Data.handIndex)), 30);
    	    	    cardPanel.add(num);
    	    	    currentCards.add(num);
    	    	} catch (IOException ex) {
    	    	    ex.printStackTrace();
    	    	}
    			JLabel suit = new JLabel();
    	    	try {
    	    	    image = ImageIO.read(new File(
    	    	    		suitLocations[Data.hand.get(i).suit]));
    	    	    suit = new JLabel(new ImageIcon(image));
    	    	    suit.setSize(60, 60);
    	    	    suit.setLocation(45+(150*(i-Data.handIndex)), 120);
    	    	    cardPanel.add(suit);
    	    	    currentCards.add(suit);
    	    	} catch (IOException ex) {
    	    	    ex.printStackTrace();
    	    	}
    		}
    		else {
    			if(Data.hand.get(i).value == 52) {
        			JLabel num = new JLabel();
        	    	try {
        	    	    image = ImageIO.read(new File("res/images/Black52.png"));
        	    	    num = new JLabel(new ImageIcon(image));
        	    	    num.setSize(60, 60);
        	    	    num.setLocation(45+(150*(i-Data.handIndex)), 75);
        	    	    cardPanel.add(num);
        	    	    currentCards.add(num);
        	    	} catch (IOException ex) {
        	    	    ex.printStackTrace();
        	    	}
    			}
    			else {
    				JLabel num = new JLabel();
	    	    	try {
	    	    	    image = ImageIO.read(new File("res/images/Red53.png"));
	    	    	    num = new JLabel(new ImageIcon(image));
	    	    	    num.setSize(60, 60);
	    	    	    num.setLocation(45+(150*(i-Data.handIndex)), 75);
	    	    	    cardPanel.add(num);
	    	    	    currentCards.add(num);
	    	    	} catch (IOException ex) {
	    	    	    ex.printStackTrace();
	    	    	}
    			}
    		}
    	}
    }
    
    public void DrawPile() {
    	
    }
    
    public void DrawGroups() {
    	for(JLabel j : currentGroups){
    		groupPanel.remove(j);
    	}
    	currentGroups.clear();
    	
    	for(int i = 0; i < 11; i++) {
    		int n = Data.plannedGroups.get(i).size();
    		int m = Data.playedGroups.get(i).size();
    		if(n > 0) {
    			if(Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size()-1).value < 51) {
	    			JLabel num = new JLabel();
	    	    	try {
	    	    	    image = ImageIO.read(new File(
	    	    	    		pictureLocations
	    	    	    		[Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit]
	    	    	    				[Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).value - 1]));
	    	    	    num = new JLabel(new ImageIcon(image));
	    	    	    num.setSize(60, 60);
	    	    	    num.setLocation(17+(105*i), 0);
	    	    	    groupPanel.add(num);
	    	    	    currentGroups.add(num);
	    	    	} catch (IOException ex) {
	    	    	    ex.printStackTrace();
	    	    	}
	    			JLabel suit = new JLabel();
	    	    	try {
	    	    	    image = ImageIO.read(new File(
	    	    	    		suitLocations
	    	    	    		[Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size() - 1).suit]));
	    	    	    suit = new JLabel(new ImageIcon(image));
	    	    	    suit.setSize(60, 60);
	    	    	    suit.setLocation(17+(105*i), 60);
	    	    	    groupPanel.add(suit);
	    	    	    currentGroups.add(suit);
	    	    	} catch (IOException ex) {
	    	    	    ex.printStackTrace();
	    	    	}
	    			JLabel stock = new JLabel();
	    	    	try {
	    	    	    image = ImageIO.read(new File("res/images/CardStock.png"));
	    	    	    stock = new JLabel(new ImageIcon(image));
	    	    	    stock.setSize(85, 120);
	    	    	    stock.setLocation(10+(105*i), 10);
	    	    	    groupPanel.add(stock);
	    	    	    currentGroups.add(stock);
	    	    	} catch (IOException ex) {
	    	    	    ex.printStackTrace();
	    	    	}
    			}
    			else {
    				if(Data.plannedGroups.get(i).get(Data.plannedGroups.get(i).size()-1).value == 52) {
            			JLabel num = new JLabel();
            	    	try {
            	    	    image = ImageIO.read(new File("res/images/Black52.png"));
            	    	    num = new JLabel(new ImageIcon(image));
            	    	    num.setSize(60, 60);
            	    	    num.setLocation(17+(105*i), 40);
            	    	    groupPanel.add(num);
            	    	    currentGroups.add(num);
            	    	} catch (IOException ex) {
            	    	    ex.printStackTrace();
            	    	}
    	    			JLabel stock = new JLabel();
    	    	    	try {
    	    	    	    image = ImageIO.read(new File("res/images/CardStock.png"));
    	    	    	    stock = new JLabel(new ImageIcon(image));
    	    	    	    stock.setSize(85, 120);
    	    	    	    stock.setLocation(10+(105*i), 10);
    	    	    	    groupPanel.add(stock);
    	    	    	    currentGroups.add(stock);
    	    	    	} catch (IOException ex) {
    	    	    	    ex.printStackTrace();
    	    	    	}
        			}
        			else {
        				JLabel num = new JLabel();
    	    	    	try {
    	    	    	    image = ImageIO.read(new File("res/images/Red53.png"));
    	    	    	    num = new JLabel(new ImageIcon(image));
    	    	    	    num.setSize(60, 60);
    	    	    	    num.setLocation(17+(105*i), 40);
    	    	    	    groupPanel.add(num);
    	    	    	    currentGroups.add(num);
    	    	    	} catch (IOException ex) {
    	    	    	    ex.printStackTrace();
    	    	    	}
    	    			JLabel stock = new JLabel();
    	    	    	try {
    	    	    	    image = ImageIO.read(new File("res/images/CardStock.png"));
    	    	    	    stock = new JLabel(new ImageIcon(image));
    	    	    	    stock.setSize(85, 120);
    	    	    	    stock.setLocation(10+(105*i), 10);
    	    	    	    groupPanel.add(stock);
    	    	    	    currentGroups.add(stock);
    	    	    	} catch (IOException ex) {
    	    	    	    ex.printStackTrace();
    	    	    	}
        			}
    			}
    		}
    		else if(m > 0) {
    			if(Data.playedGroups.get(i).get(Data.playedGroups.get(i).size()-1).value < 51) {
	    			JLabel num = new JLabel();
	    	    	try {
	    	    	    image = ImageIO.read(new File(
	    	    	    		pictureLocations
	    	    	    		[Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit]
	    	    	    				[Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).value - 1]));
	    	    	    num = new JLabel(new ImageIcon(image));
	    	    	    num.setSize(60, 60);
	    	    	    num.setLocation(17+(105*i), 0);
	    	    	    groupPanel.add(num);
	    	    	    currentGroups.add(num);
	    	    	} catch (IOException ex) {
	    	    	    ex.printStackTrace();
	    	    	}
	    			JLabel suit = new JLabel();
	    	    	try {
	    	    	    image = ImageIO.read(new File(
	    	    	    		suitLocations
	    	    	    		[Data.playedGroups.get(i).get(Data.playedGroups.get(i).size() - 1).suit]));
	    	    	    suit = new JLabel(new ImageIcon(image));
	    	    	    suit.setSize(60, 60);
	    	    	    suit.setLocation(17+(105*i), 60);
	    	    	    groupPanel.add(suit);
	    	    	    currentGroups.add(suit);
	    	    	} catch (IOException ex) {
	    	    	    ex.printStackTrace();
	    	    	}
	    			JLabel stock = new JLabel();
	    	    	try {
	    	    	    image = ImageIO.read(new File("res/images/CardStock.png"));
	    	    	    stock = new JLabel(new ImageIcon(image));
	    	    	    stock.setSize(85, 120);
	    	    	    stock.setLocation(10+(105*i), 10);
	    	    	    groupPanel.add(stock);
	    	    	    currentGroups.add(stock);
	    	    	} catch (IOException ex) {
	    	    	    ex.printStackTrace();
	    	    	}
    			}
    			else {
    				if(Data.playedGroups.get(i).get(Data.playedGroups.get(i).size()-1).value == 52) {
            			JLabel num = new JLabel();
            	    	try {
            	    	    image = ImageIO.read(new File("res/images/Black52.png"));
            	    	    num = new JLabel(new ImageIcon(image));
            	    	    num.setSize(60, 60);
            	    	    num.setLocation(17+(105*i), 40);
            	    	    groupPanel.add(num);
            	    	    currentGroups.add(num);
            	    	} catch (IOException ex) {
            	    	    ex.printStackTrace();
            	    	}
    	    			JLabel stock = new JLabel();
    	    	    	try {
    	    	    	    image = ImageIO.read(new File("res/images/CardStock.png"));
    	    	    	    stock = new JLabel(new ImageIcon(image));
    	    	    	    stock.setSize(85, 120);
    	    	    	    stock.setLocation(10+(105*i), 10);
    	    	    	    groupPanel.add(stock);
    	    	    	    currentGroups.add(stock);
    	    	    	} catch (IOException ex) {
    	    	    	    ex.printStackTrace();
    	    	    	}
        			}
        			else {
        				JLabel num = new JLabel();
    	    	    	try {
    	    	    	    image = ImageIO.read(new File("res/images/Red53.png"));
    	    	    	    num = new JLabel(new ImageIcon(image));
    	    	    	    num.setSize(60, 60);
    	    	    	    num.setLocation(17+(105*i), 40);
    	    	    	    groupPanel.add(num);
    	    	    	    currentGroups.add(num);
    	    	    	} catch (IOException ex) {
    	    	    	    ex.printStackTrace();
    	    	    	}
    	    			JLabel stock = new JLabel();
    	    	    	try {
    	    	    	    image = ImageIO.read(new File("res/images/CardStock.png"));
    	    	    	    stock = new JLabel(new ImageIcon(image));
    	    	    	    stock.setSize(85, 120);
    	    	    	    stock.setLocation(10+(105*i), 10);
    	    	    	    groupPanel.add(stock);
    	    	    	    currentGroups.add(stock);
    	    	    	} catch (IOException ex) {
    	    	    	    ex.printStackTrace();
    	    	    	}
        			}
    			}
    			
    		}
    	}	
    }
    
    public void DrawPlayers() {
    	
    }
    
    public void CardPress(int i) {
    	if(Data.phase == 3) {
    		Data.IsQueued = true;
    		Data.Queued = Data.hand.get(i+Data.handIndex-1);
    		Data.QueuedIndex = i+Data.handIndex-1;
    	}
    }
    
    public void GroupPress(int i) {
    	if(Data.phase == 3 && Data.IsQueued) {
    		if(Data.Queued.value == ValidPlays[i] || Data.Queued.value == 2 || Data.Queued.value == 52 || Data.Queued.value == 53) {
        		Card c = new Card(Data.Queued);
        		
        		Data.plannedGroups.get(i).add(c);
        		
        		Data.hand.remove(Data.QueuedIndex);
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
