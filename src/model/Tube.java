package model;

import java.awt.Image;

public class Tube extends GameObject {
	
	public static double tubeSpeed = 3;

	public Tube(String name, double x, double y, double width, double height, Image image, Background background) {
		super(name, x, y, width, height, image, background);
	}

	public void move() {
		x += tubeSpeed;		
	}



}