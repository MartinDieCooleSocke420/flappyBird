package gamedevelopment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.ImageObject;

//VIEW
public class FlappyBirdCanvas extends JPanel {
	
	//Liste aller zu zeichnenden Objekte, jeweils mit img, X & Y cordinate
	private List<ImageObject> imageObjects = new ArrayList<>();
	private Image backgroundImg = null;
	
	public FlappyBirdCanvas() {


		//Hintergrundbild laden
		try {
			backgroundImg = ImageIO.read(new File("img/background.png"));		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

		
	}

	// Angelehnt an: https://gitlab.com/winf120/winf120_gra/grundlagen-der-programmierung-2/oceanapp_gra/-/blob/master/src/OceanCanvas.java
	
	
	@Override
	public void paintComponent(Graphics g) {
				
		super.paintComponent(g);
		
		if(backgroundImg != null)
			g.drawImage(backgroundImg, 0, 0, null);
	
		
//			g.drawString(Double.toString(highscore), 620, 20);
				
		//alle imageObjecte hinzuf�gen
		for (ImageObject imageObject : imageObjects) {
			g.drawImage(imageObject.getImage(), (int) imageObject.getX(), (int) imageObject.getY(), null);
		}
		
	}
	
	public void setImageObjects(List<ImageObject> imageObjects) {
		this.imageObjects = imageObjects;
	}
}
