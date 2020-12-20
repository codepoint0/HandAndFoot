package guiObjs;

import java.util.ArrayList;
import java.util.List;

public class Data {
	public static ArrayList<Card> hand;
	public static ArrayList<ArrayList<Card>> playedGroups = new ArrayList<ArrayList<Card>>();
	public static ArrayList<ArrayList<Card>> plannedGroups = new ArrayList<ArrayList<Card>>();
	public static int handIndex = 0;
	public static int phase = 3;
	public static Card Queued;
	public static int QueuedIndex;
	public static boolean IsQueued;
	
	public static void InitData() {
		hand = new ArrayList<Card>();
		IsQueued = false;
		Queued = new Card(0, 0, 0);
		
		for(int i = 0; i < 11; i++) {
			ArrayList<Card> s = new ArrayList<Card>();
			ArrayList<Card> t = new ArrayList<Card>();
			
			playedGroups.add(s);
			plannedGroups.add(t);
		}
		
		
		
		// Test hand
		hand.add(new Card(3,0,0));
		hand.add(new Card(4,3,0));
		hand.add(new Card(5,0,0));
		hand.add(new Card(6,3,0));
		hand.add(new Card(6,2,0));
		hand.add(new Card(7,3,0));
		hand.add(new Card(7,1,0));
		hand.add(new Card(9,2,0));
		hand.add(new Card(10,1,0));
		hand.add(new Card(12,2,0));
		hand.add(new Card(12,3,0));
		hand.add(new Card(12,1,0));
		hand.add(new Card(3,0,0));
		hand.add(new Card(4,3,0));
		hand.add(new Card(5,0,0));
		hand.add(new Card(6,3,0));
		hand.add(new Card(6,2,0));
		hand.add(new Card(7,3,0));
		hand.add(new Card(7,1,0));
		hand.add(new Card(9,2,0));
		hand.add(new Card(10,1,0));
		hand.add(new Card(12,2,0));
		hand.add(new Card(12,3,0));
		hand.add(new Card(52,-1,0));
		hand.add(new Card(12,1,0));
		
		
		
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