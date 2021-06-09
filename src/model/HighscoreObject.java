package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import com.google.gson.reflect.TypeToken;

public class HighscoreObject implements Comparable<HighscoreObject>{
	
	private String pName;
	private int passes = 0;
	private double highscore = 0;
	
	static ArrayList<HighscoreObject> highscores = new ArrayList<>();
	static String savePath = "highscores.json"; 
	
	public HighscoreObject () {
//		readHighscores();
	}

	
	private static void readHighscores() {
		
		try (BufferedReader fr = new BufferedReader(new FileReader(HighscoreObject.savePath))) {
			
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = fr.readLine()) != null)
				sb.append(line);
					
			Gson gson = new Gson();
			
			Type highscoreArrayListType = new TypeToken<ArrayList<HighscoreObject>>(){}.getType();
			highscores = gson.fromJson(line, highscoreArrayListType);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
		System.out.println(highscores);
	}


	@Override
	public String toString() {
		return "HighscoreObject [pName=" + pName + ", passes=" + passes + ", highscore=" + highscore + "]";
	}


	public static void writeHighscore(HighscoreObject highscore) {
		
		highscores.add(highscore);
		Collections.sort(highscores);

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
						if((int) gameObject.getX() / 5 == (int) gameObject1.getX() / 5) {
							passes = getPasses() + 1;
							highscore += gameObject.getSpeed() * gameObject1.getSpeed();
							System.out.println(gameObject.getSpeed());
							System.out.println(gameObject1.getSpeed());
							return;
						}
					}
				}
			}
		}		
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


	@Override
	public int compareTo(HighscoreObject o) {
		if (o.getHighscore() > this.highscore)
			return 1;
		return 0;
	}
}
