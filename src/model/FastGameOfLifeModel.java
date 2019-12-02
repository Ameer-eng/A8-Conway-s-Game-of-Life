package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class FastGameOfLifeModel extends JPanel
		implements IGameOfLifeModel, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<GameOfLifeModelListener> listeners;

	private int lowBirth;
	private int highBirth;
	private int lowSurvive;
	private int highSurvive;
	private GameOfLifeRunner runner;
	private boolean isTorus;
	private int[][] squares;

	private Color borderColor;
	private Color fillColor;

	private static final Color DARK = new Color(0.5f, 0.5f, 0.5f);
	private static final Color LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final int DEFAULT_SCREEN_WIDTH = 600;
	private static final int DEFAULT_SCREEN_HEIGHT = 600;
	private static final float BORDER_WIDTH = (float) 0.1;

	/*
	 * Constructor
	 */
	public FastGameOfLifeModel() {
		listeners = new ArrayList<GameOfLifeModelListener>();

		// Set starting values for properties.
		squares = new int[10][10];
		lowBirth = 3;
		highBirth = 3;
		lowSurvive = 2;
		highSurvive = 3;
		isTorus = false;

		this.borderColor = LIGHT;
		this.fillColor = Color.YELLOW;

		addMouseListener(this);
		setBackground(DARK);
		setPreferredSize(
				new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
		repaint();
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
	 * Implementation of MouseListener
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		// Get the dimensions of an individual square in terms of pixels.
		// The dimensions of the board are fixed to be the largest square
		// possible using the available board size.
		int yDim = (int) getSize().getHeight() / squares[0].length;
		int xDim = (int) getSize().getWidth() / squares.length;
		int dimSquare = Math.min(yDim, xDim);

		// Get (x, y) coordinates of mouse.
		int x = e.getX() / dimSquare;
		int y = e.getY() / dimSquare;

		// Flip the square.
		if (x < squares.length && x >= 0 && y < squares[x].length && y >= 0) {
			squares[x][y] = (squares[x][y] == 0) ? 1 : 0;

		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/*
	 * fillGridRandomly
	 * 
	 */
	@Override
	public void fillGridRandomly(double probability) {
		if (probability < 0 || probability > 1) {
			throw new IllegalArgumentException(
					"Probability must be between 0 and 1 inclusive.");
		}

		Random rng = new Random();
		for (int x = 0; x < squares.length; x++) {
			for (int y = 0; y < squares[x].length; y++) {
				squares[x][y] = (rng.nextDouble() < probability) ? 1 : 0;
			}
		}

		repaint();
	}

	/*
	 * Advances the game according to the rules.
	 */
	@Override
	public void advanceGame() {
		// Calculate the future board state.
		int[][] future = new int[squares.length][squares[0].length];
		for (int x = 0; x < squares.length; x++) {
			for (int y = 0; y < squares[x].length; y++) {
				int liveNeighborCount = isTorus ? torusLiveNeighborsCount(x, y)
						: liveNeighborsCount(x, y);
				if (squares[x][y] == 0) {
					if (liveNeighborCount >= lowBirth
							&& liveNeighborCount <= highBirth) {
						future[x][y] = 1;
					} else {
						future[x][y] = 0;
					}
				} else {
					if (liveNeighborCount < lowSurvive
							|| liveNeighborCount > highSurvive) {
						future[x][y] = 0;
					} else {
						future[x][y] = 1;
					}
				}
			}
		}

		// Update the board.
		squares = future;
		repaint();
	}

	/*
	 * Helper methods to count the number of live neighbors a spot has.
	 */

	private int liveNeighborsCount(int x, int y) {
		int count = 0;
		int width = squares.length;
		int height = squares[0].length;

		// Check above.
		if (y != 0) {
			count += squares[x][y - 1];
		}

		// Check below.
		if (y != height - 1) {
			count += squares[x][y + 1];
		}

		// Check to the right.
		if (x != width - 1) {
			count += squares[x + 1][y];
		}

		// Check to the left.
		if (x != 0) {
			count += squares[x - 1][y];
		}

		// Check upper left diagonal.
		if (y != 0 && x != 0) {
			count += squares[x - 1][y - 1];
		}

		// Check lower right diagonal.
		if (y != height - 1 && x != width - 1) {
			count += squares[x + 1][y + 1];
		}

		// Check upper right diagonal.
		if (y != 0 && x != width - 1) {
			count += squares[x + 1][y - 1];
		}

		// Check lower left diagonal.
		if (y != height - 1 && x != 0) {
			count += squares[x - 1][y + 1];
		}

		return count;
	}

	private int torusLiveNeighborsCount(int x, int y) {
		int count = liveNeighborsCount(x, y);
		int width = squares.length;
		int height = squares[0].length;

		// Check above.
		if (y == 0) {
			count += squares[x][height - 1];
		}

		// Check below.
		if (y == height - 1) {
			count += squares[x][0];
		}

		// Check to the right.
		if (x == width - 1) {
			count += squares[0][y];
		}

		// Check to the left.
		if (x == 0) {
			count += squares[width - 1][y];
		}

		// Check upper left diagonal.
		if (y == 0 && x == 0) {
			count += squares[width - 1][height - 1];
		} else if (y == 0) {
			count += squares[x - 1][height - 1];
		} else if (x == 0) {
			count += squares[width - 1][y - 1];
		}

		// Check lower right diagonal.
		if (y == height - 1 && x == width - 1) {
			count += squares[0][0];
		} else if (y == height - 1) {
			count += squares[x + 1][0];
		} else if (x == width - 1) {
			count += squares[0][y + 1];
		}

		// Check upper right diagonal.
		if (y == 0 && x == width - 1) {
			count += squares[0][height - 1];
		} else if (y == 0) {
			count += squares[x + 1][height - 1];
		} else if (x == width - 1) {
			count += squares[0][y - 1];
		}

		// Check lower left diagonal.
		if (y == height - 1 && x == 0) {
			count += squares[width - 1][0];
		} else if (y == height - 1) {
			count += squares[x - 1][0];
		} else if (x == 0) {
			count += squares[width - 1][y + 1];
		}

		return count;
	}

	/*
	 * Changes grid size while maintaining states of current squares.
	 */
	@Override
	public void changeGridSizeTo(int width, int height) {
		// Do nothing if the size is unchanged.
		if (width == squares.length && height == squares[0].length) {
			return;
		}

		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Width or height is negative");
		}

		int[][] newSquares = new int[width][height];

		// Squares in the old grid stay the same.
		for (int x = 0; x < Math.min(squares.length, newSquares.length); x++) {
			for (int y = 0; y < Math.min(squares[x].length,
					newSquares[x].length); y++) {
				newSquares[x][y] = squares[x][y];
			}
		}

		// The rest of the squares are unfilled(0) by default so no need to do
		// anything further.

		squares = newSquares;
		repaint();
	}

	/*
	 * Kills all cells on the grid.
	 */
	@Override
	public void clearGrid() {
		for (int x = 0; x < squares.length; x++) {
			for (int y = 0; y < squares[x].length; y++) {
				squares[x][y] = 0;
			}
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		// Super class paintComponent will take care of
		// painting the background.
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();

		// Get the dimensions of an individual square in terms of pixels.
		// The dimensions of the board are fixed to be the largest square
		// possible using the available board size.
		int dimSquare = Math.min(
				(int) getSize().getHeight() / squares[0].length,
				(int) getSize().getWidth() / squares.length);

		// If the squares are more than 1 x 1, paint the grid,
		// otherwise just paint the border of the grid.
		g2d.setColor(borderColor);
		g2d.setStroke(new BasicStroke(BORDER_WIDTH));
		if (dimSquare > 1) {
			for (int x = 0; x < squares.length; x++) {
				for (int y = 0; y < squares[x].length; y++) {
					g2d.drawRect(dimSquare * x, dimSquare * y, dimSquare,
							dimSquare);
				}
			}
		} else {
			g2d.drawRect(0, 0, dimSquare * squares.length,
					dimSquare * squares[0].length);
		}

		// Fill squares appropriately.
		g2d.setColor(fillColor);
		for (int x = 0; x < squares.length; x++) {
			for (int y = 0; y < squares[x].length; y++) {
				if (squares[x][y] == 1) {
					g2d.fillRect(dimSquare * x, dimSquare * y, dimSquare,
							dimSquare);
				}
			}
		}
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
			l.update(actionCommand);
		}
	}
}
