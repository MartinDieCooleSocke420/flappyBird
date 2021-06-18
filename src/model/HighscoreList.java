package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class HighscoreList extends AbstractTableModel{

	private ArrayList<HighscoreObject> highscores = new ArrayList<>();
	
	public ArrayList<HighscoreObject> getHighscores() {
		return highscores;
	}

	public void setHighscores(ArrayList<HighscoreObject> highscores) {
		this.highscores = highscores;
	}

	@Override
	public int getRowCount() {
		return highscores.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		/*
		 * 
		 *  Credits an:
		 *  https://www.java-forum.org/thema/eigenes-tablemodel-fuer-arraylist-string.162335/post-1030956
		 * 
		 * 
		 */
		
		HighscoreObject value = highscores.get(rowIndex);		
		if(value != null) {
			switch(columnIndex) {
			case 0:
				return value.getName();
			case 1:
				return value.getHighscore();
			}
		}
		
		return null;
	}

}
