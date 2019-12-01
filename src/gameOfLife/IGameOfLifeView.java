package gameOfLife;

public interface IGameOfLifeView {
	void addGameOfLifeViewListener(GameOfLifeViewListener l);
	void removeGameOfLifeViewListener(GameOfLifeViewListener l);
}
