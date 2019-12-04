package viewAndController;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameOfLifeGrid extends JPanel implements IGameOfLifeGrid, MouseListener {
	private static final Color DARK = new Color(0.5f, 0.5f, 0.5f);
	private static final Color LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final int DEFAULT_SCREEN_WIDTH = 600;
	private static final int DEFAULT_SCREEN_HEIGHT = 600;
	private static final float BORDER_WIDTH = (float) 0.1;

	private boolean[][] states;
	private GameOfLifeView view;
	private Color borderColor;
	private Color fillColor;

	/*
	 * Constructor.
	 */
	public GameOfLifeGrid(GameOfLifeView view) {
		this.view = view;

		this.borderColor = LIGHT;
		this.fillColor = Color.YELLOW;

		addMouseListener(this);
		setBackground(DARK);
		setPreferredSize(
				new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
	}

	/*
	 * Implementation of MouseListener.
	 */

	@Override
	public void mouseClicked(MouseEvent e) {
		// Get the dimensions of an individual square in terms of pixels.
		// The dimensions of the board are fixed to be the largest square
		// possible using the available board size.
		int dimSquare = Math.min((int) getSize().getHeight() / states[0].length,
				(int) getSize().getWidth() / states.length);

		// Get (x, y) coordinates of mouse.
		int x = e.getX() / dimSquare;
		int y = e.getY() / dimSquare;
		view.fireEvent(new SquareClickedEvent(x, y));
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

	public void paintGrid(boolean[][] states) {
		this.states = states;
		repaint();
	}

	/*
	 * Override paintComponent to paint the grid according to the states.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Super class paintComponent will take care of
		// painting the background.
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();

		// Get the dimensions of an individual square in terms of pixels.
		// The dimensions of the board are fixed to be the largest square
		// possible using the available board size.
		int dimSquare = Math.min((int) getSize().getHeight() / states[0].length,
				(int) getSize().getWidth() / states.length);

		// If the squares are more than 1 x 1, paint the grid,
		// otherwise just paint the border of the grid.
		g2d.setColor(borderColor);
		g2d.setStroke(new BasicStroke(BORDER_WIDTH));
		if (dimSquare > 1) {
			for (int x = 0; x < states.length; x++) {
				for (int y = 0; y < states[x].length; y++) {
					g2d.drawRect(dimSquare * x, dimSquare * y, dimSquare,
							dimSquare);
				}
			}
		} else {
			g2d.drawRect(0, 0, dimSquare * states.length,
					dimSquare * states[0].length);
		}

		// Fill squares appropriately.
		g2d.setColor(fillColor);
		for (int x = 0; x < states.length; x++) {
			for (int y = 0; y < states[x].length; y++) {
				if (states[x][y]) {
					g2d.fillRect(dimSquare * x, dimSquare * y, dimSquare,
							dimSquare);
				}
			}
		}
	}
}
