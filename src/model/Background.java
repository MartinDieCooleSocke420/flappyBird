package model;

import java.util.List;

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
				GameObjectFactory.BIRD, 2, 200, this);
		gameObjects.add(bird);
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
		
		//bewegt jede röhre mit dem übergebenen speed
		for (GameObject gameObject : gameObjects) {
			gameObject.move();
		}
	}


}
