package gamedevelopment;

import java.awt.Dimension;

import model.Background;

//PRESENTER
public class FlappyBirdPresenter {
	
	private FlappyBirdApp window;
	private FlappyBirdCanvas canvas;
	private Background background;
	
	public FlappyBirdPresenter(FlappyBirdApp window) {
		this.window = window;
		canvas = window.getFlappyBirdCanvas();
		
		//Den Hintergrund festlegen
		background = new Background(window.getWidth(),window.getHeight());
		canvas.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
		
		
		/* 
		 * eine nicht ver�nderbare Liste wird vom Background (Model) an den
		 * FlappyBirdCanvas(View) �bergeben		
		*/
		canvas.setImageObjects(background.getGameObjects());
		//GameObjects implementieren ImageObject daher m�glich
		
		canvas.repaint();
	}

}
