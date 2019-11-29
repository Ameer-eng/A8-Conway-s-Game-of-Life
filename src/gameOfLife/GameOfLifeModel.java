package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameOfLifeModel extends JSpotBoard {

	private List<GameOfLifeModelListener> listeners;

	private int lowBirth;
	private int highBirth;
	private int lowSurvive;
	private int highSurvive;

	public GameOfLifeModel(int width, int height) {
		super(width, height);
		listeners = new ArrayList<GameOfLifeModelListener>();

		// Set default values for thresholds.
		lowBirth = 3;
		highBirth = 3;
		lowSurvive = 2;
		highSurvive = 3;
	}

	public void toggleSpot(Spot spot) {
		spot.toggleSpot();
	}

	public void fillGridRandomly() {
		Random rng = new Random();
		for (Spot s : this) {
			int randNum = rng.nextInt(2);
			if (randNum == 1) {
				s.toggleSpot();
			}
		}
	}

	/*
	 * Advances the game according to the rules.
	 */
	public void advanceGame() {
		int width = getSpotWidth();
		int height = getSpotHeight();

		// Holds information about the future board state.
		int[][] future = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Spot currentSpot = getSpotAt(x, y);
				int liveNeighborCount = liveNeighborsCount(currentSpot);
				if (currentSpot.isEmpty()) {
					if (liveNeighborCount >= lowBirth
							&& liveNeighborCount <= highBirth) {
						future[x][y] = 1;
					}
				} else {
					if (liveNeighborCount < lowSurvive
							|| liveNeighborCount > highSurvive) {
						future[x][y] = -1;
					}
				}
			}
		}

		// Update the board.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (future[x][y] != 0) {
					if (future[x][y] == 1) {
						getSpotAt(x, y).setSpot();
					} else {
						getSpotAt(x, y).clearSpot();
					}
				}
			}
		}
	}

	public void changeGridSizeTo(int size) {
		int oldSize = getSpotHeight();

		// No need to reconstruct if size is same.
		if (size == oldSize) {
			return;
		}

		// Remove all the spots and resize the layout
		removeAll();
		setLayout(new GridLayout(size, size));
		revalidate();
		repaint();

		// Redraw all the spots.
		Spot[][] oldSpotArray = getSpots();
		Spot[][] newSpotArray = new Spot[size][size];

		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH / size,
				DEFAULT_SCREEN_HEIGHT / size);

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				Color bg = ((x + y) % 2 == 0) ? DEFAULT_BACKGROUND_LIGHT
						: DEFAULT_BACKGROUND_DARK;
				newSpotArray[x][y] = new JSpot(bg, DEFAULT_SPOT_COLOR,
						DEFAULT_HIGHLIGHT_COLOR, this, x, y);

				// Spots from the old Spot array which were colored remain
				// colored.
				if (x <= oldSize - 1 && y <= oldSize - 1
						&& !oldSpotArray[x][y].isEmpty()) {
					((JSpot) newSpotArray[x][y]).setSpot();
				}

				((JSpot) newSpotArray[x][y]).setPreferredSize(preferred_size);
				add(((JSpot) newSpotArray[x][y]));
			}
		}
		setSpots(newSpotArray);

		// Need to notify all listeners that the grid has changed
		notifyListeners();
	}

	public void clearGrid() {
		for (Spot s : this) {
			if (!s.isEmpty()) {
				s.clearSpot();
			}
		}
	}

	/*
	 * Helper method to count the number of live neighbors a spot has.
	 */
	private int liveNeighborsCount(Spot spot) {
		int x = spot.getSpotX();
		int y = spot.getSpotY();
		int count = 0;

		// Check above.
		if (y >= 1) {
			if (!getSpotAt(x, y - 1).isEmpty()) {
				count++;
			}
		}

		// Check below.
		if (y <= getSpotHeight() - 2) {
			if (!getSpotAt(x, y + 1).isEmpty()) {
				count++;
			}
		}

		// Check to the right.
		if (x <= getSpotWidth() - 2) {
			if (!getSpotAt(x + 1, y).isEmpty()) {
				count++;
			}
		}

		// Check to the left.
		if (x >= 1) {
			if (!getSpotAt(x - 1, y).isEmpty()) {
				count++;
			}
		}

		// Check upper left diagonal.
		if (y >= 1 && x >= 1) {
			if (!getSpotAt(x - 1, y - 1).isEmpty()) {
				count++;
			}
		}

		// Check lower right diagonal.
		if (y <= getSpotHeight() - 2 && x <= getSpotWidth() - 2) {
			if (!getSpotAt(x + 1, y + 1).isEmpty()) {
				count++;
			}
		}

		// Check upper right diagonal.
		if (y >= 1 && x <= getSpotWidth() - 2) {
			if (!getSpotAt(x + 1, y - 1).isEmpty()) {
				count++;
			}
		}

		// Check lower left diagonal.
		if (y <= getSpotHeight() - 2 && x >= 1) {
			if (!getSpotAt(x - 1, y + 1).isEmpty()) {
				count++;
			}
		}
		return count;
	}

	public void addGameOfLifeModelListener(GameOfLifeModelListener l) {
		listeners.add(l);
	}

	public void removeGameOfLifeModelListener(GameOfLifeModelListener l) {
		listeners.remove(l);
	}

	public void notifyListeners() {
		for (GameOfLifeModelListener l : listeners) {
			l.update();
		}
	}
}
