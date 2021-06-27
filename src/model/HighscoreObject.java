package model;

import java.util.List;

public class HighscoreObject {
	
	private String pName;
	private int passes = 0;
	private double highscore = 0;
	
	public static HighscoreList highscoreList = new HighscoreList();

	/**
	 * Ueberprueft ob der Spieler gerade eine Roehre durchquert hat und erhoet beim erfolgreichen durchfliegen
	 * die passes und den Highscore
	 * 
	 * @param gameObjects liste aller Gameobjects
	 */
	public void checkHighscore(List<GameObject> gameObjects, double[] difficulty) {
		for (GameObject gameObject : gameObjects) {
			if(gameObject instanceof Tube) {
				for (GameObject gameObject1 : gameObjects) {
					if(gameObject1 instanceof Bird) {
						//Da es passieren kann, dass je nach Schwierigkeitseinstellungen die Roehren X und Vogel X koordinaten nie == sind
						// wird hier geprüft ob ein gewisser abstand vorhanden ist && ob die Roehren annehrernd in der naehre sind 
						if(gameObject1.getX() - gameObject.getX() < 0 && gameObject1.getX() - gameObject.getX() > -difficulty[1]/2 ) {
							passes = getPasses() + 1;
							highscore += (difficulty[1] * difficulty[2] - difficulty[0]) /20;
							return;
						}
					}
				}
			}
		}		
	}
	

	//hashCode && Equals

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
	
	//Getters && Setters

	public double getHighscoreValue() {
		return highscore;
	}
	
	public String[][] getHighscoreArray() {
		return highscoreList.getHighscoreArray();
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
