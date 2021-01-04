package guiObjs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * This thread listens for all commands from the server and sends all needed
 * information to the server.
 * 
 * @author Tyler K. Gordon and Joshua Speckman
 *
 */
public class Thread0 implements Runnable {
	byte[] b = new byte[4096];
	public Socket client;
	final DataOutputStream out;

	/***
	 * Splits a given string according to a character. Splits into a String array.
	 * 
	 * @param s the string to split
	 * @param d the character to split by
	 * @return an array of strings of the split parts
	 */
	public static String[] SplitOnCharacter(String s, char d) {
		ArrayList<String> t = new ArrayList<String>();
		String[] j;
		String toAdd = "";

		// Adds character by character
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

	/***
	 * Given a client creates an object that will listen and write to the server
	 * 
	 * @param client the client which is connected to the server
	 */
	public Thread0(Socket client) {
		this.client = client;
		OutputStream outToServer = null;
		try {
			outToServer = client.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out = new DataOutputStream(outToServer);
	}

	/***
	 * Sends a message to the Server
	 * 
	 * @param message The message to be sent
	 */
	public void SendMessage(String message) {
		Data.logger.info("Sending: " + message);

		try {
			byte[] OutBuffer = new byte[4096];
			OutBuffer = message.getBytes();

			// Write the message to the output stream
			out.write(OutBuffer);
			Data.logger.fine("Message was sent!");
		} catch (IOException e) {
			Data.logger.severe("FAILED TO SEND MESSAGE: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * A thread that constantly listens to the server to get commands.
	 */
	@Override
	public void run() {
		SendMessage(Data.playerNum);
		while (client.isConnected()) {
			try {
				InputStream inFromServer = client.getInputStream();

				// While the client is connected try to read from the stream
				DataInputStream in = new DataInputStream(inFromServer);
				b = new byte[4096];

				Data.logger.finest("Ready to read from SERVER");
				in.read(b);
				String message = new String(b);

				Data.logger.fine("Reading: " + message);

				// Phase switch message
				if (message.substring(0, 3).equals("PS:")) {
					Data.logger.finest("Phase Switch");
					try {
						// If the user is not looking at another teams groups
						// then set the phase if they are then set the previous phase.
						if (Data.phase != 5) {
							Data.phase = Integer.parseInt(message.substring(3, 4).trim());
						} else {
							Data.previousPhase = Integer.parseInt(message.substring(3, 4).trim());
						}
					} catch (Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
						System.out.println(e.toString());
					}

					Data.logger.finer("Message parsed, data gathered, sending back confirmation");

					// Send back confirmation
					SendMessage("~");
				}

				// Top card message
				else if (message.substring(0, 3).equals("TC:")) {
					Data.logger.finest("Top Card");

					// Clears the pile to show the top card
					Data.pile.clear();

					// Parses the card first by ' '
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

				// Pile number message
				else if (message.substring(0, 3).equals("PN:")) {
					Data.logger.finest("Pile Number");

					// The number of cards in the pile
					Data.pileNumber = Integer.parseInt(message.substring(3, message.length()).trim());
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Hand count message
				else if (message.substring(0, 3).equals("HC:")) {
					Data.logger.finest("Hand Count");
					try {

						// updates the number of cards there are in the players hand
						Data.logger.finer("Trying to assign hand count to player!");
						Data.players.get(Integer.parseInt(message.substring(3, 4))).cards = Integer
								.parseInt(message.substring(5, message.length()).trim());
					} catch (Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Has foot message
				else if (message.substring(0, 3).equals("HF:")) {
					Data.logger.finest("Has Foot");

					// User with given id at 3 has their foot
					if (message.charAt(5) == '1') {
						Data.logger.finer("Determined that player has foot");
						try {
							Data.players.get(Character.getNumericValue(message.charAt(3))).foot = true;
						} catch (Exception e) {
							Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
							System.out.println(e.toString());
						}

					} else {
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

				// Error message
				else if (message.substring(0, 3).equals("EE:")) {

					// This message only gets displayed if the player makes an illegal play
					Data.logger.warning(
							"Error in sending groups? Something failed, either we wanted that or not... check!");
					for (int i = 0; i < Data.plannedGroups.size(); i++) {
						for (int j = 0; j < Data.plannedGroups.get(i).size(); j++) {
							Data.hand.add(Data.plannedGroups.get(i).get(j));
						}
						Data.plannedGroups.get(i).clear();
					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Player hand message
				else if (message.substring(0, 3).equals("PH:")) {
					Data.logger.finest("Player Hand");

					// Splits by comma
					String[] cards = message.substring(3, message.length()).split(",");
					Data.hand.clear();
					Data.logger.finest("Cleared player's hand (client side)");
					for (String s : cards) {

						// Then splits by space
						String[] cardValues = s.split(" ");
						Data.logger.finest(
								"We parsed the card into " + s + " trying to put that as a card in the player's hand");
						try {

							// Tries to parse and place into player's hand
							Data.hand.add(new Card(Integer.parseInt(cardValues[0].trim()),
									Integer.parseInt(cardValues[1].trim()), Integer.parseInt(cardValues[2].trim())));
						} catch (Exception e) {
							Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
						}
					}
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Player group message
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
						String[] pipeSplit = SplitOnCharacter(message.substring(5, message.length()), '|');
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
					} else {
						Data.logger.finer("The ID IS NOT this player!");
						for (int i = 0; i < 11; i++) {
							Data.players.get(playerID).groups.get(i).clear();
						}
						String[] pipeSplit = SplitOnCharacter(message.substring(5, message.length()), '|');
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

				// Start game message
				else if (message.substring(0, 3).equals("SG:")) {
					Data.logger.finest("Start Game");

					// Tries to set the player id
					try {
						Data.userID = Integer.parseInt(message.substring(3, 4).trim());
					} catch (Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
					}

					// Parses the number of players and sets the scores for the score board
					try {
						Data.logger.finest("Trying to parse num of players and then create those players");
						Data.numOfPlayers = Integer.parseInt(message.substring(5, 6).trim());
						for (int i = 0; i < Data.numOfPlayers; i++) {
							Data.players.add(new Player());
						}
						for (int i = 0; i < Data.numOfPlayers / 2; i++) {
							Data.scores.add(0);
							Data.teamNames.add("");
						}
					} catch (Exception e) {
						Data.logger.severe("We failed to parse : " + message + " : " + e.getMessage());
					}

					// Tells the team select to start the game
					Data.ts.start();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage(Data.username);
				}

				// Username message
				else if (message.substring(0, 3).equals("UN:")) {
					Data.logger.finest("Got Username");
					int user = 0;

					// Parses the player id and then sets that username to the player id
					try {
						user = Integer.parseInt(message.substring(3, 4).trim());
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					Data.logger.finer("Trying to set username to given user ID");
					Data.players.get(user).name = message.substring(5, message.length() - 1);
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Get username message
				else if (message.substring(0, 3).equals("GU:")) {

					// The server is requesting this user's username
					Data.logger.finest("Get Username");

					// If this user is user 0 then it is the original user
					if (message.substring(0, 4).equals("GU:0")) {
						Data.logger.finest("User is user 0");
						Data.Original = true;
					}
					Data.playerNum = message.substring(5, message.length()).trim();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage(Data.username);
				}

				// Getting all other usernames message
				else if (message.substring(0, 3).equals("US:")) {
					Data.logger.finest("Usernames");

					Data.usernames.add(message.substring(5, message.length() - 1));
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Move to Lobby message
				else if (message.substring(0, 3).equals("LB:")) {
					Data.logger.finest("Lobby");
					Data.l.start();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Send which team the user wanted
				else if (message.substring(0, 3).equals("SC:")) {
					Data.logger.finest("Team Select");
					SendMessage("" + Data.selectedTeam);
				}

				// Scoreboard information message
				else if (message.substring(0, 3).equals("SB:")) {
					Data.logger.finest("Score Board");

					// Parses by team and by score
					Data.scores.set(Integer.parseInt(message.substring(3, 4).trim()),
							Integer.parseInt(message.substring(8, message.length()).trim()));
					Data.teamNames.set(Integer.parseInt(message.substring(3, 4).trim()),
							message.substring(5, 7).trim());
					Data.setUpdate();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Send the next person's turn
				else if (message.substring(0, 3).equals("NT:")) {
					Data.logger.finest("Next Turn");
					Data.CurrentTurn = Integer.parseInt(message.substring(3, 4).trim());
					SendMessage("~");
				}

				// The card which the player drew
				else if (message.substring(0, 3).equals("DW:")) {
					Data.logger.finest("Card Drew");
					int suit = 0;
					int value = 0;
					try {
						suit = Integer.parseInt(message.substring(3, 4).trim());
						value = Integer.parseInt(message.substring(5, message.length()).trim());
						Data.logger.finest("Card is: " + suit + " " + value + " NOT WILD");
					}

					// wild drew
					catch (Exception e) {
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

				// Redraw
				else if (message.substring(0, 3).equals("RD:")) {
					Data.logger.finest("Redraw");
					SendMessage("~");
					Data.setUpdate();
				}

				// End game message
				else if (message.substring(0, 3).equals("EG:")) {
					Data.logger.finest("End Game");
					Data.scores.set(Integer.parseInt(message.substring(3, 4).trim()),
							Integer.parseInt(message.substring(8, message.length()).trim()));
					Data.teamNames.set(Integer.parseInt(message.substring(3, 4).trim()),
							message.substring(5, 7).trim());
					Data.endGame = true;
					Data.setUpdate();
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Select hand message
				else if (message.substring(0, 3).equals("SL:")) {

					// Gather which hand the user selected
					Data.logger.finest("Select Hand");
					SendMessage("" + Data.selected);
					Data.phase = 0;
				}

				// Left card message
				else if (message.substring(0, 3).equals("LC:")) {

					// The card which is to be displayed on the left card spot
					// for the user to pick from
					Data.logger.finest("Left Card");
					int suit = 0;
					int value = 0;
					try {
						suit = Integer.parseInt(message.substring(3, 4).trim());
						value = Integer.parseInt(message.substring(5, message.length()).trim());
					} catch (Exception e) {
						suit = -1;
						value = Integer.parseInt(message.substring(6, message.length()).trim());
					}

					Data.left.suit = suit;
					Data.left.value = value;
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Right card message
				else if (message.substring(0, 3).equals("RC:")) {
					Data.logger.finest("Right Card");
					int suit = 0;
					int value = 0;
					try {
						suit = Integer.parseInt(message.substring(3, 4).trim());
						value = Integer.parseInt(message.substring(5, message.length()).trim());
					} catch (Exception e) {
						suit = -1;
						value = Integer.parseInt(message.substring(6, message.length()).trim());
					}

					Data.right.suit = suit;
					Data.right.value = value;
					Data.logger.finer("Message parsed, data gathered, sending back confirmation");
					SendMessage("~");
				}

				// Code message
				else if (message.substring(0, 3).equals("CO:")) {

					// The code the server is using for the room
					try {
						Data.code = Integer.parseInt(message.substring(3, message.length()).trim());
					} catch (Exception e) {

					}
				}
			} catch (Exception e) {
			}
		}

	}

	/***
	 * This section of code is used to get and send the code from the server
	 */
	public void Code() {
		InputStream inFromServer = null;
		try {
			inFromServer = Data.client.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(inFromServer);
		byte[] b = new byte[4096];

		Data.logger.info("Sending: " + Data.playerNum);

		// Write out either the code to the server
		// or writes 4, 6, or 8 and the server knows
		// to create a new game based on that
		try {
			byte[] OutBuffer = new byte[4096];
			OutBuffer = Data.playerNum.getBytes();
			out.write(OutBuffer);
			Data.logger.fine("Message was sent!");
		} catch (IOException e) {
			Data.logger.severe("FAILED TO SEND MESSAGE: " + e.getMessage());
			e.printStackTrace();
		}

		Data.logger.finest("Ready to read from SERVER");
		
		// Read the code from the server
		try {
			in.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String message = new String(b);
		
		// Parse the message from the server
		try {
			Data.code = Integer.parseInt(message.substring(3, message.length()).trim());
		} catch (Exception e) {
			Data.code = -1;
		}
	}

}
