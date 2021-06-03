package model;

import java.awt.Image;

public class Tube extends GameObject {
	
	
	//TODO: nochmal �berdenken ob static sein muss
	//evtl vom background �bergeben als klassenattribut
	public double speed;

	public Tube(String name, double x, double y, double width, double height, Image image, Background background,double speed) {
		super(name, x, y, width, height, image, background);
		this.speed = speed;
	}

	public void move() {
	
		x -= speed;	
		dead = false;
//		
//		if(!background.isObjectInBackground(x, y, width, height)) {
//			x += tubeSpeed;		
//			dead = false;
//		}
	}

	public double getSpeed() {
		return speed;
	}

}
