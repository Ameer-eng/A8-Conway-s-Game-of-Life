package gameOfLife;

import javax.swing.SwingUtilities;

public class GameOfLifeRunner extends Thread {

	private IGameOfLifeModel model;
	private long delay;
	private boolean isRunning;
	
	public GameOfLifeRunner(IGameOfLifeModel model, long delay) {
		this.model = model;
		this.delay = delay;
		isRunning = true;
	}

	public void halt() {
		isRunning = false;
	}

	public void run() {
		while (isRunning) {
			try {
				Thread.sleep(delay);
			} catch(InterruptedException e) {
			}
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					model.advanceGame();
				}
			});
		}
	}
	
}
