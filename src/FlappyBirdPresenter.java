

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import model.Background;
import model.HighscoreObject;


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
	
		/*
		 * TODO:
		 * 	Auslagern in eine eigene methode:
		 * evtl. neuanlegen oder weiterbenutzen
		 * 
		 * mit background.reset
		 * wenn background == null neuen erstellen
		 * 
		 */
		
		createNewGame();
		
		
		}

	private void createNewGame() {
		//Das Spielfeld festlegen (MODEL)
		background = new Background(window.getWidth(),window.getHeight());
		canvas.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
//		window.setVisible(true);
		
		
		/* 
		 * eine nicht verï¿½nderbare Liste wird vom Background (Model) an den
		 * FlappyBirdCanvupdatePlayer();
				background.generateTube(); 
				background.moveAll();
				canvas.repaint();as(View) ï¿½bergeben		
		*/
		canvas.setImageObjects(background.getGameObjects());
		//GameObjects implementieren ImageObject daher mï¿½glich
		
		syncDifficulty();
		background.generateBird();

		/*
		 * if (timer != null timer.stop)
		 * 
		 */
		timer = new Timer(frameTime, e-> {
			if(window.isStarted()) {
				updatePlayer();
				background.generateTube(); 
				background.moveAll();
				
				//background bird.isdead -> timer.stop -> showendscren()
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
		
		//background.getPlayer().ClearDistances(); ? OceanPresenter Z. 64 von prieï¿½
	
		background.getBird().ClearDistances();
		
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
			
//			TODO: Highscore plazierung anzeigen lassen
//			endScreen.add(new JLabel("HighscorePlatz: " + highscore.getHighscorePlacement()), gbc);
			
			/*
			TODO: gridbag layout spaces vergrößern
				gidwid
			*/
			
		JButton restart = new JButton("RESTART");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setHighscoreName(playerName.getText());
				HighscoreObject.writeHighscore(background.getHighscore());
				createNewGame();
				endScreen.setVisible(false);
					
				}
			});
			
		JButton end = new JButton("END GAME");
		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setHighscoreName(playerName.getText());
				HighscoreObject.writeHighscore(background.getHighscore());
					
//				System.out.println(HighscoreList.highscores);
				System.exit(0);
				}
			});
			
		gbc.gridx = 3;
		gbc.gridy = 3;
		endScreen.add(playerName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		endScreen.add(restart, gbc);
			
		gbc.gridx = 5;
		gbc.gridy = 5;
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
}
