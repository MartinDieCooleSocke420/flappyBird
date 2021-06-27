

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
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
		createNewGame();
		
		}

	private void createNewGame() {
		//Das Spielfeld festlegen (MODEL)
		if(background == null) {
			background = new Background(window.getWidth(),window.getHeight());
			canvas.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
			canvas.setImageObjects(background.getGameObjects()); //übergibt nicht veraenderbare Liste
			syncDifficulty(); //muss nur einmal gemacht werden, da späteres ändern nicht möglich ist
		}
		else //Wenn Spielfeld bereits besteht, nicht neu erstellen, sondern reset
			background.reset();
		
		
		//SpielerFigur einmalig Erstellen
		background.generateBird();

		timer = new Timer(frameTime, e-> {
			if(window.isStarted()) {
				updatePlayer();
				background.generateTube(); 
				background.moveAll();			
				canvas.setHighscore(background.getHighscore().getHighscoreValue());
				
				if(background.isBirdDead()) {
					timer.stop();
					showEndScreen();
				}
				
				canvas.repaint();
			}
		});
		
			timer.start();		
	}

	private void updatePlayer() {
		
		//Da immer auf der Y-Achse addiert wird ist ein Clear nötig, ansonsten unspielbar
		background.getBird().clearDistances();
		
		if(statusTasten.contains(KeyEvent.VK_W) || //fï¿½r WASD
				statusTasten.contains(KeyEvent.VK_UP) || //fï¿½r Pfeiltasten
				statusTasten.contains(KeyEvent.VK_SPACE) //fï¿½r Space
				) {
			background.getBird().setDistanceY(-frameTime);
		}
		
		window.setStatusTasten(statusTasten); 
		
		//gravitation
		background.getBird().setDistanceY(grav);
		
	}
	
	public void showEndScreen() {
		
		/*
		TODO: gridbag layout spaces vergrößern
			gidwid
		*/
					
		JDialog endScreen = new JDialog();
		endScreen.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		
		gbc.gridy = 0;
		gbc.gridx = 2;
		JLabel gameOver = new JLabel("GameOver");
		gameOver.setFont(new Font("Serif",Font.BOLD,30));
		gameOver.setForeground(Color.RED);
		endScreen.add(gameOver,gbc);
			
		background.addHighscore();
		
		JTextField playerName = new JTextField(10);
		playerName.setForeground(Color.BLACK);
		playerName.setBackground(Color.WHITE);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 3;
		endScreen.add(new JLabel("Namen eingeben: "), gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		endScreen.add(new JLabel("Geschaffte Tubes: " + background.getHighscore().getPasses()), gbc);
			
		gbc.gridx = 3;
		gbc.gridy = 1;
		endScreen.add(new JLabel("Highscore: " + background.getHighscore().getHighscoreValue()), gbc);

		gbc.gridy = 3;
		gbc.gridy = 0;
		endScreen.add(new JLabel("HighscorePlatz: " + background.getHighscore().getHighscoreList().getHighscorePlacement(background.getHighscore()) ), gbc);
	
			
		JButton restart = new JButton("RESTART");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					background.setHighscoreName(playerName.getText());
					background.writeHighscore();
					createNewGame();
					endScreen.setVisible(false);	
				}});

			
		JButton end = new JButton("END GAME");
		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setHighscoreName(playerName.getText());
				background.writeHighscore();
				System.exit(0);
				}
			});
			
		gbc.gridx = 3;
		gbc.gridy = 3;
		endScreen.add(playerName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		restart.setBackground(Color.GREEN);
		endScreen.add(restart, gbc);
			
		gbc.gridx = 5;
		gbc.gridy = 5;
		end.setBackground(Color.RED);
		endScreen.add(end, gbc);
			
		endScreen.setSize(500, 500);
		endScreen.setResizable(false);
		endScreen.setLocationRelativeTo(null);
		endScreen.setModal(true);
		endScreen.setUndecorated(true);
		endScreen.setVisible(true);		
		
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

	public String[][] getHighscoreArray() {
		return background.getHighscore().getHighscoreArray();
	}

	public void readHighscores() {
		background.getHighscore().getHighscoreList().readHighscores();
	}
}
