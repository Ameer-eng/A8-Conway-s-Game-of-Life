package gameOfLife;

public abstract class GameOfLifeViewEvent {

	public boolean isSpotClickedEvent() {
		return false;
	}

	public boolean isFillGridRandomlyEvent() {
		return false;
	}

	public boolean isAdvanceGameEvent() {
		return false;
	}

	public boolean isToggleTorusEvent() {
		return false;
	}

	public boolean isClearGridEvent() {
		return false;
	}

	public boolean isStartStopEvent() {
		return false;
	}

	public boolean isChangeGridSizeEvent() {
		return false;
	}

	public boolean isChangeThresholdsEvent() {
		return false;
	}
}

class SpotClickedEvent extends GameOfLifeViewEvent {
	private Spot spot;

	public SpotClickedEvent(Spot spot) {
		this.spot = spot;
	}

	public boolean isSpotClickedEvent() {
		return true;
	}

	public Spot getSpot() {
		return spot;
	}
}

class FillGridRandomlyEvent extends GameOfLifeViewEvent {
	public boolean isFillGridRandomlyEvent() {
		return true;
	}
}

class AdvanceGameEvent extends GameOfLifeViewEvent {
	public boolean isAdvanceGameEvent() {
		return true;
	}
}

class ToggleTorusEvent extends GameOfLifeViewEvent {
	// True = turn on, false = turn off
	private boolean targetState;

	public ToggleTorusEvent(boolean targetState) {
		this.targetState = targetState;
	}

	public boolean isToggleTorusEvent() {
		return true;
	}

	public boolean getTargetState() {
		return targetState;
	}
}

class ClearGridEvent extends GameOfLifeViewEvent {
	public boolean isClearGridEvent() {
		return true;
	}
}

class StartStopEvent extends GameOfLifeViewEvent {
	private boolean targetState;

	public StartStopEvent(boolean targetState) {
		this.targetState = targetState;
	}

	public boolean isStartStopEvent() {
		return true;
	}

	public boolean getTargetState() {
		return targetState;
	}
}

class ChangeGridSizeEvent extends GameOfLifeViewEvent {
	private int size;

	public ChangeGridSizeEvent(int size) {
		this.size = size;
	}

	public boolean isChangeGridSizeEvent() {
		return true;
	}

	public int getSize() {
		return size;
	}
}

class ChangeThresholdsEvent extends GameOfLifeViewEvent {
	private int lowBirth;
	private int highBirth;
	private int lowSurvive;
	private int highSurvive;

	public ChangeThresholdsEvent(int lowBirth, int highBirth, int lowSurvive,
			int highSurvive) {
		this.lowBirth = lowBirth;
		this.highBirth = highBirth;
		this.lowSurvive = lowSurvive;
		this.highSurvive = highSurvive;
	}

	public boolean isChangeThresholdsEvent() {
		return true;
	}

	public int getLowBirth() {
		return lowBirth;
	}

	public int getHighBirth() {
		return highBirth;
	}

	public int getLowSurvive() {
		return lowSurvive;
	}

	public int getHighSurvive() {
		return highSurvive;
	}
}
