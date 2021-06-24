package model;

import java.awt.Image;

public class Tube extends GameObject {

	public Tube(String name, double x, double y, double width, double height, Image image, Background background,double speed) {
		super(name, x, y, width, height, image, background, speed/3);
	}

	public void move() {
	
		x -= speed;	
		dead = false;
	}

}
