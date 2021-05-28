package gamedevelopment;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
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
	private int frameTime = 5;
	

	private Set<Integer> statusTasten = new HashSet<Integer>();
	
	
	public FlappyBirdPresenter(FlappyBirdApp window) {
		this.window = window;
		canvas = window.getFlappyBirdCanvas();
	
		background = new Background(window.getWidth(),window.getHeight());
		canvas.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
		
		
		/* 
		 * eine nicht ver�nderbare Liste wird vom Background (Model) an den
		 * FlappyBirdCanvas(View) �bergeben		
		*/
		canvas.setImageObjects(background.getGameObjects());
		//GameObjects implementieren ImageObject daher m�glich
		
		//TODO: einbauen, dass hier ein Wert f�r die bird gravitation �bergeben wird
		background.generatePlayer(5);
		
		timer = new Timer(frameTime, e-> {
			updatePlayer();
			
			//TODO: Einbauen das man die werte z.B. durch regler im View ver�ndern kann
//			erster wert die heufigkeit wie offt etwas spawnd zweiter die geschwindigkeit
			background.generateTube(0.5, 10); 
			background.moveAll();
			canvas.repaint();
		});
		
		timer.start();		
		
	}


	private void updatePlayer() {
		
		//background.getPlayer().ClearDistances(); ? OceanPresenter Z. 64 von prie�
	
		background.getBird().ClearDistances();
		
		if(statusTasten.contains(KeyEvent.VK_W) || //f�r WASD
				statusTasten.contains(KeyEvent.VK_UP) || //f�r Pfeiltasten
				statusTasten.contains(KeyEvent.VK_SPACE) //f�r Space
				) {
			background.getBird().setDistanceY(-frameTime);
		}
		
		//TODO Maussteuerung on linkslick 
		
		window.setStatusTasten(statusTasten); 
		
		//gravitation
//		background.getBird().setDistanceY(grav);
		
	}


	public void addStatusTasten(int gedruckteTaste) {
		 statusTasten.add(gedruckteTaste);
	}
	public void removeStatusTasten(int losgelasseneTaste) {
		statusTasten.remove(losgelasseneTaste);
	}


	private void addKeyListener(KeyAdapter keyAdapter) {
		// TODO Auto-generated method stub
		
	}

}
