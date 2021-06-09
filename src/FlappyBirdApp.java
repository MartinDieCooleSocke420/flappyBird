import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FlappyBirdApp extends JFrame {
	

	private static final long serialVersionUID = 1L;
//	private JPanel panel;
	private static JPanel menu;
	private boolean started = false;
	private double difficulty[] =  new double[3]; 
	
	//Nach Architektur des Model-View-Presenter
	private static FlappyBirdCanvas backgroundCanvas; //Am ende der View
	private FlappyBirdPresenter presenter; //Am ende der Presenter
	
	//ein collection Set f�r die Steuerung
	private Set<Integer> statusTasten = new HashSet<Integer>();
		
	public static void main(String[] args) {
		
		FlappyBirdApp window = new FlappyBirdApp(); //erstellt JFrame
		FlappyBirdPresenter presenter = new FlappyBirdPresenter(window); //setzt presenter
		window.setPresenter(presenter); //presenter wird JFrame zugewisen
		window.setVisible(true); //JFrame wird sichtbar gemacht
		backgroundCanvas.setVisible(false);
		
		menu = new JPanel(new GridBagLayout());
		
//		menu.setBackground(Color.GRAY);
//		menu.setVisible(false);
		
		// da Probleme beim starten menü geskipt, ENTFERNEN
//		 backgroundCanvas.setVisible(true);
//		 window.started = true;
				
		JButton start = new JButton("START");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.started = true;
				FlappyBirdCanvas fbc = new FlappyBirdCanvas();
				fbc.setVisible(true); //display jframe2
				menu.setVisible(false);
				backgroundCanvas.setVisible(true);
			}
		});
				
		JButton highscore = new JButton("HIGHSCORE");
		highscore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		//NACH DER IMPLEMENTATION VON OPTION ENTFERNEN:
		window.difficulty[0] = 0.5; //röhrenabstand
		window.difficulty[1] = 9; //röhrenspeed
		window.difficulty[2] = 2; //birdspeed
		
		presenter.syncDifficulty();
		
		JButton options = new JButton ("OPTIONS");
		options.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Schwierigkeitseinstellungen, JDialog?
				//difficulty[] befüllen 
				// 1. Platz im Array, häufigkeit der Rören
				// 2. Platz im Array, geschwindigkeit der Rören
				// 3. Platz im Array, geschwindigkeit der Röre (hierraus wird auch die gravitation berechnet)
				
				//EVTL MIT SCHIEBEREGLERN:
				// https://stackoverflow.com/questions/9815506/add-components-to-jdialog
				
				
			}
		});
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.insets = new Insets(10,10,10,10);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		menu.add(start);

		constraints.gridx = 5;
		constraints.gridy = 0;
		menu.add(highscore);
		
		constraints.gridx = 10;
		constraints.gridy = 0;
		menu.add(options);
	

		window.add(menu);
	
	}

	public FlappyBirdApp () {
		initialize();
	}
	
	//	JFrame inizialisieren
	
	
	private void initialize() {
		
		this.setBounds(0, 0, 1080, 720);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		//TODO: hier startmen� punkt - als modal (muss bearbeitet werden) jDialog
		backgroundCanvas = new FlappyBirdCanvas();
		this.add(backgroundCanvas, BorderLayout.CENTER);
		
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

		
	}
	
	private void setPresenter(FlappyBirdPresenter presenter) {
		this.presenter = presenter;
		
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
