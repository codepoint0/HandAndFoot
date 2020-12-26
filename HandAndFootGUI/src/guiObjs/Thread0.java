package guiObjs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
		OutputStream outToServer;
		try {
			byte[] OutBuffer = new byte[4096];
			outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			OutBuffer = message.getBytes();
			System.out.println(message);
			out.write(OutBuffer);
			OutBuffer = new byte[4096];
		} catch (IOException e) {
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

				in.read(b);
				String message = new String(b);
				
				if (message.substring(0, 3).equals("PS:")) {
					try {
						if (Data.phase != 5) {
							Data.phase = Integer.parseInt(message.substring(3, 4).trim());
						} else {
							Data.previousPhase = Integer.parseInt(message.substring(3, 4).trim());
						}
					}
					catch(Exception e) {
						System.out.println(e.toString());
					}

					SendMessage("~");
					Data.setUpdate();
				} else if (message.substring(0, 3).equals("TC:")) {
					Data.pile.clear();
					System.out.println("CLEARED");
					String[] values = message.substring(3, message.length()).split(" ");
					System.out.println("SPLIT");
					if (!values[0].equals("0")) {
						try {
							Data.pile.add(new Card(Integer.parseInt(values[0].trim()),
									Integer.parseInt(values[1].trim()), Integer.parseInt(values[2].trim())));
						} catch (Exception e) {
							System.out.println(e.toString());
						}

					}
					System.out.println("PARSED");
					SendMessage("~");
					Data.setUpdate();
				} else if (message.substring(0, 3).equals("PN:")) {
					Data.pileNumber = Integer.parseInt(message.substring(3, message.length()).trim());
					SendMessage("~");
					Data.setUpdate();
				} else if (message.substring(0, 3).equals("HC:")) {
					Data.players.get(Integer.parseInt(message.substring(3, 4))).cards = Integer
							.parseInt(message.substring(5, message.length()).trim());
					SendMessage("~");
					Data.setUpdate();
				} else if (message.substring(0, 3).equals("HF:")) {
					System.out.println(message.charAt(5) + " " + message.charAt(3));
					if (message.charAt(5) == '1') {
						try {
							Data.players.get(Character.getNumericValue(message.charAt(3))).foot = true;
						} catch (Exception e) {
							System.out.println(e.toString());
						}

					} else {
						try {
							Data.players.get(Character.getNumericValue(message.charAt(3))).foot = false;
						} catch (Exception e) {
							System.out.println(e.toString());
						}
					}

					SendMessage("~");
					Data.setUpdate();
				} else if (message.substring(0, 3).equals("EE:")) {
					for (int i = 0; i < Data.plannedGroups.size(); i++) {
						for (int j = 0; j < Data.plannedGroups.get(i).size(); j++) {
							Data.hand.add(Data.plannedGroups.get(i).get(j));
						}
						Data.plannedGroups.get(i).clear();
					}
					SendMessage("~");
					Data.setUpdate();
				} else if (message.substring(0, 3).equals("PH:")) {
					String[] cards = message.substring(3, message.length()).split(",");
					Data.hand.clear();
					for (String s : cards) {
						String[] cardValues = s.split(" ");
						try {
							Data.hand.add(new Card(Integer.parseInt(cardValues[0].trim()),
									Integer.parseInt(cardValues[1].trim()), Integer.parseInt(cardValues[2].trim())));
						} catch (Exception e) {
						}

					}
					SendMessage("~");
					Data.setUpdate();
				} else if (message.substring(0, 3).equals("PG:")) {
					int playerID = Integer.parseInt(message.substring(3, 4).trim());
					if (Data.userID == playerID) {
						for (ArrayList<Card> c : Data.plannedGroups) {
							c.clear();
						}
						for (ArrayList<Card> c : Data.playedGroups) {
							c.clear();
						}
						String[] pipeSplit = pipeSplit(message.substring(5, message.length()), '|');
						System.out.println(pipeSplit.length);
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
								}

							}
						}
						SendMessage("~");
						Data.setUpdate();
					} else {
						for (int i = 0; i < 11; i++) {
							Data.players.get(playerID).groups.get(i).clear();
						}
						String[] pipeSplit = pipeSplit(message.substring(5, message.length()), '|');
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

								}
							}
						}
						SendMessage("~");
						Data.setUpdate();
					}
				} else if (message.substring(0, 3).equals("SG:")) {
					System.out.println(message.substring(3));
					try {
						Data.userID = Integer.parseInt(message.substring(3, 4).trim());
					} catch (Exception e) {
						System.out.println(e.toString());
					}

					System.out.println("IN SG");

					try {
						Data.numOfPlayers = Integer.parseInt(message.substring(5, 6).trim());
						for (int i = 0; i < Data.numOfPlayers; i++) {
							Data.players.add(new Player());
						}
					} catch (Exception e) {
						System.out.println(e.toString());
					}

					Data.l.start();
					SendMessage(Data.username);
					Data.setUpdate();
				}
				else if (message.substring(0, 3).equals("UN:")) {
					System.out.println(message.substring(3));
					int user = 0;
					try {
						user = Integer.parseInt(message.substring(3, 4).trim());
					} catch (Exception e) {
						System.out.println(e.toString());
					}
					Data.players.get(user).name = message.substring(5, message.length() - 1);
					SendMessage("~");
					Data.setUpdate();
				}
			}

			catch (Exception e) {

			}

		}

	}

}
