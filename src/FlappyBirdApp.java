import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FlappyBirdApp extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private boolean started = false;
	private double difficulty[] =  new double[3]; 
	private JPanel menu = new JPanel(new GridBagLayout());
	
	//Nach Architektur des Model-View-Presenter
	private FlappyBirdCanvas backgroundCanvas = new FlappyBirdCanvas();; //Am Ende der View
	private FlappyBirdPresenter presenter; //Am Ende der Presenter

	//ein collection Set fuer die Steuerung
	//wird benoetigt, auch wenn eclipse das so nicht sagt!!!!
	private Set<Integer> statusTasten = new HashSet<Integer>();  

	public static void main(String[] args) {
		FlappyBirdApp window = new FlappyBirdApp("FlappyBird by Marvin und Martin"); //erstellt JFrame
		window.setVisible(true); 	
	}

	public FlappyBirdApp (String name) {
		super(name);
		setBounds(0, 0, 1080, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Standard Schwierigkeit:
		difficulty[0] = 10; //roehrenabstand
		difficulty[1] = 30; //roehrenspeed
		difficulty[2] = 20; //birdspeed	
		
		
		initialize();
	}
		
	private void initialize() {		
		presenter = new FlappyBirdPresenter(this); //Setzt presenter
		presenter.syncDifficulty();
		
		add(menu);
		
		//Ereignisbehandlung
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {

			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(e.getID() == KeyEvent.KEY_PRESSED) {
					presenter.addStatusTasten(e.getKeyCode());
					
				}
				else if(e.getID() == KeyEvent.KEY_RELEASED) {
					presenter.removeStatusTasten(e.getKeyCode());	
				}
				return true;
			}
			return false;
		});
		
		
		/*
		 * Generate Menu
		 * 
		 */

		menu.setBackground(Color.DARK_GRAY);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10,10,10,10);

		JButton start = new JButton("START");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				started = true;
				add(backgroundCanvas); 
				menu.setVisible(false);
				backgroundCanvas.setVisible(true);
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 0;
		menu.add(start);

			
		JButton highscore = new JButton("HIGHSCORE");
		highscore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog highscoreDialog = new JDialog();
				highscoreDialog.setTitle("HIGHSCORE");
				highscoreDialog.setSize(500, 500);
				highscoreDialog.setResizable(false);
				highscoreDialog.setLocationRelativeTo(null);					
			        
		        String[] columnNames = {"Name", "Highscore"};
				
		    	presenter.readHighscores();
		    	
				JTable highscores = new JTable(presenter.getHighscoreArray(), columnNames);
		        JScrollPane highscoreScreen = new JScrollPane(highscores);
		        
		        
				highscoreDialog.add(highscoreScreen);
				highscoreDialog.setModal(true);	
				highscoreDialog.setVisible(true);

			}
		});
		constraints.gridx = 5;
		constraints.gridy = 0;
		menu.add(highscore);
		
		
		
		JButton options = new JButton ("OPTIONS");
		options.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
			
			  	JDialog difficultyDialog = new JDialog();
			  	difficultyDialog.setTitle("OPTIONS");
				difficultyDialog.setSize(500, 500);
				difficultyDialog.setResizable(false);
				difficultyDialog.setLocationRelativeTo(null);

				difficultyDialog.setLayout(new GridLayout(0, 1));
				
				JLabel tubeDistanceLbl = new JLabel("Tube Distance");
				JSlider tubeDistance = new JSlider(5, 90);
				difficultyDialog.add(tubeDistanceLbl);
				difficultyDialog.add(tubeDistance);
				tubeDistance.setMinorTickSpacing(10);
				tubeDistance.setMajorTickSpacing(25);
				tubeDistance.setPaintTicks(true);
				tubeDistance.setPaintLabels(true);
				tubeDistance.setValue((int) difficulty[0]);
				tubeDistance.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						difficulty[0] = tubeDistance.getValue();						
					}
				});
				
				
				
				JLabel tubeSpeedLbl = new JLabel("Tube Speed");
				JSlider tubeSpeed = new JSlider(5, 90);
				difficultyDialog.add(tubeSpeedLbl);
				difficultyDialog.add(tubeSpeed);
				tubeSpeed.setMinorTickSpacing(10);
				tubeSpeed.setMajorTickSpacing(25);
				tubeSpeed.setPaintTicks(true);
				tubeSpeed.setPaintLabels(true);
				tubeSpeed.setValue((int) difficulty[1]);
				tubeSpeed.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						difficulty[1] = tubeSpeed.getValue();						
					}
				});
				
				JLabel birdSpeedLbl = new JLabel("Bird Speed");
				JSlider birdSpeed = new JSlider(5, 90);
				difficultyDialog.add(birdSpeedLbl);
				difficultyDialog.add(birdSpeed);
				birdSpeed.setMinorTickSpacing(10);
				birdSpeed.setMajorTickSpacing(25);
				birdSpeed.setPaintTicks(true);
				birdSpeed.setPaintLabels(true);
				birdSpeed.setValue((int) difficulty[2]);
				birdSpeed.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						difficulty[2] = birdSpeed.getValue();						
					}
				});

				JButton speichern = new JButton("Speichern");
				speichern.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						presenter.syncDifficulty();
						difficultyDialog.setVisible(false);
						
					}
				});
				
				difficultyDialog.add(speichern);			
				difficultyDialog.setModal(true);
				difficultyDialog.setVisible(true);
				
			}
		});
		constraints.gridx = 10;
		constraints.gridy = 0;
		menu.add(options);		
		
		
	}
	
	public FlappyBirdCanvas getFlappyBirdCanvas() {
		return backgroundCanvas;
	}

	public void setStatusTasten(Set<Integer> statusTasten) {
		this.statusTasten = statusTasten;
	}

	public double[] getDifficulty() {
		return difficulty;
	}
		
	public boolean isStarted () {
		return started;
	}

}
