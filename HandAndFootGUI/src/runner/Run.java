package runner;

import java.io.IOException;
import java.net.Socket;

import guiObjs.Data;
import guiObjs.Lobby;
import guiObjs.Thread0;
import guiObjs.Welcome;
import guiObjs.Window1700;

public class Run {

	public static void main(String[] args) {
		Data.InitData();
		Welcome w = new Welcome();
		Lobby l = new Lobby();
		Window1700 wd = new Window1700();
		Data.w = wd;
		
		Data.s = w;
		Data.l = l;
		
		int i = 0;
		
		while(!Data.connected) {
			i++;
			i--;
			System.out.println(i);
		}
		System.out.println("Test");
		Thread0 th = new Thread0(Data.client);
		Data.th = th;
		Thread t1 = new Thread(l);
		
		t1.run();

		System.out.println("1");
		Thread t2 = new Thread(th);
		if(!Data.playerNum.equals("-1")) {
			Data.th.SendMessage(Data.playerNum + " " + Data.username);
		}
		t2.run();
		
		
		System.out.println("2");
	}

}
