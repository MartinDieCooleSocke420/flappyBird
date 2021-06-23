package model;

import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;


public class Background {

	private double width, height;
	
	private List<GameObject> gameObjects = new ArrayList<>();
	private Bird bird;
	private double[] difficulty;
	private HighscoreObject highscore = new HighscoreObject();
	
	
	public Background( double width, double height) {
		this.width = width;
		this.height = height;	
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public Bird getBird() {
		return bird;
	}

	public void addGameObjects(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	public List<ImageObject> getGameObjects(){
		return java.util.Collections.unmodifiableList(gameObjects);	}

	public void moveAll() {	
		for (GameObject gameObject : gameObjects) {
			gameObject.move();
			
			//kollisionspruefung fuer den Vogel
			if (gameObject instanceof Bird) {	
				for (GameObject gameObject1 : gameObjects) {
					
					//Ueberpruefung ob ein Objekt mit Bird kollidiert
					//ist auf einem Abstrakten level wie von Herr Prieïss in 03b_Teil4 gezeigt
					//es wuerde hier reichen nur auf Tubes zu pruefen,
					//durch spaetere Features kann es jedoch pracktisch sein es so Abstrackt wie moeglich zu implementieren
					if(!(gameObject1 instanceof Bird) && gameObject.intersect(gameObject1)) {
						gameObject.dead = true;	
					}
				}
			}			
		}
		
		//Entfernt die toten GameObjects, welche kollidiert sind aus der GameObject list
		Predicate<GameObject> myPredicate = (GameObject o) -> (o.dead);
		gameObjects.removeIf(myPredicate);

		
		highscore.checkHighscore(gameObjects);		
	}
	
	public void generateTube() {
		
		
		double abstand = difficulty[0] * 100; //nach wieviel pxl vom canvas eine neue rï¿½hre kommen soll
		double speed = difficulty[1];
		
		boolean toCreate = true;
		
		
		for (GameObject gameObject : gameObjects) {
			if(gameObject.getClass() == Tube.class) {

				//Die vorherigen tubes muessen beim abstand zum rechtsn rand (width ist da max) spawnen
				if(width - gameObject.getX() < abstand) {
					toCreate = false;
				}
				
			}
		}
		
		
		//gap ist hierbei nicht die Luecke sondern eher die hoehenverschiebung um welche beide roehren auf y verschoben wird
		//je groesser der Multiplikator von random() ist desto groesser sind die schwankungen
		double gap = Math.random()*350;
		
		if(toCreate) {
			GameObject tubeBot = GameObjectFactory.createGameObject("tubeBot", GameObjectFactory.TUBEB, this.getWidth(), this.getHeight()-(400-gap), this, speed);
			GameObject tubeTop = GameObjectFactory.createGameObject("tubeTop", GameObjectFactory.TUBET, this.getWidth(), this.getHeight()-(1500-gap), this, speed);
			gameObjects.add(tubeBot);
			gameObjects.add(tubeTop);
		}
		
	}
	
	public void reset() {
		gameObjects.removeAll(gameObjects);
		highscore = new HighscoreObject();
		bird = null;
	}
	
	public void generateBird() {
		bird = (Bird) GameObjectFactory.createGameObject("Player1", 
				GameObjectFactory.BIRD, 150, 200, this,  difficulty[2]);
		gameObjects.add(bird);
	}
	
	public boolean isObjectInBackground(double x, double y, double width, double height) {
		return (x > 0 && x + width < this.width && y > 0 && y + height < this.height);
		
	}
	
	public void writeHighscore() {
		HighscoreObject.writeHighscore(highscore.getHighscoreList());
		
	}
	
	//Setter & Getter

	public void setDifficulty(double[] difficulty) {
		this.difficulty = difficulty;
	}
	
	public boolean isBirdDead() {
		return bird.dead;
	}
	

	public HighscoreObject getHighscore() {
		return highscore;
	}

	public void setHighscoreName(String text) {
		highscore.setName(text);
	}

	public void addHighscore() {
		highscore.getHighscoreList().add(highscore);
		
	}
	
}
