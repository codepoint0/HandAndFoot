package guiObjs;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JLabel;

/***
 * A data class that acts as a connection between the GUI
 * and Thread0 (the thread doing the communication). Holds
 * all values that the GUI uses to paint.
 * 
 * @author Tyler K. Gordon and Joshua Speckman
 *
 */
public class Data {
	public static String username;
	public static int userID;
	public static int playOrder;
	public static ArrayList<Card> hand;
	public static ArrayList<Card> pile;
	public static ArrayList<ArrayList<Card>> playedGroups = new ArrayList<ArrayList<Card>>();
	public static ArrayList<ArrayList<Card>> plannedGroups = new ArrayList<ArrayList<Card>>();
	public static int handIndex = 0;
	public static int phase = 0;
	public static Card Queued;
	public static int QueuedIndex;
	public static boolean IsQueued;
	public static boolean discardQueued;
	public static boolean foot;
	public static ArrayList<Player> players;
	public static int selectedPlayer;
	public static int previousPhase;
	public static boolean[] dirty;
	public static boolean update;
	public static Window gameWindow;
	public static int pileNumber;
	public static Thread0 coms;
	public static Lobby l;
	public static Welcome welcome;
	public static String ip;
	public static String port;
	public static String playerNum;
	public static Socket client;
	public static boolean connected;
	public static int numOfPlayers;
	public static int Resolution;
	public static int selectedTeam;
	public static ArrayList<String> usernames;
	public static TeamSelect ts;
	public static boolean Original = false;
	public static ArrayList<Integer> scores;
	public static ArrayList<String> teamNames;
	public static ArrayList<JLabel> cardNums;
	public static int CurrentTurn;
	public static Card drew;
	public static boolean drewCard;
	public static boolean endGame;
	public static int selected;
	public static Card left;
	public static Card right;
	public static Logger logger;
	public static int code;
	
	/***
	 * Initializes Data that needs to be
	 * to start the program
	 */
	public static void InitData() {
		CurrentTurn = 0;
		client = new Socket();
		hand = new ArrayList<Card>();
		pile = new ArrayList<Card>();
		IsQueued = false;
		Queued = new Card(0, 0, 0);
		discardQueued = true;
		foot = true;
		players = new ArrayList<Player>();
		userID = 0;
		dirty = new boolean[11];
		connected = false;
		numOfPlayers = -1;
		selectedTeam = -1;
		usernames = new ArrayList<String>();
		scores = new ArrayList<Integer>();
		teamNames = new ArrayList<String>();
		drew = new Card(0, 0, 0);
		left = new Card(0, 0, 0);
		right = new Card(0, 0, 0);
		selected = 1;
		username = "";
		
		for(int i = 0; i < 11; i++) {
			ArrayList<Card> s = new ArrayList<Card>();
			ArrayList<Card> t = new ArrayList<Card>();
			
			playedGroups.add(s);
			plannedGroups.add(t);
		}		
		
	}
	
	/**
	 * Signifies to the gameWindow that it needs
	 * to be updated.
	 */
	public static void setUpdate() {
		gameWindow.ServerUpdate();
	}
	
	/***
	 * Determines if a book is dirty
	 * @param c the book in question
	 * @return if c is dirty or clean
	 */
	public static boolean DirtyBook(ArrayList<Card> c) {
		for(Card d : c) {
			if(d.value == 2 || d.value == 52 || d.value == 53) {
				return true;
			}
		}
		return false;
	}
}

/***
 * A class that keeps track of Card values
 * 
 * @author Tyler K. Gordon and Joshua Speckman
 *
 */
class Card{
	public int value;
	public int suit;
	public int deckID;
	
	public Card(int v, int s, int d) {
		value = v;
		suit = s;
		deckID = d;
	}
	
	public Card(Card c) {
		value = c.value;
		suit = c.suit;
		deckID = c.deckID;
	}
	
	
}

/***
 * A Player class to keep track of any player values,
 * groups and foot indicators.
 * 
 * @author Tyler K. Gordon and Joshua Speckman
 *
 */
class Player {
	public String name;
	
	// Displays the # of cards a player has
	public int cards;
	
	// Groups that the player has
	public ArrayList<ArrayList<Card>> groups;
	
	// Signifies if the player still has their foot
	public boolean foot;
	
	public Player() {
		name = "";
		groups = new ArrayList<ArrayList<Card>>();
		for(int i = 0; i < 11; i++) {
			groups.add(new ArrayList<Card>());
		}
		
	}
	
	public Player(String n, boolean f, int c) {
		name = n;
		foot = f;
		cards = c;
		groups = new ArrayList<ArrayList<Card>>();
		for(int i = 0; i < 11; i++) {
			ArrayList<Card> s = new ArrayList<Card>();
			if(i == 0) {
				s.add(new Card(1, 0, 0));
			}
			if(i == 2) {
				s.add(new Card(5, 0, 0));
				s.add(new Card(5, 1, 0));
			}
			if(i == 10) {
				s.add(new Card(13, 0, 0));
			}
			
			groups.add(s);
		}
	}
	
	public Player(String n, int c, ArrayList<ArrayList<Card>> g, boolean f) {
		name = n;
		cards = c;
		groups = g;
		foot = f;
	}
}