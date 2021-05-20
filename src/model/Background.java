package model;

import java.util.List;
import java.util.function.Predicate;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import java.util.ArrayList;
import java.util.Collection;

public class Background {

	private double width, height;
	
	private List<GameObject> gameObjects = new ArrayList<>();

	private Bird bird;
	
	public Background( double width, double height) {
		this.width = width;
		this.height = height;
	
		bird = (Bird) GameObjectFactory.createGameObject("Player1", 
				GameObjectFactory.BIRD, 150, 200, this);
		gameObjects.add(bird);
		
		//TODO: generateTube zum ersten mal ausführen hier um eine Start tube zu haben?
		
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
		//TODO: implementieren erwähnt 02d_Java_Swing_OceanApp_Teil3 min 47
		return true;
	}
	
	public List<ImageObject> getGameObjects(){
		return java.util.Collections.unmodifiableList(gameObjects);
		//übergibt unveränderbare list damit der View beim zugriff nichts verändern kann
	}

	public void moveAll() {
		
		
		for (GameObject gameObject : gameObjects) {
			gameObject.move();
			
			//kollisionsprüfung für den Vogel
			if (gameObject instanceof Bird) {	
				for (GameObject gameObject1 : gameObjects) {
					
					//überprüfung ob ein Objekt mit Bird kollidiert
					//ist auf einem Abstrakten level wie von herr Prieß in 03b_Teil4 gezeigt
					//es würde hier reichen nur auf Tubes zu prüfen,
					//durch spätere Features kann es jedoch pracktisch sein es so Abstrackt wie möglich zu implementieren
					if(!(gameObject1 instanceof Bird) && gameObject.intersect(gameObject1)) {
						gameObject.dead = true;
					}
				}
			}
			
			
			//entfernen der Toten objekte, um einen concurrent error zu umgehen:
			//Hier zuerst eine liste erstellen von den Objekten die später removed werden
			

		}
		
		
		Predicate<GameObject> myPredicate = (GameObject o) -> (o.dead);
		gameObjects.removeIf(myPredicate);
		
		//TODO: funktioniert nicht
		
		
	}

	public void generateTube() {
		
		double gap = generateGap();
		
		//TODO: Nicht TubeBot und TubeTop aufrufen!!
		//TODO: Random abstand generieren zwischen den Röhren
		GameObject tubeBot = GameObjectFactory.createGameObject("tubeBot", GameObjectFactory.TUBEB, this.getWidth(), this.getHeight()-(300*gap), this);
		GameObject tubeTop = GameObjectFactory.createGameObject("tubeTop", GameObjectFactory.TUBET, this.getWidth(), this.getHeight()-(1500*gap), this);
		gameObjects.add(tubeBot);
		gameObjects.add(tubeTop);
		System.out.println("Tube Generated in Background");
	}

	private double generateGap() {	
		
		
		return (int) Math.random();
	}

	//zum Überprüfen ob Objekt im 
	public boolean isObjectInBackground(double x, double y, double width, double height) {
		return (x > 0 && x + width < this.width && y > 0 && y + height < this.height);
	}
	
}
