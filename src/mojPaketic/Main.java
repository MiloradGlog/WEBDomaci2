package mojPaketic;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Main {

	private static final int NUMBER_OF_CLIENTS = 10;
	
	public static void main(String[] args) {

		//odradio komunikaciju preko jsona sledece rasporedjivanje
		
		startServer();
		
		ArrayList<Thread> clients = new ArrayList<Thread>();
		
		for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
			clients.add(new KockarClient(i));
		}
		
		for (Thread t : clients) {
			t.start();
		}
	      
	}

	private static void startServer() {
		int port = 8081;
	      try {
	         Thread t = new KrupijeServer(port);
	         t.start();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	
	private static void startClient() {
		Thread t = new KockarClient((int)(Math.random()*100));
	    t.start();
	}
	
}

