import java.awt.List;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import model.HighscoreObject;

public class FlappyBirdServer {

	private static final String savePath = "highscoreServer.json";
	

	public static void main(String[] args) {

		ArrayList<HighscoreObject> highscores = new ArrayList<>();
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
										
					highscores = gson.fromJson(line, new TypeToken<ArrayList<HighscoreObject>>() {}.getType());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Highscores werden gespeichert!");
				writeHighscore(highscores);

				System.out.println("Warte auf nchste Anfrage!");
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	/*
	 * TODO:
	 * Einen Reader einbauen der die highscoreServer.json einließt
	 * überprüfen ob die vom client übergebenen Highscores schon in der highscoreServer.json vorhanden sind
	 * ansonsten diese der highscoreServer.json anhängen
	 */
	
	public static void writeHighscore(ArrayList<HighscoreObject> highscores) {
		
		try (Writer writer = new FileWriter(savePath)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(highscores, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
