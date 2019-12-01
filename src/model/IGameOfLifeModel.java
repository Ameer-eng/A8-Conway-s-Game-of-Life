package model;

public interface IGameOfLifeModel {
	void setLowBirth(int lowBirth);
	void setHighBirth(int highBirth);
	void setLowSurvive(int lowSurvive);
	void setHighSurvive(int highSurvive);
	
	void setTorus(boolean isTorus);
	
	void fillGridRandomly(double probability);
	void advanceGame();
	void changeGridSizeTo(int size);
	void clearGrid();
	void startRunner(long delay);
	void stopRunner();
	
	void addGameOfLifeModelListener(GameOfLifeModelListener l);
	void removeGameOfLifeModelListener(GameOfLifeModelListener l);
}
