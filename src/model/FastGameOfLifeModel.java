package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FastGameOfLifeModel implements IGameOfLifeModel {
	private int lowBirth;
	private int highBirth;
	private int lowSurvive;
	private int highSurvive;
	private GameOfLifeRunner runner;
	private boolean isTorus;
	private boolean[][] states;
	private List<GameOfLifeModelListener> listeners;

	/*
	 * Constructor
	 */
	public FastGameOfLifeModel() {
		listeners = new ArrayList<GameOfLifeModelListener>();

		// Set default starting values for properties.
		states = new boolean[10][10];
		lowBirth = 3;
		highBirth = 3;
		lowSurvive = 2;
		highSurvive = 3;
		isTorus = false;
	}

	/*
	 * Getters.
	 */

	@Override
	public boolean[][] getStates() {
		boolean[][] statesClone = new boolean[states.length][states[0].length];
		for (int x = 0; x < states.length; x++) {
			statesClone[x] = states[x].clone();
		}
		return statesClone;
	}

	@Override
	public int getWidth() {
		return states.length;
	}

	@Override
	public int getHeight() {
		return states[0].length;
	}

	/*
	 * Setters.
	 */

	@Override
	public void setLowBirth(int lowBirth) {
		if (lowBirth < 0 || lowBirth > 8) {
			throw new IllegalArgumentException(
					"Low birth threshold must be between 1 and 8 inclusive.");
		}

		this.lowBirth = lowBirth;
	}

	@Override
	public void setHighBirth(int highBirth) {
		if (highBirth < 0 || highBirth > 8) {
			throw new IllegalArgumentException(
					"High birth threshold must be between 1 and 8 inclusive.");
		}

		this.highBirth = highBirth;
	}

	@Override
	public void setLowSurvive(int lowSurvive) {
		if (lowSurvive < 0 || lowSurvive > 8) {
			throw new IllegalArgumentException(
					"Low survive threshold must be between 1 and 8 inclusive.");
		}

		this.lowSurvive = lowSurvive;
	}

	@Override
	public void setHighSurvive(int highSurvive) {
		if (highSurvive < 0 || highSurvive > 8) {
			throw new IllegalArgumentException(
					"High survive threshold must be between 1 and 8 inclusive.");
		}

		this.highSurvive = highSurvive;
	}

	@Override
	public void setTorus(boolean isTorus) {
		this.isTorus = isTorus;

		if (isTorus) {
			notifyListeners("Torus turned on");
		} else {
			notifyListeners("Torus turned off");
		}
	}

	@Override
	public void toggleSquare(int x, int y) {
		if (x < 0 || x >= states.length || y < 0 || y >= states[x].length) {
			throw new IllegalArgumentException(
					"X and y coordinates must be within the dimensions of the model.");
		}

		states[x][y] = !states[x][y];
		notifyListeners("States changed");
	}

	/*
	 * Fills the grid randomly with each square having the same probability to
	 * be filled.
	 */
	@Override
	public void fillGridRandomly(double probability) {
		if (probability < 0 || probability > 1) {
			throw new IllegalArgumentException(
					"Probability must be between 0 and 1 inclusive.");
		}

		Random rng = new Random();
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				states[x][y] = rng.nextDouble() < probability;
			}
		}

		notifyListeners("States changed");
	}

	/*
	 * Advances the game according to the rules.
	 */
	@Override
	public void advanceGame() {
		// Calculate the future board state.
		boolean[][] future = new boolean[states.length][states[0].length];
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				int liveNeighborCount = liveNeighborsCount(x, y);
				future[x][y] = states[x][y]
						? liveNeighborCount >= lowSurvive
								&& liveNeighborCount <= highSurvive
						: liveNeighborCount >= lowBirth
								&& liveNeighborCount <= highBirth;
			}
		}

		// Update the board.
		states = future;

		notifyListeners("States changed");
	}

	/*
	 * Helper method to count the number of live neighbors a spot has.
	 */
	private int liveNeighborsCount(int x, int y) {
		int count = 0;
		int width = states.length;
		int height = states[0].length;

		// Check above.
		if (y != 0 && states[x][y - 1]) {
			count++;
		}

		// Check below.
		if (y != height - 1 && states[x][y + 1]) {
			count++;
		}

		// Check to the right.
		if (x != width - 1 && states[x + 1][y]) {
			count++;
		}

		// Check to the left.
		if (x != 0 && states[x - 1][y]) {
			count++;
		}

		// Check upper left diagonal.
		if (y != 0 && x != 0 && states[x - 1][y - 1]) {
			count++;
		}

		// Check lower right diagonal.
		if (y != height - 1 && x != width - 1 && states[x + 1][y + 1]) {
			count++;
		}

		// Check upper right diagonal.
		if (y != 0 && x != width - 1 && states[x + 1][y - 1]) {
			count++;
		}

		// Check lower left diagonal.
		if (y != height - 1 && x != 0 && states[x - 1][y + 1]) {
			count++;
		}

		// If torus is on do more checks.
		if (isTorus) {
			// Check above.
			if (y == 0 && states[x][height - 1]) {
				count++;
			}

			// Check below.
			if (y == height - 1 && states[x][0]) {
				count++;
			}

			// Check to the right.
			if (x == width - 1 && states[0][y]) {
				count++;
			}

			// Check to the left.
			if (x == 0 && states[width - 1][y]) {
				count++;
			}

			// Check upper left diagonal.
			if (y == 0 && x == 0) {
				if (states[width - 1][height - 1]) {
					count++;
				}
			} else if (y == 0) {
				if (states[x - 1][height - 1]) {
					count++;
				}
			} else if (x == 0) {
				if (states[width - 1][y - 1]) {
					count++;
				}
			}

			// Check lower right diagonal.
			if (y == height - 1 && x == width - 1) {
				if (states[0][0]) {
					count++;
				}
			} else if (y == height - 1) {
				if (states[x + 1][0]) {
					count++;
				}
			} else if (x == width - 1) {
				if (states[0][y + 1]) {
					count++;
				}
			}

			// Check upper right diagonal.
			if (y == 0 && x == width - 1) {
				if (states[0][height - 1]) {
					count++;
				}
			} else if (y == 0) {
				if (states[x + 1][height - 1]) {
					count++;
				}
			} else if (x == width - 1) {
				if (states[0][y - 1]) {
					count++;
				}
			}

			// Check lower left diagonal.
			if (y == height - 1 && x == 0) {
				if (states[width - 1][0]) {
					count++;
				}
			} else if (y == height - 1) {
				if (states[x - 1][0]) {
					count++;
				}
			} else if (x == 0) {
				if (states[width - 1][y + 1]) {
					count++;
				}
			}
		}

		return count;
	}

	/*
	 * Kills all states on the grid.
	 */
	@Override
	public void clearGrid() {
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				states[x][y] = false;
			}
		}

		notifyListeners("States changed");
	}

	/*
	 * Runner Advances game automatically with given delay between advances.
	 * Delay is in milliseconds.
	 */

	@Override
	public void startRunner(long delay) {
		if (delay < 0) {
			throw new IllegalArgumentException("Delay must be positive.");
		}

		runner = new GameOfLifeRunner(this, delay);
		runner.start();

		notifyListeners("Game started");
	}

	@Override
	public void stopRunner() {
		runner.halt();
		try {
			runner.join();
		} catch (InterruptedException e) {
		}

		notifyListeners("Game stopped");
	}

	/*
	 * Changes grid size while maintaining states of current squares.
	 */
	@Override
	public void changeGridSizeTo(int width, int height) {
		// Do nothing if the size is unchanged.
		if (width == states.length && height == states[0].length) {
			return;
		}

		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Width or height is negative");
		}

		boolean[][] newSquares = new boolean[width][height];

		// Old states stay the same.
		for (int x = 0; x < Math.min(states.length, newSquares.length); x++) {
			for (int y = 0; y < Math.min(states[x].length,
					newSquares[x].length); y++) {
				newSquares[x][y] = states[x][y];
			}
		}

		// The rest of the states are unfilled (false) by default so no need to
		// do anything further.

		states = newSquares;

		notifyListeners("States changed");
	}

	@Override
	public void addGameOfLifeModelListener(GameOfLifeModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeGameOfLifeModelListener(GameOfLifeModelListener l) {
		listeners.remove(l);
	}

	private void notifyListeners(String actionCommand) {
		for (GameOfLifeModelListener l : listeners) {
			l.update(actionCommand, this);
		}
	}
}
