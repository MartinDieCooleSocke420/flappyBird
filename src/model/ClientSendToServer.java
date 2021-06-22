package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class ClientSendToServer extends Thread {

	private String msgFromServer;
	public ClientSendToServer(String name) {
		super(name);
	}

	public void run() {
		
		String hostName = "localhost"; // Rechner-Name bzw. -Adresse
		int port = 4242; // Port-Nummer
		
		Gson gson = new Gson();
        		
		//TODO: Extra Thread hierf�r einf�gen, damit es immernoch geht
		try (Socket socket = new Socket(hostName, port)){

			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Inputstream vom Server
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true); // Outputstream zum Server

			
			socketOut.println(gson.toJson(HighscoreObject.highscoreList));

			
			//TODO: Hier kommt die aktuelle Higshscoreliste an
			msgFromServer = socketIn.readLine();
		
			

		} catch (UnknownHostException ue) {
			System.out.println("Kein DNS-Eintrag fuer " + hostName);
		} catch (NoRouteToHostException e) {
			System.err.println("Nicht erreichbar " + hostName);
		} catch (IOException e) {
			System.out.println("IO-Error");
		}

	}
	
	public String getMsgFromServer() {
		return msgFromServer;
	}
}
