package mojPaketic;

import java.net.*;

import com.google.gson.Gson;

import java.io.*;

public class KockarClient extends Thread{
	
	private String serverName = "localhost";
	private int port = 8081;
	private Gson gson;
	
	private boolean PLAYING;
	
	private int UUID;
	
	public KockarClient(int UUID) {
		this.gson = new Gson();
		this.UUID = UUID;
		this.PLAYING = false;
	}
	
	public void run() {
		while (true) {
			try {
				if (PLAYING) {
					sleep(10);
				} else {
					sleep((int)(Math.random()*1000));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("Konektujem se");
			connect();
		}
	}
	
	
	
	public void connect() {
		
      try {
          System.out.println("Connecting to " + serverName + " on port " + port);
          Socket client = new Socket(serverName, port);
         
          //System.out.println("Just connected to " + client.getRemoteSocketAddress());
         
          sendCommPackage(new CommPackage("Prvi paket sa klijenta"), client);
         
          CommPackage p = readCommPackage(client);
          //System.out.println("Stigao comm paket sa servera: "+ p.getMessage());;
         
          client.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
	}
	
	
	   private void sendCommPackage(CommPackage p, Socket client) throws SocketTimeoutException, IOException {
		   
		   p.setClientUUID(UUID);
		   p.setClientPlaying(PLAYING);
		   
		   OutputStream outToServer = client.getOutputStream();
		   
	       DataOutputStream out = new DataOutputStream(outToServer);
	         
	       out.writeUTF(gson.toJson(p));
	   }
	   
	   private CommPackage readCommPackage(Socket client) throws SocketTimeoutException, IOException {

		   InputStream inFromServer = client.getInputStream();
		   
	       DataInputStream in = new DataInputStream(inFromServer);
	         
	       CommPackage p = gson.fromJson(in.readUTF(), CommPackage.class);
	       
	       PLAYING = p.isClientPlaying();
	       
	       return p;
	   }
	
	
	public static void main(String [] args) {
		
		Thread t = new KockarClient((int)(Math.random()*100));
		t.start();
		
	}
}