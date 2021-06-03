package model;


import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//TOOLKlasse zum instanziieren von Objekten
public class GameObjectFactory {
	
	public final static String BIRD = "Bird";
	public static final String TUBET = "TubeT";
	public static final String TUBEB = "TubeB";
	
	public static GameObject createGameObject(String name, String type, double x, double y, Background background, double speed) {
		
		GameObject result = null;
		
		switch(type) {
		
		case BIRD: 
			Image imgBird = loadImage("img/bird.gif");
			if(imgBird != null) {
				result = new Bird(name, x, y, imgBird.getWidth(null), imgBird.getHeight(null), imgBird, background, speed);
			}
			break;
		case TUBEB:
			Image imgTube = loadImage("img/tube.png");
			if(imgTube != null) {
				result = new Tube(name, x, y, imgTube.getWidth(null), imgTube.getHeight(null), imgTube, background, speed);
			}
			break;
		case TUBET:
			Image imgTubeTop = loadImage("img/tubeTop.png");
			if(imgTubeTop != null) {
				result = new Tube(name, x, y, imgTubeTop.getWidth(null), imgTubeTop.getHeight(null), imgTubeTop, background, speed);
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
