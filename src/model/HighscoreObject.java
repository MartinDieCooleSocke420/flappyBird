package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class HighscoreObject implements Comparable<HighscoreObject>{
	
	private String pName;
	private int passes = 0;
	private double highscore = 0;
	
	static ArrayList<HighscoreObject> highscores = new ArrayList<>();
	static String savePath = "highscores.json"; 
	
	public HighscoreObject () {
		readHighscores();
	}

	
	private static void readHighscores() {
		
		ArrayList<HighscoreObject> parsedArrayList = new ArrayList<>();
		
		try (BufferedReader fr = new BufferedReader(new FileReader(HighscoreObject.savePath))) {
			
		
			
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = fr.readLine()) != null)
				sb.append(line);
			Gson gson = new Gson();
			parsedArrayList = gson.fromJson(sb.toString(), highscores.getClass());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();		
		}
	
		
		highscores = parsedArrayList;
		System.out.println(highscores);
		
	}


	@Override
	public String toString() {
		return "HighscoreObject [pName=" + pName + ", passes=" + passes + ", highscore=" + highscore + "]";
	}


	public static void writeHighscore(HighscoreObject highscore) {
		
		highscores.add(highscore);
//		Collections.sort(highscores); //TODO: sortieren der liste

		try (Writer writer = new FileWriter(savePath)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(highscores, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void checkHighscore(List<GameObject> gameObjects) {
		for (GameObject gameObject : gameObjects) {
			if(gameObject instanceof Tube) {
				for (GameObject gameObject1 : gameObjects) {
					if(gameObject1 instanceof Bird) {
						
						/*
						 * Da die X Koordinate von Roehre und Bird nur auußerst selten identisch sind,
						 * werden diese erst durch 5 geteilt und verglichen. Hierdurch entsteht eine
						 * ungenauigkeit, diese ist aber verwerflich						 * 
						 */						
						
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
	
	
	//TODO: Muss noch getestet werden
	public int getHighscorePlacement() {
		
		int counter = 0;
		
		for (HighscoreObject highscoreObject : highscores) {
			if (highscoreObject.getHighscore() > getHighscore())
				return counter;	
		}
		//wenn der eintrag der letzte ist soll der highscore selber nochmal zurück gegeben werden

		return counter;

	}
	
	
	
	public double getHighscore() {
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
	
	
	//TODO: Muss noch getested werden, zuerst einlesen von JSONS 
	//Wandelt die ArrayList<HighscoreObjects> in ein Array für die Textausgabe in einem JTable um und gibt dieses Array zurück
	public String[][] getHighscoreArray() {
		
	/*
	 * 
	 * Wirft eine: 
	 * Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException: class com.google.gson.internal.LinkedTreeMap cannot be cast to class model.HighscoreObject (com.google.gson.internal.LinkedTreeMap and model.HighscoreObject are in unnamed module of loader 'app')
	at model.HighscoreObject.getHighscoreArray(HighscoreObject.java:158)
	 * 
		String[][] data = new String[highscores.size()][2];
	
		int counter = 0;
		for (HighscoreObject highscoreObject : highscores) {
			data[counter][0] = highscoreObject.getName();
			data[counter][1] = Double.toString(highscoreObject.getHighscore());
			counter++;
		}
		
		*/
		
		String[][] testData = {			
	            { "Gib deinen Namen ein", "18.0"},
	            { "Martin", "12.0" },
	            { "Maritn :P", "30.0"},
	            { "sdafsfdgdfg", "6.0"},
	            { "Martin J. Brucker", "132.0"}	               
	        };

		return testData;
		
	}


	//TODO: Muss noch getested werden, zuerst einlesen von JSONS 
	@Override
	public int compareTo(HighscoreObject o) {
		if (o.getHighscore() > this.highscore)
			return 1;
		return 0;
	}
}
