package viewAndController;

import model.FastGameOfLifeModel;

public class GameOfLifeController implements IGameOfLifeController {
	FastGameOfLifeModel model;
	GameOfLifeView view;

	/*
	 * Constructor.
	 */
	public GameOfLifeController(FastGameOfLifeModel model,
			GameOfLifeView view) {
		view.addGameOfLifeViewListener(this);
		model.addGameOfLifeModelListener(this);
		this.model = model;
		this.view = view;
	}

	/*
	 * Handles events that happen in the View.
	 */
	@Override
	public void handleGameOfLifeViewEvent(GameOfLifeViewEvent e) {
		if (e.isFillGridRandomlyEvent()) {
			FillGridRandomlyEvent fillGridRandomly = (FillGridRandomlyEvent) e;
			model.fillGridRandomly(fillGridRandomly.getProbability());
		} else if (e.isAdvanceGameEvent()) {
			model.advanceGame();
		} else if (e.isToggleTorusEvent()) {
			ToggleTorusEvent toggleTorus = (ToggleTorusEvent) e;
			model.setTorus(toggleTorus.getTargetState());
		} else if (e.isClearGridEvent()) {
			model.clearGrid();
		} else if (e.isStartEvent()) {
			StartEvent start = (StartEvent) e;
			model.startRunner(start.getDelay());
		} else if (e.isStopEvent()) {
			model.stopRunner();
		} else if (e.isChangeGridSizeEvent()) {
			ChangeGridSizeEvent changeGridSize = (ChangeGridSizeEvent) e;
			model.changeGridSizeTo(changeGridSize.getWidth(),
					changeGridSize.getHeight());
		} else if (e.isChangeLowBirthThresholdEvent()) {
			ChangeLowBirthThresholdEvent changeLowBirth = (ChangeLowBirthThresholdEvent) e;
			model.setLowBirth(changeLowBirth.getLowBirth());
		} else if (e.isChangeHighBirthThresholdEvent()) {
			ChangeHighBirthThresholdEvent changeHighBirth = (ChangeHighBirthThresholdEvent) e;
			model.setHighBirth(changeHighBirth.getHighBirth());
		} else if (e.isChangeLowSurviveThresholdEvent()) {
			ChangeLowSurviveThresholdEvent changeLowSurvive = (ChangeLowSurviveThresholdEvent) e;
			model.setLowSurvive(changeLowSurvive.getLowSurvive());
		} else if (e.isChangeHighSurviveThresholdEvent()) {
			ChangeHighSurviveThresholdEvent changeHighSurvive = (ChangeHighSurviveThresholdEvent) e;
			model.setHighSurvive(changeHighSurvive.getHighSurvive());
		}
	}

	/*
	 * Handles updates from the Model.
	 */
	@Override
	public void update(String actionCommand) {
		switch (actionCommand) {
		case "Torus turned on":
			view.setToggleTorusButtonTextTo("Turn torus off");
			break;
		case "Torus turned off":
			view.setToggleTorusButtonTextTo("Turn torus on");
			break;
		case "Game started":
			view.setStartStopButtonTextTo("Stop");
			break;
		case "Game stopped":
			view.setStartStopButtonTextTo("Start");
		}
	}
}
