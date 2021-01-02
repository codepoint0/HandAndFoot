package guiObjs;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
	public static Window w;
	public static int pileNumber;
	public static Thread0 th;
	public static Lobby l;
	public static Welcome s;
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
		
		for(int i = 0; i < 11; i++) {
			ArrayList<Card> s = new ArrayList<Card>();
			ArrayList<Card> t = new ArrayList<Card>();
			
			playedGroups.add(s);
			plannedGroups.add(t);
		}		
		
	}
	
	public static void setUpdate() {
		w.ServerUpdate();
	}
	
	public static boolean DirtyBook(ArrayList<Card> c) {
		for(Card d : c) {
			if(d.value == 2 || d.value == 52 || d.value == 53) {
				return true;
			}
		}
		return false;
	}
}

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


class Player {
	public String name;
	public int cards;
	public ArrayList<ArrayList<Card>> groups;
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