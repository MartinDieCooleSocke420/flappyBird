package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.HighscoreList;
import model.HighscoreObject;

public class FlappyBirdServer {

	private final String savePath = "highscoreServer.json";
	HighscoreList highscoreList = new HighscoreList();
	

	public static void main(String[] args) {
		
		FlappyBirdServer server = new FlappyBirdServer();
		ServerReciveFromClient t1 = new ServerReciveFromClient("Listener 1", server);
		t1.start();		
	}
	
	FlappyBirdServer() {
		readHighscores();
	}
	
	public void writeHighscore() {
		
		try (Writer writer = new FileWriter(savePath)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(highscoreList, writer);
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
		
		//Wenn die eingelesene hl gültig ist, soll sie als objektatribut festgelegt werden
		//um nullpointer exeption zu umgehen
		if(hl != null) {
			highscoreList = hl;				
		}		
	}

	
	/**
	 * Ueberprueft ob die uebergebenen highscores bereits in der Server highscoreList sind
	 * ansonsten werden diese hinzugefügen 
	 * 
	 * Zum Schluss wird der ServerHighscore sortiert
	 * 
	 * @param receivedHighscoreListAsObject Objekt der Klasse HighscoreList
	 */
	public void checkHighscores(HighscoreList receivedHighscoreListAsObject) {
		
		ArrayList<HighscoreObject> receivedHighscoreListAsArrayList = receivedHighscoreListAsObject.getHighscores();
		ArrayList<HighscoreObject> serverHighscoreList = highscoreList.getHighscores();
		
		for (HighscoreObject receivedHighscoreListItem : receivedHighscoreListAsArrayList) {
			if(!serverHighscoreList.contains(receivedHighscoreListItem)) {
				serverHighscoreList.add(receivedHighscoreListItem);
			}
		}
		
		//Sortieren der Server-ArrayListe zum Ende
		highscoreList.getHighscores().sort(Comparator.comparing(HighscoreObject::getHighscoreValue));
		Collections.reverse(highscoreList.getHighscores());
	}	
}
