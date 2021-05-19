package gamedevelopment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.ImageObject;

//VIEW
public class FlappyBirdCanvas extends JPanel {
	
	//Liste aller zu zeichnenden Objekte, jeweils mit img, X & Y cordinate
	private List<ImageObject> imageObjects = new ArrayList<>();

	public FlappyBirdCanvas() {
		this.setBackground(Color.GRAY);
	}

	// Angelehnt an: https://gitlab.com/winf120/winf120_gra/grundlagen-der-programmierung-2/oceanapp_gra/-/blob/master/src/OceanCanvas.java
	
	
	@Override
	public void paintComponent(Graphics g) {
	
		//TODO: Graphics 2D? Was vorteile evtl eine typumwandlung
		super.paintComponent(g);
		
		//alle imageObjecte hinzufügen
		for (ImageObject imageObject : imageObjects) {
			g.drawImage(imageObject.getImage(), (int) imageObject.getX(), (int) imageObject.getY(), null);
		}
		
	}
	
	public void setImageObjects(List<ImageObject> imageObjects) {
		this.imageObjects = imageObjects;
	}
	
}
