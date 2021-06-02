package gamedevelopment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FlappyBirdApp extends JFrame {
	

	private static final long serialVersionUID = 1L;
//	private JPanel panel;
	private static JPanel menu;
	static boolean started = false;
	
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
		
		JButton start = new JButton("START");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				started = true;
				FlappyBirdCanvas fbc = new FlappyBirdCanvas();
				fbc.setVisible(true); //display jframe2
				menu.setVisible(false);
				
				backgroundCanvas.setVisible(true);
			}
		});
		
		JButton highscore = new JButton("HIGHSCORE");
		JButton options = new JButton ("OPTIONS");
		
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
		

	}
