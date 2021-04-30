package gamedevelopment;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FlappyBirdApp extends JFrame{

	
	private JFrame frame;
	private JPanel panel;
	
	//Nach Architekturmuster Model-View-Presenter
	private FlappyBirdCanvas canvas;
	private FlappyBirdPresenter presenter;
	
	public static void main(String[] args) {
		
		FlappyBirdApp window = new FlappyBirdApp(); //erstellt JFrame
		FlappyBirdPresenter presenter = new FlappyBirdPresenter(window); //setzt presenter
		window.setPresenter(presenter); //presenter wird JFrame zugewisen
		window.setVisible(true); //JFrame wird sichtbar gemacht
		
		//try catch einbauen? wie im oceanapp git
			
	}

	public FlappyBirdApp () {
		initialize();
	}
	
	
	protected void setPresenter(FlappyBirdPresenter presenter) {
		this.presenter = presenter;
		
	}




	private void initialize() {
		
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		
		/**
		flowLayout.setHgap(10);
		flowLayout.setVgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		this.add(panel, BorderLayout.NORTH);
		**/
		
		canvas = new FlappyBirdCanvas();
		this.add(canvas, BorderLayout.CENTER);

		
	}
}

