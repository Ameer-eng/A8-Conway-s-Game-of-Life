package gameOfLife;

public class GameOfLifeController
		implements GameOfLifeViewListener, GameOfLifeModelListener {

	GameOfLifeModel model;
	GameOfLifeView view;

	public GameOfLifeController(GameOfLifeModel model, GameOfLifeView view) {
		view.addGameOfLifeViewListener(this);
		model.addGameOfLifeModelListener(this);
		this.model = model;
		this.view = view;
	}

	@Override
	public void handleGameOfLifeViewEvent(GameOfLifeViewEvent e) {
		if (e.isSpotClickedEvent()) {
			SpotClickedEvent spotClicked = (SpotClickedEvent) e;
			model.toggleSpot(spotClicked.getSpot());
		} else if (e.isFillGridRandomlyEvent()) {
			model.fillGridRandomly();
		} else if (e.isAdvanceGameEvent()) {
			model.advanceGame();
		} else if (e.isChangeGridSizeEvent()) {
			ChangeGridSizeEvent changeGridSize = (ChangeGridSizeEvent) e;
			model.changeGridSizeTo(changeGridSize.getSize());
		} else if (e.isClearGridEvent()) {
			model.clearGrid();
		}
	}

	@Override
	public void update() {
		model.addSpotListener(view);
	}
}
