package gamedevelopment;

import model.Background;

public class FlappyBirdPresenter {
	
	private FlappyBirdApp window;
	private FlappyBirdCanvas canvas;
	private Background background;
	
	public FlappyBirdPresenter(FlappyBirdApp window) {
		this.window = window;
		canvas = window.getFlappyBirdCanvas();
	}

}
