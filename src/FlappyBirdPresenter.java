

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
	private double grav = 2; //gravitation mit der Bird Y+ addiert wird
	

	private Set<Integer> statusTasten = new HashSet<Integer>();
	
	
	public FlappyBirdPresenter(FlappyBirdApp window) {
		this.window = window;
		canvas = window.getFlappyBirdCanvas();
		
		//Den Hintergrund festlegen
		background = new Background(window.getWidth(),window.getHeight());
		canvas.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
//		window.setVisible(true);
		
		
		/* 
		 * eine nicht ver�nderbare Liste wird vom Background (Model) an den
		 * FlappyBirdCanvupdatePlayer();
				background.generateTube(); 
				background.moveAll();
				canvas.repaint();as(View) �bergeben		
		*/
		canvas.setImageObjects(background.getGameObjects());
		//GameObjects implementieren ImageObject daher m�glich
		
			background.generateBird();

		
		timer = new Timer(frameTime, e-> {
			if(window.isStarted()) {
				updatePlayer();
				background.generateTube(); 
				background.moveAll();
				canvas.repaint();
			}
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
		
		window.setStatusTasten(statusTasten); 
		
		//gravitation
		background.getBird().setDistanceY(grav);
		
	}


	public void addStatusTasten(int gedruckteTaste) {
		 statusTasten.add(gedruckteTaste);
	}
	public void removeStatusTasten(int losgelasseneTaste) {
		statusTasten.remove(losgelasseneTaste);
	}

	public void syncDifficulty() {
		background.setDifficulty(window.getDifficulty());
	}

}
