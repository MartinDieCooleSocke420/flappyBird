package gamedevelopment;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

import model.Background;


//PRESENTER
public class FlappyBirdPresenter {
	
	private FlappyBirdApp window;
	private FlappyBirdCanvas canvas;
	private Background background;
	
	private Timer timer;
	private int frameTime = 10;
	private int counter = 20; //wieviele röhren es geben soll
	private int grav = 20; //gravitation mit der Bird Y+ addiert wird
	
	private Set<Integer> tastaturEingaben = new HashSet<Integer>();
	
	
	public FlappyBirdPresenter(FlappyBirdApp window) {
		this.window = window;
		canvas = window.getFlappyBirdCanvas();
		
		//Den Hintergrund festlegen
		background = new Background(window.getWidth(),window.getHeight());
		canvas.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
		
		
		/* 
		 * eine nicht veränderbare Liste wird vom Background (Model) an den
		 * FlappyBirdCanvas(View) übergeben		
		*/
		canvas.setImageObjects(background.getGameObjects());
		//GameObjects implementieren ImageObject daher möglich
		
		timer = new Timer(frameTime, e-> {
			updatePlayer(); //player steuerung
			updateTubes(); //background tube bewegung
			
			canvas.repaint();
		});
		
		timer.start();		
		
	}


	private void updateTubes() {
		// TODO Auto-generated method stub
		
	}


	private void updatePlayer() {
		
		//background.getPlayer().ClearDistances(); ? OceanPresenter Z. 64 von prieß

		if(tastaturEingaben.contains(KeyEvent.VK_W) || //für WASD
				tastaturEingaben.contains(KeyEvent.VK_UP) || //für Pfeiltasten
				tastaturEingaben.contains(KeyEvent.VK_SPACE) //für Space
				) {
			background.getBird().setDistanceY(-frameTime);
		}
		
		//TODO Maussteuerung on linkslick 
		
		//gravitation
		background.getBird().setDistanceY(grav);
		
	}

}
