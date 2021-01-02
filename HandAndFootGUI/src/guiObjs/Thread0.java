package guiObjs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import guiObjs.Window1700;

public class Thread0 implements Runnable {
	byte[] b = new byte[4096];
	public Socket client;

	public static String[] pipeSplit(String s, char d) {
		ArrayList<String> t = new ArrayList<String>();
		String[] j;
		String toAdd = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == d) {
				t.add(toAdd);
				toAdd = "";
			} else {
				toAdd += s.charAt(i);
			}
		}
		t.add(toAdd);
		j = new String[t.size()];
		j = t.toArray(j);
		return j;
	}

	public Thread0(Socket client) {
		this.client = client;
	}

	public void SendMessage(String message) { 
		Data.logger.info("Sending: " + message);
		OutputStream outToServer;
		try {
			byte[] OutBuffer = new byte[4096];
			outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			OutBuffer = message.getBytes();
			System.out.println(message);
			out.write(OutBuffer);
			Data.logger.fine("Message was sent!");
			
		} catch (IOException e) {
			Data.logger.severe("FAILED TO SEND MESSAGE: " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (client.isConnected()) {
			try {
				InputStream inFromServer = client.getInputStream();
				DataInputStream in = new DataInputStream(inFromServer);
				b = new byte[4096];
				
				Data.logger.finest("Ready to read from SERVER");
				in.read(b);
				String message = new String(b);
				
				Data.logger.fine("Reading: " + message);
				if (message.substring(0, 3).equals("PS:")) {
					Data.logger.finest("Phase Switch");
					try {
						if (Data.phase != 5) {
							Data.phase = Integer.parseInt(message.substring(3, 4).trim());
						} else {
							Data.previousPhase = Integer.parseInt(message.substring(3, 4).trim());
						}
					}
					catch(Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
						System.out.println(e.toString());
					}
					
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				} 
				else if (message.substring(0, 3).equals("TC:")) {
					Data.logger.finest("Top Card");
					Data.pile.clear();
					String[] values = message.substring(3, message.length()).split(" ");
					Data.logger.finer("Parsed out top card: " + Arrays.toString(values));
					if (!values[0].equals("0")) {
						try {
							Data.logger.finer("Trying to create top card from given values");
							Data.pile.add(new Card(Integer.parseInt(values[0].trim()),
									Integer.parseInt(values[1].trim()), Integer.parseInt(values[2].trim())));
						} catch (Exception e) {
							Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
							System.out.println(e.toString());
						}

					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				} 
				else if (message.substring(0, 3).equals("PN:")) {
					Data.logger.finest("Pile Number");
					Data.pileNumber = Integer.parseInt(message.substring(3, message.length()).trim());
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				} 
				else if (message.substring(0, 3).equals("HC:")) {
					Data.logger.finest("Hand Count");
					try {
						Data.logger.finer("Trying to assign hand count to player!");
						Data.players.get(Integer.parseInt(message.substring(3, 4))).cards = Integer
								.parseInt(message.substring(5, message.length()).trim());
					}
					catch(Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				} 
				else if (message.substring(0, 3).equals("HF:")) {
					Data.logger.finest("Has Foot");
					if (message.charAt(5) == '1') {
						Data.logger.finer("Determined that player has foot");
						try {
							Data.players.get(Character.getNumericValue(message.charAt(3))).foot = true;
						} catch (Exception e) {
							Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
							System.out.println(e.toString());
						}

					} 
					else {
						Data.logger.finer("Determined that player DOES NOT HAVE foot");
						try {
							Data.players.get(Character.getNumericValue(message.charAt(3))).foot = false;
						} catch (Exception e) {
							Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
							System.out.println(e.toString());
						}
					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				} 
				else if (message.substring(0, 3).equals("EE:")) {
					Data.logger.warning("Error in sending groups? Something failed, either we wanted that or not... check!");
					for (int i = 0; i < Data.plannedGroups.size(); i++) {
						for (int j = 0; j < Data.plannedGroups.get(i).size(); j++) {
							Data.hand.add(Data.plannedGroups.get(i).get(j));
						}
						Data.plannedGroups.get(i).clear();
					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				} 
				else if (message.substring(0, 3).equals("PH:")) {
					Data.logger.finest("Player Hand");
					String[] cards = message.substring(3, message.length()).split(",");
					Data.hand.clear();
					Data.logger.finest("Cleared player's hand (client side)");
					for (String s : cards) {
						String[] cardValues = s.split(" ");
						Data.logger.finest("We parsed the card into " + s + " trying to put that as a card in the player's hand");
						try {
							Data.hand.add(new Card(Integer.parseInt(cardValues[0].trim()),
									Integer.parseInt(cardValues[1].trim()), Integer.parseInt(cardValues[2].trim())));
						} catch (Exception e) {
							Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
						}

					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				} 
				else if (message.substring(0, 3).equals("PG:")) {
					Data.logger.finest("Player Group");
					int playerID = Integer.parseInt(message.substring(3, 4).trim());
					if (Data.userID == playerID) {
						Data.logger.finer("The ID IS this player!");
						for (ArrayList<Card> c : Data.plannedGroups) {
							c.clear();
						}
						for (ArrayList<Card> c : Data.playedGroups) {
							c.clear();
						}
						String[] pipeSplit = pipeSplit(message.substring(5, message.length()), '|');
						Data.logger.info("Split the cards by group!");
						for (int i = 0; i < 11; i++) {
							String[] cards = pipeSplit[i].split(",");

							for (String s : cards) {
								String[] cardValues = s.split(" ");
								try {
									Data.playedGroups.get(i)
											.add(new Card(Integer.parseInt(cardValues[0].trim()),
													Integer.parseInt(cardValues[1].trim()),
													Integer.parseInt(cardValues[2].trim())));
								} catch (Exception e) {
									Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
								}
							}
						}
						Data.logger.finer("Message parsed, data gathered, sending back confirmation");
						SendMessage("~");
					} 
					else {
						Data.logger.finer("The ID IS NOT this player!");
						for (int i = 0; i < 11; i++) {
							Data.players.get(playerID).groups.get(i).clear();
						}
						String[] pipeSplit = pipeSplit(message.substring(5, message.length()), '|');
						Data.logger.info("Split the cards by group!");
						for (int i = 0; i < pipeSplit.length; i++) {
							String[] cards = pipeSplit[i].split(",");
							for (String s : cards) {
								String[] cardValues = s.split(" ");
								try {
									Data.players.get(playerID).groups.get(i)
											.add(new Card(Integer.parseInt(cardValues[0].trim()),
													Integer.parseInt(cardValues[1].trim()),
													Integer.parseInt(cardValues[2].trim())));
								} catch (Exception e) {
									Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
								}
							}
						}
						Data.logger.finer("Message parsed, data gathered, sending back confirmation");
						SendMessage("~");
					}
				} 
				else if (message.substring(0, 3).equals("SG:")) {
					Data.logger.finest("Start Game");
					try {
						Data.userID = Integer.parseInt(message.substring(3, 4).trim());
					} catch (Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
					}

					try {
						Data.logger.finest("Trying to parse num of players and then create those players");
						Data.numOfPlayers = Integer.parseInt(message.substring(5, 6).trim());
						for (int i = 0; i < Data.numOfPlayers; i++) {
							Data.players.add(new Player());
						}
						for(int i = 0; i < Data.numOfPlayers/2; i++) {
							Data.scores.add(0);
							Data.teamNames.add("");
						}
					} catch (Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
					}

					Data.ts.start();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage(Data.username);
				}
				else if (message.substring(0, 3).equals("UN:")) {
					Data.logger.finest("Got Username");
					int user = 0;
					try {
						user = Integer.parseInt(message.substring(3, 4).trim());
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					Data.logger.finer("Trying to set username to given user ID");
					Data.players.get(user).name = message.substring(5, message.length()-1);
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
				else if (message.substring(0, 3).equals("GU:")) {
					Data.logger.finest("Get Username");
					if(message.substring(0,4).equals("GU:0")) {
						Data.logger.finest("User is user 0");
						Data.Original = true;
					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage(Data.username);
				}
				else if (message.substring(0, 3).equals("US:")) {
					Data.logger.finest("Usernames");

					Data.usernames.add(message.substring(5, message.length() - 1));
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
				else if (message.substring(0, 3).equals("LB:")) {
					Data.logger.finest("Lobby");
					Data.l.start();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
				
				else if (message.substring(0, 3).equals("SC:")) {
					Data.logger.finest("Team Select");
					SendMessage("" + Data.selectedTeam);
				}
				else if(message.substring(0, 3).equals("SB:")) {
					Data.logger.finest("Score Board");
					Data.scores.set(Integer.parseInt(message.substring(3,4).trim()), Integer.parseInt(message.substring(8, message.length()).trim()));
					Data.teamNames.set(Integer.parseInt(message.substring(3,4).trim()), message.substring(5, 7).trim());
					Data.setUpdate();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
				else if(message.substring(0,3).equals("NT:")) {
					Data.logger.finest("Next Turn");
					Data.CurrentTurn = Integer.parseInt(message.substring(3, 4).trim());
					SendMessage("~");
				}
				else if(message.substring(0, 3).equals("DW:")) {
					Data.logger.finest("Card Drew");
					int suit = 0;
					int value = 0;
					try {
						suit = Integer.parseInt(message.substring(3,4).trim());
						value = Integer.parseInt(message.substring(5, message.length()).trim());
						Data.logger.finest("Card is: " + suit + " " + value + " NOT WILD");
					}
					catch(Exception e) {
						suit = -1;
						value = Integer.parseInt(message.substring(6, message.length()).trim());
						Data.logger.finest("Card is: " + suit + " " + value + " WILD");
					}
					
					
					Data.drew.suit = suit;
					Data.drew.value = value;
					Data.drewCard = true;
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
				else if(message.substring(0, 3).equals("RD:")) {
					Data.logger.finest("Redraw");
					SendMessage("~");
					Data.setUpdate();
				}
				else if(message.substring(0, 3).equals("EG:")) {
					Data.logger.finest("End Game");
					Data.scores.set(Integer.parseInt(message.substring(3,4).trim()), Integer.parseInt(message.substring(8, message.length()).trim()));
					Data.teamNames.set(Integer.parseInt(message.substring(3,4).trim()), message.substring(5, 7).trim());
					Data.endGame = true;
					Data.setUpdate();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
				else if(message.substring(0, 3).equals("SL:")) {
					Data.logger.finest("Select Hand");
					SendMessage("" + Data.selected);
					Data.phase = 0;
				}
				else if(message.substring(0, 3).equals("LC:")) {
					Data.logger.finest("Left Card");
					int suit = 0;
					int value = 0;
					try {
						suit = Integer.parseInt(message.substring(3,4).trim());
						value = Integer.parseInt(message.substring(5, message.length()).trim());
					}
					catch(Exception e) {
						suit = -1;
						value = Integer.parseInt(message.substring(6, message.length()).trim());
					}
					
					Data.left.suit = suit;
					Data.left.value = value;
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
				else if(message.substring(0, 3).equals("RC:")) {
					Data.logger.finest("Right Card");
					int suit = 0;
					int value = 0;
					try {
						suit = Integer.parseInt(message.substring(3,4).trim());
						value = Integer.parseInt(message.substring(5, message.length()).trim());
					}
					catch(Exception e) {
						suit = -1;
						value = Integer.parseInt(message.substring(6, message.length()).trim());
					}
					
					Data.right.suit = suit;
					Data.right.value = value;
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}
			else if(message.substring(0, 3).equals("CO:")) {
				try {
					Data.code = Integer.parseInt(message.substring(3,message.length()).trim());
				}
				catch(Exception e) {
					
				}
			}
		}
			catch (Exception e) {

			}

		}

	}

}
