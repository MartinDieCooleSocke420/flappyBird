package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class HighscoreObject {
	
	private String pName;
	private int passes = 0;
	private double highscore = 0;
	
	public static HighscoreList highscoreList = new HighscoreList();
	//TODO: WENN NICHT STATISCH java.lang.StackOverflowError !!!???
	
	private static final String savePath = "highscores.json"; 
	
	public HighscoreObject () {
//		readHighscores();
	}

	public void readHighscores() {
		
		HighscoreList hl = null;
		
		try (BufferedReader fr = new BufferedReader(new FileReader(HighscoreObject.savePath))) {
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
			
			//Sortieren der ArrayListe nach dem Einlesen
			highscoreList.getHighscores().sort(Comparator.comparing(HighscoreObject::getHighscoreValue));
			Collections.reverse(highscoreList.getHighscores());
			return;
		}		
	}

	@Override
	public String toString() {
		return "HighscoreObject [pName=" + pName + ", passes=" + passes + ", highscore=" + highscore + "]";
	}


	public static void writeHighscore(HighscoreList highscoreList) {
		
		//TODO: Versuchen vom server zu holen
	
//		Collections.sort(highscores); //TODO: sortieren der HighscoreListe
		
		System.out.println(highscoreList);
		
		Gson gson = new Gson();
		String highscoreListAsJson = gson.toJson(highscoreList);
		
		System.out.println(highscoreListAsJson);
		
		try (FileWriter fw = new FileWriter(savePath)) {
			fw.write(highscoreListAsJson);			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		ClientSendToServer t1 = new ClientSendToServer("Verbindung 1");
		t1.start();
		
	}

	/**
	 * Ueberprueft ob der Spieler gerade eine Roehre durchquert hat und erhoet beim erfolgreichen durchfliegen
	 * die passes und den Highscore
	 * 
	 * @param gameObjects liste aller Gameobjects
	 */
	public void checkHighscore(List<GameObject> gameObjects) {
		for (GameObject gameObject : gameObjects) {
			if(gameObject instanceof Tube) {
				for (GameObject gameObject1 : gameObjects) {
					if(gameObject1 instanceof Bird) {
						
						//TODO: je nach tube speed funktioniert es nicht
						if( (int) gameObject.getX() == (int) gameObject1.getX()) {
							passes = getPasses() + 1;
							highscore += gameObject.getSpeed() * gameObject1.getSpeed();
							return;
						}
					}
				}
			}
		}		
	}
	

	//TODO: Funktioniert noch nicht, gerade zu müde das zu machen, mach ich morgen
	public int getHighscorePlacement() {
		
		int counter = 0;
		ArrayList<HighscoreObject> highscores = highscoreList.getHighscores();
		
		for (HighscoreObject highscoreObject : highscores) {
			if (highscoreObject.getHighscoreValue() > getHighscoreValue())
				return counter;	
		}

		//wenn der eintrag der letzte ist soll der highscore selber nochmal zurück gegeben werden
		return counter;

	}

	
	
	//Wandelt die ArrayList<HighscoreObjects> in ein Array für die Textausgabe in einem JTable um und gibt dieses Array zurück
	public String[][] getHighscoreArray() {
		
		ArrayList<HighscoreObject> hl = highscoreList.getHighscores();

		String[][] data = new String[highscoreList.getHighscores().size()][2];
	
		int counter = 0;
		for (HighscoreObject highscoreObject : hl) {
			data[counter][0] = highscoreObject.getName();
			data[counter][1] = Double.toString(highscoreObject.getHighscoreValue());
			counter++;
		}
		
		return data;
		
	}
	
	//Getters && Setters

	public double getHighscoreValue() {
		return highscore;
	}


	public int getPasses() {
		return passes;
	}

	public void setName(String text) {
		this.pName = text;
		
	}
	
	public String getName() {
		return pName;
		
	}	

	public HighscoreList getHighscoreList() {
		return highscoreList;
	}

}
