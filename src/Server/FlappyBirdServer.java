package Server;
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

import model.ClientSendToServer;
import model.HighscoreObject;

public class FlappyBirdServer {

	private static final String savePath = "highscoreServer.json";
	static ArrayList<HighscoreObject> highscores = new ArrayList<>();
	

	public static void main(String[] args) {
		
//		readHighscore();
		
		
		
		ServerReciveFromClient t1 = new ServerReciveFromClient("Listener 1");
		t1.start();
	/*
	 * TODO:
	 * Einen Reader einbauen der die highscoreServer.json einließt
	 * überprüfen ob die vom client übergebenen Highscores schon in der highscoreServer.json vorhanden sind
	 * ansonsten diese der highscoreServer.json anhängen
	 */
	}
	public static void writeHighscore(ArrayList<HighscoreObject> highscores) {
		
		try (Writer writer = new FileWriter(savePath)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(highscores, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
