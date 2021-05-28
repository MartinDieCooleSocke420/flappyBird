package model;

import java.awt.Image;

public class Tube extends GameObject {
	
	
	//TODO: nochmal überdenken ob static sein muss
	//evtl vom background übergeben als klassenattribut
	public double tubeSpeed;

	public Tube(String name, double x, double y, double width, double height, Image image, Background background, double tubeSpeed) {
		super(name, x, y, width, height, image, background);
		this.tubeSpeed = tubeSpeed;
	}

	public void move() {
	
		x -= tubeSpeed;	
		dead = false;
//		
//		if(!background.isObjectInBackground(x, y, width, height)) {
//			x += tubeSpeed;		
//			dead = false;
//		}
	}



}
