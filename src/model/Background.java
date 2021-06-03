package model;

import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.Collection;

public class Background {

	private double width, height;
	
	private List<GameObject> gameObjects = new ArrayList<>();

	private Bird bird;
	private HighscoreObject highscore = new HighscoreObject();

	private double[] difficulty;
	
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
	
	public boolean removeGameObjects(String name) {
		//TODO: implementieren erw�hnt 02d_Java_Swing_OceanApp_Teil3 min 47
		return true;
	}
	
	public List<ImageObject> getGameObjects(){
		return java.util.Collections.unmodifiableList(gameObjects);
		//�bergibt unver�nderbare list damit der View beim zugriff nichts ver�ndern kann
	}

	public void moveAll() {
		
		
		for (GameObject gameObject : gameObjects) {
			gameObject.move();
			
			//kollisionspr�fung f�r den Vogel
			if (gameObject instanceof Bird) {	
				for (GameObject gameObject1 : gameObjects) {
					
					//�berpr�fung ob ein Objekt mit Bird kollidiert
					//ist auf einem Abstrakten level wie von herr Prie� in 03b_Teil4 gezeigt
					//es w�rde hier reichen nur auf Tubes zu pr�fen,
					//durch sp�tere Features kann es jedoch pracktisch sein es so Abstrackt wie m�glich zu implementieren
					if(!(gameObject1 instanceof Bird) && gameObject.intersect(gameObject1)) {
						gameObject.dead = true;
					}
				}
			}
			
			
			//entfernen der Toten objekte, um einen concurrent error zu umgehen:
			//Hier zuerst eine liste erstellen von den Objekten die sp�ter removed werden
			

		}
//		
//		if(highscore ) 
//			highscore.checkHighscore(gameObjects);
		
		Predicate<GameObject> myPredicate = (GameObject o) -> (o.dead);
		gameObjects.removeIf(myPredicate);
		
	}

	public void generateTube() {
		
		
		double abstand = difficulty[0]; //nach wieviel prozent vom canvas eine neue r�hre kommen soll
		double speed = difficulty[1];
		
		boolean toCreate = true;
		
		for (GameObject gameObject : gameObjects) {
			
			//Wenn game Object eine Tube
			if(gameObject.getClass() == Tube.class) {

				//Die vorherigen tubes m�ssen beim abstand spawnen
				if(gameObject.getX() % (width * abstand) != 0) {
					toCreate = false;
				}
				
			}
		}
		
		
		//gap ist hierbei nicht die L�cke sondern eher die h�henverschiebung um welche beide r�hren auf y verschoben wird
		//je gr��er der Multiplikator von random() ist desto gr��er sind die schwankungen
		double gap = Math.random()*350;
		
		if(toCreate) {
			//TODO: Nicht TubeBot und TubeTop aufrufen!!
			GameObject tubeBot = GameObjectFactory.createGameObject("tubeBot", GameObjectFactory.TUBEB, this.getWidth(), this.getHeight()-(400-gap), this, speed);
			GameObject tubeTop = GameObjectFactory.createGameObject("tubeTop", GameObjectFactory.TUBET, this.getWidth(), this.getHeight()-(1500-gap), this, speed);
			gameObjects.add(tubeBot);
			gameObjects.add(tubeTop);
			System.out.println("Tube Generated in Background");
		}
		
	}


	//zum �berpr�fen ob Objekt im 
	public boolean isObjectInBackground(double x, double y, double width, double height) {
		return (x > 0 && x + width < this.width && y > 0 && y + height < this.height);
	}

	public void setDifficulty(double[] difficulty) {
		this.difficulty = difficulty;
		
	}
	
	public void generateBird() {
		bird = (Bird) GameObjectFactory.createGameObject("Player1", 
				GameObjectFactory.BIRD, 150, 200, this, difficulty[2]);
		gameObjects.add(bird);
	}
	
}
