package model;

import java.awt.Image;

//PlayerObject
public class Bird extends GameObject {
	
	private double grav;
	private double distanceY;

	public Bird(String name, double x, double y, double width, double height, Image image, Background background, double grav) {
		super(name, x, y, width, height, image, background);
		this.grav = grav;
	}
	
	public void setDistanceY(double frameTime) {
		
		//berechnet aus der gravitation und frametime eine optimale Geschwindigkeit für den player
		distanceY += frameTime - grav - 1;
		
	}

	@Override
	public void move() {
		
		y += grav;
		
		if(!background.isObjectInBackground(x, y, width, height)) {
			return;
		}
		
		y += distanceY;
		dead = false;
		
		
	}

	public void ClearDistances() {
		distanceY = 0.0;
		
	}

	
}
