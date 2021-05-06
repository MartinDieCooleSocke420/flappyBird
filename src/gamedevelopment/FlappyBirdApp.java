package gamedevelopment;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jdk.nashorn.internal.ir.ReturnNode;

public class FlappyBirdApp extends JFrame{

	
	private JFrame frame;
	private JPanel panel;
	
	//Nach Architekturmuster Model-View-Presenter
	private FlappyBirdCanvas backgroundCanvas; //Am ende der View
	private FlappyBirdPresenter presenter; //Am ende der Presenter
	
	public static void main(String[] args) {
		
		FlappyBirdApp window = new FlappyBirdApp(); //erstellt JFrame
		FlappyBirdPresenter presenter = new FlappyBirdPresenter(window); //setzt presenter
		window.setPresenter(presenter); //presenter wird JFrame zugewisen
		window.setVisible(true); //JFrame wird sichtbar gemacht
		
		//TODO: try catch einbauen? wie im oceanapp git
			
	}

	public FlappyBirdApp () {
		initialize();
	}
	


	/**
	*	JFrame inizialisieren
	*/
	
	private void initialize() {
		
		this.setBounds(0, 0, 1080, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		
		/**
		 * Noch aus Pries beispiel, glaue nicht mehr nötig?
		flowLayout.setHgap(10);
		flowLayout.setVgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		this.add(panel, BorderLayout.NORTH);
		**/
		
		backgroundCanvas = new FlappyBirdCanvas();
		this.add(backgroundCanvas, BorderLayout.CENTER);

		
	}
	
	private void setPresenter(FlappyBirdPresenter presenter) {
		this.presenter = presenter;
		
	}

	public FlappyBirdCanvas getFlappyBirdCanvas() {
		// TODO Auto-generated method stub
		return backgroundCanvas;
	}

	
}

