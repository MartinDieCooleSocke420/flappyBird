package model;

import java.awt.Image;

//PlayerObject
public class Bird extends GameObject {
	
	private double speed;
	private double distanceY;

	public Bird(String name, double x, double y, double width, double height, Image image, Background background, double speed) {
		super(name, x, y, width, height, image, background);
		this.speed = speed;
		dead = false;
	}
	
	public void setDistanceY(double frameTime) {
		//berechnet aus der gravitation und frametime eine optimale Geschwindigkeit fr den player
		distanceY += +frameTime * speed - 1;
	}

	@Override
	public void move() {
		if(!background.isObjectInBackground(x, y, width, height)) {
			dead = true;
			return;
		}
		
		y += distanceY;
		dead = false;		
	}

	public void ClearDistances() {
		distanceY = 0.0;
		
	}

	@Override
	protected double getSpeed() {
		return speed;
	}	
}
