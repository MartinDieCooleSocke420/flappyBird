package model;

import java.util.ArrayList;


public class HighscoreList {
	//ohne AbstractTableModel, da ansonsten probleme mit gson

	private ArrayList<HighscoreObject> highscores = new ArrayList<>();
	
	public ArrayList<HighscoreObject> getHighscores() {
		return highscores;
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
