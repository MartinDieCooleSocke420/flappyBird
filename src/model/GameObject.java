package model;

import java.awt.Image;

public class GameObject implements ImageObject{

	private String name;
	private Image image;
	
	//protected damit aus subklasse zugegriffen werden kann (keine getter/setter nötig)
	protected double x, y, width, height;
	protected Background background;
	
	
	public GameObject(String name, double x, double y, double width, double height, Image image, Background background) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.background = background;
	}
	
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public Image getImage() {
		return image;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public String getName() {
		return name;
	}

}
