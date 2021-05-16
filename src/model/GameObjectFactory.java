package model;


import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//TOOLKlasse zum instanziieren von Objekten
public class GameObjectFactory {
	
	public final static String BIRD = "Bird";
	
	public static GameObject createGameObject(String name, String type, double x, double y, Background background) {
		
		GameObject result = null;
		
		switch(type) {
		
		case BIRD: 
			Image imgBird = loadImage("img/bird.png");
			if(imgBird != null) {
				result = new Bird(name, x, y, imgBird.getWidth(null), imgBird.getHeight(null), imgBird, background, 3);
			}
			break;
		
		}
		
		return result;
	}

	private static Image loadImage(String path) {
		Image result = null;
		
		try {
			result = ImageIO.read(new File(path));		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
