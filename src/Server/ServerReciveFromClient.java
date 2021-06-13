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

import model.HighscoreObject;

public class ServerReciveFromClient extends Thread {

	
	public boolean isStarted = false;

	public ServerReciveFromClient (String name){
		super(name);
	}
	
	public void run() {
		int port = 4242;
		
		try (ServerSocket server = new ServerSocket(port)){

			System.out.println("Server gestartet!");

			while (true) {

				try (Socket socket = server.accept()) { // try-with-resources, Auf Verbindung warten, Methode blockiert
					socket.setSoTimeout(5000);

					BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Inputstream vom Client
					PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true); // Outputstream zum Client mit autoflush

					String line = socketIn.readLine();
					
					
					Gson gson = new Gson();
										
					FlappyBirdServer.highscores = gson.fromJson(line, new TypeToken<ArrayList<HighscoreObject>>() {}.getType());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Highscores werden gespeichert!");
				FlappyBirdServer.writeHighscore(FlappyBirdServer.highscores);

				System.out.println("Warte auf nchste Anfrage!");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
