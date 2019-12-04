package viewAndController;

import java.awt.event.MouseListener;

public interface IGameOfLifeGrid extends MouseListener {
	public void paintGrid(boolean[][] states);
}
