package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.HighscoreList;
import model.HighscoreObject;

public class ServerReciveFromClient extends Thread {

	
	public boolean isStarted = false;
	private FlappyBirdServer fBserver;

	public ServerReciveFromClient (String name, FlappyBirdServer fBserver){
		super(name);
		this.fBserver = fBserver;
	}
	
	public void run() {
		int port = 4242;
		
		try (ServerSocket server = new ServerSocket(port)){

			System.out.println("Server gestartet!");

			while (true) {
				
				HighscoreList hs = new HighscoreList();

				try (Socket socket = server.accept()) { // try-with-resources, Auf Verbindung warten, Methode blockiert
					socket.setSoTimeout(5000);

					BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Inputstream vom Client
					PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true); // Outputstream zum Client mit autoflush

					String line = socketIn.readLine();
					Gson gson = new Gson();			
					hs = gson.fromJson(line, HighscoreList.class);
					
					
					//Die vom client übergebenen Highscores werden mit den highscoreObjects vom Server verglichen
					//und abschliessend abgespeichert					
					if(hs != null) {
						fBserver.checkHighscores(hs);
						FlappyBirdServer.writeHighscore(FlappyBirdServer.highscoreList);
					}
					
					//Zurückgeben der ServerHighscorelist an den Client
					if(hs != FlappyBirdServer.highscoreList)
						socketOut.println(gson.toJson(FlappyBirdServer.highscoreList));
					
				} catch (IOException e) {
					e.printStackTrace();
				}		

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
