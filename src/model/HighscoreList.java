package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;


public class HighscoreList {
	//ohne AbstractTableModel, da ansonsten probleme mit gson

	private final String savePath = "highscores.json"; 
	private ArrayList<HighscoreObject> highscores = new ArrayList<>();
	
	public ArrayList<HighscoreObject> getHighscores() {
		return highscores;
	}
	
	/**
	 * Liest den Highscore aus den lokalen Daten, fragt beim Server an ob dieser einen Highscore hat
	 * wenn ein Server datenzurücksendet, werden diese verwendet. Ansonsten die lokalen
	 * 
	 */
	public void readHighscores() {
		
		HighscoreList highscoreListLocal = null;
		

		try (BufferedReader fr = new BufferedReader(new FileReader(savePath))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = fr.readLine()) != null)
				sb.append(line);
			Gson gson = new Gson();
			
			highscoreListLocal = gson.fromJson(sb.toString(), HighscoreList.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException  e) {
			e.printStackTrace();
		}
		
		//Wenn die eingelesene hl gültig ist, soll sie alst objektatribut festgelegt werden
		if(highscoreListLocal != null) {
			HighscoreObject.highscoreList = highscoreListLocal;				
		}	
		
		//TODO: unschön mit .join direkt nach dem starten
		//Ueberprueft ob die eingelesene Highscorelist mit dem Server uebereinstimmt
		ClientSendToServer t1 = new ClientSendToServer("Verbindung 1");
		t1.start();
		
		try {
			t1.join();
			if(t1.getMsgFromServer() != null)
				HighscoreObject.highscoreList = new Gson().fromJson(t1.getMsgFromServer(), HighscoreList.class);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	
	}

	public void writeHighscore() {
				
		//Sortieren der ArrayListe vor dem Abspeichern
		highscores.sort(Comparator.comparing(HighscoreObject::getHighscoreValue));
		Collections.reverse(highscores);

		Gson gson = new Gson();
		String highscoreListAsJson = gson.toJson(this);
			
		try (FileWriter fw = new FileWriter(savePath)) {
			fw.write(highscoreListAsJson);			
		} catch (IOException e) {
			e.printStackTrace();
		}				
		
		
		//Highscore zum Server senden
		ClientSendToServer t1 = new ClientSendToServer("Verbindung 1");
		t1.start();
	}
	
	//TODO: Funktioniert noch nicht, gerade zu müde das zu machen, mach ich morgen
	public int getHighscorePlacement(HighscoreObject highscoreObject) {
		
		//Sortieren der highscores
		highscores.sort(Comparator.comparing(HighscoreObject::getHighscoreValue));
		Collections.reverse(highscores);

		return highscores.indexOf(highscoreObject) + 1;

	}

	
	/**
	 * Wandelt die ArrayList<HighscoreObjects> in ein Array für die Textausgabe in einem JTable um und gibt dieses Array zurück
	 * 
	 * @return ArrayList as String Array
	 */
	public String[][] getHighscoreArray() {
		
		ArrayList<HighscoreObject> hl = highscores;

		String[][] data = new String[highscores.size()][2];
	
		int counter = 0;
		for (HighscoreObject highscoreObject : hl) {
			data[counter][0] = highscoreObject.getName();
			data[counter][1] = Double.toString(highscoreObject.getHighscoreValue());
			counter++;
		}
		
		return data;
		
	}
	
	

	@Override
	public String toString() {
		return "HighscoreList [highscores=" + highscores + "]";
	}

	public void setHighscores(ArrayList<HighscoreObject> highscores) {
		this.highscores = highscores;
	}

	public void add(HighscoreObject highscoreObject) {
		highscores.add(highscoreObject);
	}
}
