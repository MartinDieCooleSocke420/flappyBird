package Server;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import model.ClientSendToServer;
import model.HighscoreList;
import model.HighscoreObject;

public class FlappyBirdServer {

	private static final String savePath = "highscoreServer.json";
	static HighscoreList highscoreList = new HighscoreList();
	

	public static void main(String[] args) {
		
		FlappyBirdServer server = new FlappyBirdServer();
		ServerReciveFromClient t1 = new ServerReciveFromClient("Listener 1", server);
		t1.start();		
		/*
	 * TODO:
	 * Einen Reader einbauen der die highscoreServer.json einließt
	 * überprüfen ob die vom client übergebenen Highscores schon in der highscoreServer.json vorhanden sind
	 * ansonsten diese der highscoreServer.json anhängen
	 */
	}
	
	FlappyBirdServer() {
		readHighscores();
	}
	
	public static void writeHighscore(HighscoreList highscoreList2) {
		
		try (Writer writer = new FileWriter(savePath)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(highscoreList2, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readHighscores() {
		
		HighscoreList hl = null;
		
		try (BufferedReader fr = new BufferedReader(new FileReader(savePath))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = fr.readLine()) != null)
				sb.append(line);
			Gson gson = new Gson();
			
			hl = gson.fromJson(sb.toString(), HighscoreList.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException  e) {
			e.printStackTrace();
		}
		
		//Wenn die eingelesene hl gültig ist, soll sie alst objektatribut festgelegt werden
		if(hl != null) {
			HighscoreObject.highscoreList = hl;				
		}		
	}

	
	/**
	 * Ueberprueft ob die uebergebenen highscores bereits in der Server highscoreList sind
	 * ansonsten werden diese hinzugefügen
	 * 
	 * Zum Schluss wird der ServerHighscore sortiert
	 * 
	 * @param resivedHighscoreList Objekt der Klasse HighscoreList
	 */
	public void checkHighscores(HighscoreList resivedHighscoreList) {
		
		ArrayList<HighscoreObject> resevedHighscoreList = resivedHighscoreList.getHighscores();
		ArrayList<HighscoreObject> serverHighscoreList = highscoreList.getHighscores();
		
		for (HighscoreObject serverHLItem : resevedHighscoreList) {
			if(!serverHighscoreList.contains(serverHLItem)) {
				serverHighscoreList.add(serverHLItem);
			}
		}
		
		//Sortieren der Server-ArrayListe zum Ende
		highscoreList.getHighscores().sort(Comparator.comparing(HighscoreObject::getHighscoreValue));
		Collections.reverse(highscoreList.getHighscores());
	}	
}
