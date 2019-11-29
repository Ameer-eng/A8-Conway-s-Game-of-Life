package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

/*
 * JSpotBoard is a user interface component that implements SpotBoard.
 * 
 * Spot width and spot height are specified to the constructor. 
 * 
 * By default, the spots on the spot board are set up with a checker board pattern
 * for background colors and yellow highlighting.
 * 
 * Uses SpotBoardIterator to implement Iterable<Spot>
 * 
 */

public class JSpotBoard extends JPanel implements SpotBoard {

	protected static final int DEFAULT_SCREEN_WIDTH = 600;
	protected static final int DEFAULT_SCREEN_HEIGHT = 600;
	protected static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f,
			0.8f, 0.8f);
	protected static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f,
			0.5f);
	protected static final Color DEFAULT_SPOT_COLOR = Color.YELLOW;
	protected static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

	private Spot[][] spots;

	public JSpotBoard(int width, int height) {
		if (width < 1 || height < 1 || width > 500 || height > 500) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		setLayout(new GridLayout(height, width));
		spots = new Spot[width][height];

		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH / width,
				DEFAULT_SCREEN_HEIGHT / height);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color bg = ((x + y) % 2 == 0) ? DEFAULT_BACKGROUND_LIGHT
						: DEFAULT_BACKGROUND_DARK;
				spots[x][y] = new JSpot(bg, DEFAULT_SPOT_COLOR,
						DEFAULT_HIGHLIGHT_COLOR, this, x, y);
				((JSpot) spots[x][y]).setPreferredSize(preferred_size);
				add(((JSpot) spots[x][y]));
			}
		}
	}

	// Getters for SpotWidth and SpotHeight properties

	@Override
	public int getSpotWidth() {
		return spots.length;
	}

	@Override
	public int getSpotHeight() {
		return spots[0].length;
	}

	// Lookup method for Spot at position (x,y)

	@Override
	public Spot getSpotAt(int x, int y) {
		if (x < 0 || x >= getSpotWidth() || y < 0 || y >= getSpotHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates");
		}

		return spots[x][y];
	}

	protected Spot[][] getSpots() {
		return spots;
	}

	protected void setSpots(Spot[][] a) {
		spots = a;
	}

	// Convenience methods for (de)registering spot listeners.

	@Override
	public void addSpotListener(SpotListener spotListener) {
		for (Spot s : this) {
			s.addSpotListener(spotListener);
		}
	}

	@Override
	public void removeSpotListener(SpotListener spotListener) {
		for (Spot s : this) {
			s.removeSpotListener(spotListener);
		}
	}

	@Override
	public Iterator<Spot> iterator() {
		return new SpotBoardIterator(this);
	}
}
