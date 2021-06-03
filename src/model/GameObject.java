package model;

import java.awt.Image;

public abstract class GameObject implements ImageObject{

	private String name;
	private Image image;
	
	//protected damit aus subklasse zugegriffen werden kann (keine getter/setter n�tig)
	protected double x, y, width, height;
	protected Background background;
	protected boolean dead;
	
	
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
	
	public boolean intersect(GameObject gameObject) {
		
		//der False-Fall wird gepr�ft also wenn die Objekte sich nicht �berschneiden
		//eine �bersichtlichere L�sung w�re ein IF-Else, abgek�rzt so darstellbar:
		return !(this.x + this.width < gameObject.x || this.y + this.height < gameObject.y
				|| gameObject.x + gameObject.width < this.x || gameObject.y + gameObject.height < this.y);

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

	public abstract void move();

	protected abstract double getSpeed();



}
