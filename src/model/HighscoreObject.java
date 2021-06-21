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
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class HighscoreObject implements Comparable<HighscoreObject>{
	
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
			this.highscoreList = hl;	
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
	
	/*
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
	*/
	
	
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
	
	//Wandelt die ArrayList<HighscoreObjects> in ein Array für die Textausgabe in einem JTable um und gibt dieses Array zurück
	public String[][] getHighscoreArray() {
		
		ArrayList<HighscoreObject> hl = highscoreList.getHighscores();
	/* 
	 * Wirft eine: 
	 * Exception in thread "AWT-EventQueue-0" java.lang.ClassCastException: class com.google.gson.internal.LinkedTreeMap cannot be cast to class model.HighscoreObject (com.google.gson.internal.LinkedTreeMap and model.HighscoreObject are in unnamed module of loader 'app')
	at model.HighscoreObject.getHighscoreArray(HighscoreObject.java:158)
	 */ 
		String[][] data = new String[highscoreList.getHighscores().size()][2];
	
		int counter = 0;
		for (HighscoreObject highscoreObject : hl) {
			data[counter][0] = highscoreObject.getName();
			data[counter][1] = Double.toString(highscoreObject.getHighscoreValue());
			counter++;
		}
		
	
		
		String[][] testData = {			
	            { "Gib deinen Namen ein", "18.0"},
	            { "Martin", "12.0" },
	            { "Maritn :P", "30.0"},
	            { "sdafsfdgdfg", "6.0"},
	            { "Martin J. Brucker", "132.0"}	               
	        };

		return data;
		
	}
	
	//TODO: Muss noch getested werden, zuerst einlesen von JSONS 
	@Override
	public int compareTo(HighscoreObject o) {
		if (o.getHighscoreValue() > this.highscore)
			return 1;
		return 0;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(highscore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pName == null) ? 0 : pName.hashCode());
		result = prime * result + passes;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HighscoreObject other = (HighscoreObject) obj;
		if (Double.doubleToLongBits(highscore) != Double.doubleToLongBits(other.highscore))
			return false;
		if (pName == null) {
			if (other.pName != null)
				return false;
		} else if (!pName.equals(other.pName))
			return false;
		if (passes != other.passes)
			return false;
		return true;
	}

	public HighscoreList getHighscoreList() {
		return highscoreList;
	}
	
}
