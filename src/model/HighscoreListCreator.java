package model;


public class HighscoreListCreator {

	public static void main(String[] args) {
		HighscoreList highscoreList1 = new HighscoreList();
		HighscoreObject hO = new HighscoreObject();
		hO.setName("Martin");
		highscoreList1.highscores.add(hO);
	
		HighscoreObject.writeHighscore(hO);
		
	}
}
