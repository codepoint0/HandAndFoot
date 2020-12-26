package guiObjs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	static String serverName = "52.26.167.56";
	static int port = Integer.parseInt("8080");
	static byte[] b = new byte[4096];
	static Socket client;
	public static void main(String[] args) {

		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			client = new Socket(serverName, port);
		} catch (Exception e) {

		}
	}

	public static void send(String message) {
		OutputStream outToServer;
		try {
			outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.write(b);
			b = message.getBytes();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static String receive() {
		try {
			InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         in.read(b);
	         String str = new String(b); 
	         return str;
		}
		catch(Exception e){
			return e.toString();
		}
        
	}
	
	public static void Phase0() {
		
		while(Data.phase == 0) {
			
		}
	}

}



