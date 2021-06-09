package model;

import java.util.List;
import java.util.function.Predicate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

public class Background {

	private double width, height;
	
	private List<GameObject> gameObjects = new ArrayList<>();
	private Bird bird;
	private double[] difficulty;
	private HighscoreObject highscore = new HighscoreObject();
	
	
	public Background( double width, double height) {
		this.width = width;
		this.height = height;	
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public Bird getBird() {
		return bird;
	}

	public void addGameObjects(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	public boolean removeGameObjects(String name) {
		//TODO: implementieren erw�hnt 02d_Java_Swing_OceanApp_Teil3 min 47
		return true;
	}
	
	public List<ImageObject> getGameObjects(){
		return java.util.Collections.unmodifiableList(gameObjects);
		//�bergibt unver�nderbare list damit der View beim zugriff nichts ver�ndern kann
	}

	public void moveAll() {	
		for (GameObject gameObject : gameObjects) {
			gameObject.move();
			
			//kollisionspr�fung f�r den Vogel
			if (gameObject instanceof Bird) {	
				for (GameObject gameObject1 : gameObjects) {
					
					//�berpr�fung ob ein Objekt mit Bird kollidiert
					//ist auf einem Abstrakten level wie von herr Prie� in 03b_Teil4 gezeigt
					//es w�rde hier reichen nur auf Tubes zu pr�fen,
					//durch sp�tere Features kann es jedoch pracktisch sein es so Abstrackt wie m�glich zu implementieren
					if(!(gameObject1 instanceof Bird) && gameObject.intersect(gameObject1)) {
						gameObject.dead = true;		
						checkBirdDeath();
					}
				}
			}
			
			
			
			//entfernen der Toten objekte, um einen concurrent error zu umgehen:
			//Hier zuerst eine liste erstellen von den Objekten die sp�ter removed werden
			

		}
		
		
		
		Predicate<GameObject> myPredicate = (GameObject o) -> (o.dead);
		gameObjects.removeIf(myPredicate);

		highscore.checkHighscore(gameObjects);
		
	}
	
	
	public void checkBirdDeath() {
		if(bird.dead = true) {
			
			JDialog endScreen = new JDialog();
			
			
			
			JTextField playerName = new JTextField(10);
			playerName.setText("Gib deinen Namen ein");
			playerName.setForeground(Color.BLACK);
			playerName.setBackground(Color.WHITE);
			
			endScreen.add(new JLabel("Geschaffte Tubes: " + highscore.getPasses()));
			endScreen.add(new JLabel("Highscore: " + highscore.getHighscore()));
			
			JButton restart = new JButton("RESTART");
			restart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					highscore.setName(playerName.getText());
					HighscoreObject.writeHighscore(highscore);
					endScreen.setVisible(false);
					
				}
			});
			
			JButton end = new JButton("END GAME");
			end.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					highscore.setName(playerName.getText());
					HighscoreObject.writeHighscore(highscore);
					
					System.out.println(HighscoreObject.highscores);
					System.out.println(highscore);
				System.exit(0);
				}
			});
			
			endScreen.add(playerName);
			endScreen.add(restart);
			endScreen.add(end);
			endScreen.setSize(500, 500);
			endScreen.setResizable(false);
			endScreen.setLocationRelativeTo(null);
			endScreen.setModal(true);
			endScreen.setUndecorated(true);
//			endScreen.setLayout(new BoxLayout(endScreen, BoxLayout.Y_AXIS));
//			endScreen.setLayout(new GridLayout(3,1));
			endScreen.setLayout(new GridBagLayout());
			endScreen.setVisible(true);		
		
		}
	}

	public void generateTube() {
		
		
		double abstand = difficulty[0]; //nach wieviel prozent vom canvas eine neue r�hre kommen soll
		double speed = difficulty[1];
		
		boolean toCreate = true;
		
		for (GameObject gameObject : gameObjects) {
			
			//Wenn game Object eine Tube
			if(gameObject.getClass() == Tube.class) {

				//Die vorherigen tubes m�ssen beim abstand spawnen
				if(gameObject.getX() % (width * abstand) != 0) {
					toCreate = false;
				}
				
			}
		}
		
		
		//gap ist hierbei nicht die L�cke sondern eher die h�henverschiebung um welche beide r�hren auf y verschoben wird
		//je gr��er der Multiplikator von random() ist desto gr��er sind die schwankungen
		double gap = Math.random()*350;
		
		if(toCreate) {
			//TODO: Nicht TubeBot und TubeTop aufrufen!!
			GameObject tubeBot = GameObjectFactory.createGameObject("tubeBot", GameObjectFactory.TUBEB, this.getWidth(), this.getHeight()-(400-gap), this, speed);
			GameObject tubeTop = GameObjectFactory.createGameObject("tubeTop", GameObjectFactory.TUBET, this.getWidth(), this.getHeight()-(1500-gap), this, speed);
			gameObjects.add(tubeBot);
			gameObjects.add(tubeTop);
			System.out.println("Tube Generated in Background");
		}
		
	}


	//zum �berpr�fen ob Objekt im 
	public boolean isObjectInBackground(double x, double y, double width, double height) {
		return (x > 0 && x + width < this.width && y > 0 && y + height < this.height);
	}

	public void setDifficulty(double[] difficulty) {
		this.difficulty = difficulty;
		
	}
	
	public void generateBird() {
		bird = (Bird) GameObjectFactory.createGameObject("Player1", 
//				GameObjectFactory.BIRD, 150, 200, this, difficulty[2]);
				GameObjectFactory.BIRD, 150, 200, this, 2);
		gameObjects.add(bird);
	}
	
}
