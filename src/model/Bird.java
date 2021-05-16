package model;

import java.awt.Image;

//PlayerObject
public class Bird extends GameObject {
	
	private double speed = 5;

	public Bird(String name, double x, double y, double width, double height, Image image, Background background) {
		super(name, x, y, width, height, image, background);
	}
	//TODO: Evtl. speed in den konstruktor schreiben, und diesen dann von der GameObjectFactory übergeben
	
	public void setDistanceY(int frameTime) {
		this.y += speed * frameTime;
		
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
