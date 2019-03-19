package mojPaketic;

import com.google.gson.*;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class KrupijeServer extends Thread {
	
   private ServerSocket serverSocket;
   private Gson gson;
   
   private int[] table;
   
   public KrupijeServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(100000);
      gson = new Gson();
      table = new int[6];
      resetTable();
   }

   public void run() {
      while(true) {
         try {
            Socket server = serverSocket.accept();
            
            CommPackage p = readCommPackage(server);
//          System.out.println("Poruka od klijenta [" + p.getClientUUID() + "]: "+ p.getMessage());
            
            if (checkIfTableIsFull()) {
            	Game game = new Game(table);
            	int[] results = game.playGame();
            	
            	resetTable();
            	
            	System.out.println("Ispis rezultata iz threada krupije:\n");
            }
            
            sendCommPackage(new CommPackage("testPoruka bla bla"), server);
            
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   
   private void sendCommPackage(CommPackage p, Socket server) throws SocketTimeoutException, IOException {
	   
       DataOutputStream out = new DataOutputStream(server.getOutputStream());
       
       out.writeUTF(gson.toJson(p));
   }
   
   private CommPackage readCommPackage(Socket server) throws SocketTimeoutException, IOException {

       DataInputStream in = new DataInputStream(server.getInputStream());
       
       CommPackage p = gson.fromJson(in.readUTF(), CommPackage.class);
       
       addClientToTable(p.getClientUUID());
       
       printTable();
       
       return p;
   }
   
   
   private boolean addClientToTable(int UUID) {
	   
	   for (int i = 0; i < 6; i++) {
		   if (table[i] == UUID) {
			   System.out.println("Client ["+ UUID +"] already in table");
			   return true;
		   }
		   if (table[i] == -1) {
			   table[i] = UUID;
			   System.out.println("Added client ["+ UUID +"] to table");
			   return true;
		   }
	   }
	   System.out.println("Couldn't add client ["+ UUID +"] to table");
	   return false;
   }
   
   private boolean removeClientFromTable(int UUID) {
	   
	   for (int i = 0; i < 6; i++) {
		   if (table[i] == UUID) {
			   table[i] = -1;
			   System.out.println("Removed client ["+ UUID +"] from the table");
			   return true;
		   }
	   }
	   System.out.println("Couldnt remove client ["+ UUID +"] from the table");
	   return false;
   }
   
   private void printTable() {
	   StringBuilder sb = new StringBuilder();
	   for (int i = 0; i < 6; i++) {
		   switch (i) {
		   case(0): {
			   sb.append("     [" + table[i] + "]\n");	break;
		   }case(1): {
			   sb.append("[" + table[i] + "]/----\\");	break;
		   }case(2): {
			   sb.append("[" + table[i] + "]\n");	break;
		   }case(3): {
			   sb.append("[" + table[i] + "]\\----/");	break;
		   }case(4): {
			   sb.append("[" + table[i] + "]\n");	break;
		   }case(5): {
			   sb.append("     [" + table[i] + "]");	break;
		   }
		   }
	   }
	   System.out.println(sb.toString());
   }
   
   private void resetTable() {
	   for (int i = 0; i < 6; i++) {
		   table[i] = -1;
	   }
   }
   
   private boolean checkIfTableIsFull() {
	   for (int i = 0; i < 6; i++) {
		   if (table[i] == -1) {
			   return false;
		   }
	   }
	   return true;
   }
   
   public static void main(String [] args) {
      int port = 8081;
      try {
         Thread t = new KrupijeServer(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}