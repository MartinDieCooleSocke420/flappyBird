package model;

import java.awt.Image;

//PlayerObject
public class Bird extends GameObject {
	
	private double distanceY;

	public Bird(String name, double x, double y, double width, double height, Image image, Background background, double speed) {
		super(name, x, y, width, height, image, background, speed);
	}
	
	public void setDistanceY(double frameTime) {
		//berechnet aus der gravitation und frametime eine Geschwindigkeit fuer den player
		distanceY += frameTime * speed/10 ;
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

}
