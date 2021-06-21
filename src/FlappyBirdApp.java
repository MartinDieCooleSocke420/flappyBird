import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
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
import javax.swing.table.TableModel;

import model.HighscoreList;
import model.HighscoreObject;
import model.ImageObject;

public class FlappyBirdApp extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private boolean started = false;
	private double difficulty[] =  new double[3]; 
	private JPanel menu = new JPanel(new GridBagLayout());
	
	//Nach Architektur des Model-View-Presenter
	//als objektattribut
	private FlappyBirdCanvas backgroundCanvas = new FlappyBirdCanvas();; //Am ende der View
	private FlappyBirdPresenter presenter; //Am ende der Presenter
	
	//ein collection Set fï¿½r die Steuerung
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
		
		
		//Standart Schwierigkeit:
		difficulty[0] = 10; //roehrenabstand
		difficulty[1] = 10; //roehrenspeed
		difficulty[2] = 2; //birdspeed	
		
		
		initialize();
	}
		
	private void initialize() {		
		presenter = new FlappyBirdPresenter(this); //setzt presenter
		presenter.syncDifficulty();
		
		add(menu);
		
//		add(backgroundCanvas); //TODO: Add eigentlich hier? siehe zeile 122
		
		
		//ereignisbehandlung
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
//		add(menu);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10,10,10,10);

		JButton start = new JButton("START");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				started = true;
				add(backgroundCanvas);  //TODO: WARUM ADD HIER??? 
				menu.setVisible(false);
				backgroundCanvas.setVisible(true);
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 0;
		menu.add(start);

		
		
		//TODO: getHighscoreArray wirft error, in model/HighscoreObject mehr beschrieben		
		JButton highscore = new JButton("HIGHSCORE");
		highscore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog highscoreDialog = new JDialog();
				highscoreDialog.setTitle("HIGHSCORE");
				highscoreDialog.setSize(500, 500);
				highscoreDialog.setResizable(false);
				highscoreDialog.setLocationRelativeTo(null);					
				
				//TESTDATA:
				String[][] testData = {			
			            { "Gib deinen Namen ein", "18.0"},
			            { "Martin", "12.0" },
			            { "Maritn :P", "30.0"},
			            { "sdafsfdgdfg", "6.0"},
			            { "Martin J. Brucker", "132.0"}	               
			        };
			        
			       
		        String[] columnNames = {"Name", "Highscore"};
//		        data [][] = 
				
		    	HighscoreObject.readHighscores();
		    	System.out.println(HighscoreObject.highscoreList);
		    	
				JTable highscores = new JTable(testData, columnNames);
		        highscores.setBounds(30, 40, 200, 300);
		        
				highscoreDialog.add(highscores);
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
				JSlider tubeDistance = new JSlider();
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
				JSlider tubeSpeed = new JSlider();
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
				JSlider birdSpeed = new JSlider();
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
				
				//TODO: richtiges Umwandeln der Sliderwerte (im Background?)
				//TODO: .getValue gibt immer 50 aus, hier muss ein ?changelistener? eingefügt werden
				//https://docs.oracle.com/javase/tutorial/uiswing/components/slider.html
											
				//Speichern Button zum schliesen des JDialogs 
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
