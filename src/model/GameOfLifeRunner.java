package model;

import javax.swing.SwingUtilities;

public class GameOfLifeRunner extends Thread implements IGameOfLifeRunner {
	private FastGameOfLifeModel model;
	private long delay;
	private boolean isRunning;
	
	public GameOfLifeRunner(FastGameOfLifeModel model, long delay) {
		this.model = model;
		this.delay = delay;
		isRunning = true;
	}

	@Override
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
