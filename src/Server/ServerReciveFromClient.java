package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import com.google.gson.Gson;
import model.HighscoreList;


public class ServerReciveFromClient extends Thread {

	
	public boolean isStarted = false;
	private FlappyBirdServer fBserver;

	public ServerReciveFromClient (String name, FlappyBirdServer fBserver){
		super(name);
		this.fBserver = fBserver;
	}
	
	//TODO: protokoll lösung 2
	public void run() {
		int port = 4242;
		
		try (ServerSocket server = new ServerSocket(port)){

			System.out.println("Server gestartet!");

			while (true) {
				
				HighscoreList hs = new HighscoreList();

				//in extern auslagern
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
						
						//TODO: nicht sinvoll da bereits refferenz bekannt, folge aus nicht mehr static
						fBserver.writeHighscore(fBserver.highscoreList);
					}
					
					//Zurückgeben der ServerHighscorelist an den Client
					if(hs != fBserver.highscoreList)
						socketOut.println(gson.toJson(fBserver.highscoreList));
					
				} catch (IOException e) {
					e.printStackTrace();
				}		

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
