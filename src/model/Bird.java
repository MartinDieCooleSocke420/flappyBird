package model;

import java.awt.Image;

//PlayerObject
public class Bird extends GameObject {
	
	private double speed;
	private double distanceY;

	public Bird(String name, double x, double y, double width, double height, Image image, Background background, double speed) {
		super(name, x, y, width, height, image, background);
		this.speed = speed;
	}
	
	public void setDistanceY(double frameTime) {
		distanceY += speed * frameTime;
		
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void move() {
		y += distanceY;
		
	}

	public void ClearDistances() {
		distanceY = 0.0;
		
	}

	
}
