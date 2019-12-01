package viewAndController;

public abstract class GameOfLifeViewEvent {

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

	public boolean isStartEvent() {
		return false;
	}
	
	public boolean isStopEvent() {
		return false;
	}

	public boolean isChangeGridSizeEvent() {
		return false;
	}
	
	public boolean isChangeLowBirthThresholdEvent() {
		return false;
	}
	
	public boolean isChangeHighBirthThresholdEvent() {
		return false;
	}
	
	public boolean isChangeLowSurviveThresholdEvent() {
		return false;
	}
	
	public boolean isChangeHighSurviveThresholdEvent() {
		return false;
	}
}

class FillGridRandomlyEvent extends GameOfLifeViewEvent {
	private double probability;
	
	public FillGridRandomlyEvent(double probability) {
		this.probability = probability;
	}
	
	public double getProbability() {
		return probability;
	}
	
	@Override
	public boolean isFillGridRandomlyEvent() {
		return true;
	}
}

class AdvanceGameEvent extends GameOfLifeViewEvent {
	@Override
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
	
	public boolean getTargetState() {
		return targetState;
	}

	@Override
	public boolean isToggleTorusEvent() {
		return true;
	}
}

class ClearGridEvent extends GameOfLifeViewEvent {
	@Override
	public boolean isClearGridEvent() {
		return true;
	}
}

class StartEvent extends GameOfLifeViewEvent {
	private long delay;
	
	public StartEvent(long delay) {
		this.delay = delay;
	}
	
	public long getDelay() {
		return delay;
	}
	
	@Override
	public boolean isStartEvent() {
		return true;
	}
}

class StopEvent extends GameOfLifeViewEvent {
	@Override
	public boolean isStopEvent() {
		return true;
	}
}

class ChangeGridSizeEvent extends GameOfLifeViewEvent {
	private int size;

	public ChangeGridSizeEvent(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}
	
	@Override
	public boolean isChangeGridSizeEvent() {
		return true;
	}
}

class ChangeLowBirthThresholdEvent extends GameOfLifeViewEvent {
	private int lowBirth;
	
	public ChangeLowBirthThresholdEvent(int lowBirth) {
		this.lowBirth = lowBirth;
	}
	
	public int getLowBirth() {
		return lowBirth;
	}
	
	@Override
	public boolean isChangeLowBirthThresholdEvent() {
		return true;
	}
}

class ChangeHighBirthThresholdEvent extends GameOfLifeViewEvent {
	private int highBirth;
	
	public ChangeHighBirthThresholdEvent(int highBirth) {
		this.highBirth = highBirth;
	}
	
	public int getHighBirth() {
		return highBirth;
	}
	
	@Override
	public boolean isChangeHighBirthThresholdEvent() {
		return true;
	}
}

class ChangeLowSurviveThresholdEvent extends GameOfLifeViewEvent {
	private int lowSurvive;
	
	public ChangeLowSurviveThresholdEvent(int lowSurvive) {
		this.lowSurvive = lowSurvive;
	}
	
	public int getLowSurvive() {
		return lowSurvive;
	}
	
	@Override
	public boolean isChangeLowSurviveThresholdEvent() {
		return true;
	}
}

class ChangeHighSurviveThresholdEvent extends GameOfLifeViewEvent {
	private int highSurvive;
	
	public ChangeHighSurviveThresholdEvent(int highSurvive) {
		this.highSurvive = highSurvive;
	}
	
	public int getHighSurvive() {
		return highSurvive;
	}
	
	@Override
	public boolean isChangeHighSurviveThresholdEvent() {
		return true;
	}
}
