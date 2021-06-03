package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HighscoreObject {
	
	private String pName;
	private int passes;
	private int highscore;
	
	static ArrayList<HighscoreObject> highscores = new ArrayList<>();
	static String savePath = "highscores.json"; 
	
	public HighscoreObject () {
		readHighscores();
	}

	
	private void readHighscores() {
		
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
		
		
	}


	public void writeHighscore() {
		
		Gson gson = new Gson();
		
		if(!highscores.contains(this))
				highscores.add(this);
		
		String highscoresAsJson = gson.toJson(highscores);
		
		try (FileWriter fw = new FileWriter(HighscoreObject.savePath)) {
			fw.write(highscoresAsJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void checkHighscore(List<GameObject> gameObjects) {
		for (GameObject gameObject : gameObjects) {
			if(gameObject instanceof Tube) {
				for (GameObject gameObject1 : gameObjects) {
					if(gameObject1 instanceof Bird) {
						if(gameObject.getX() == gameObject1.getX()) {
							passes++;
							highscore += gameObject.getSpeed() * gameObject1.getSpeed();
						}
					}
				}
			}
		}		
	}
	
	public double getHighscore() {
		return highscore;
	}
}
