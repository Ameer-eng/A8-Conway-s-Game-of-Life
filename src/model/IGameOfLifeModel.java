package model;

public interface IGameOfLifeModel {
	boolean[][] getStates();
	int getWidth();
	int getHeight();
	void setLowBirth(int lowBirth);
	void setHighBirth(int highBirth);
	void setLowSurvive(int lowSurvive);
	void setHighSurvive(int highSurvive);
	void setTorus(boolean isTorus);
	
	void toggleSquare(int x, int y);
	void fillGridRandomly(double probability);
	void advanceGame();
	void clearGrid();
	void startRunner(long delay);
	void stopRunner();
	void changeGridSizeTo(int width, int height);
	
	void addGameOfLifeModelListener(GameOfLifeModelListener l);
	void removeGameOfLifeModelListener(GameOfLifeModelListener l);
}
