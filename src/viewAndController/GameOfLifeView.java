package viewAndController;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.FastGameOfLifeModel;

public class GameOfLifeView extends JPanel
		implements IGameOfLifeView, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<GameOfLifeViewListener> listeners;
	private JButton toggleTorusButton;
	private JButton startStopButton;
	private JTextField changeGridWidthInput;
	private JTextField changeGridHeightInput;
	private JTextField changeLowBirthInput;
	private JTextField changeHighBirthInput;
	private JTextField changeLowSurviveInput;
	private JTextField changeHighSurviveInput;
	private JTextField changeDelayInput;
	private JTextField changeProbabilityInput;

	/*
	 * Constructor.
	 */
	public GameOfLifeView(FastGameOfLifeModel grid) {
		/* Populate the view */
		setLayout(new BorderLayout());

		// Create the controlPanel which holds all the necessary UI elements.
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 0));

		// Create top panel to hold user-Game action UI elements.
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 0));

		// Create bottom panel to hold UI elements that change game settings.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 0, 10, 0));

		// Add fill randomly panel.
		JPanel fillRandomlyPanel = new JPanel();
		fillRandomlyPanel.setLayout(new GridLayout(1, 0));

		JLabel changeProbabilityLabel = new JLabel("Probability = ");
		fillRandomlyPanel.add(changeProbabilityLabel);

		changeProbabilityInput = new JTextField("0.5");
		changeProbabilityInput
				.setActionCommand("Change probability text field");
		fillRandomlyPanel.add(changeProbabilityInput);

		JButton fillRandomlyButton = new JButton("Fill");
		fillRandomlyButton.addActionListener(this);
		fillRandomlyButton.setActionCommand("Fill randomly button");
		fillRandomlyPanel.add(fillRandomlyButton);

		topPanel.add(fillRandomlyPanel);

		// Add advance game button.
		JButton advanceGameButton = new JButton("Advance game");
		advanceGameButton.addActionListener(this);
		advanceGameButton.setActionCommand("Advance game button");

		topPanel.add(advanceGameButton);

		// Add toggle torus button
		toggleTorusButton = new JButton("Turn torus on");
		toggleTorusButton.addActionListener(this);
		toggleTorusButton.setActionCommand("Toggle torus button");

		topPanel.add(toggleTorusButton);

		// Add clear grid button.
		JButton clearButton = new JButton("Clear grid");
		clearButton.addActionListener(this);
		clearButton.setActionCommand("Clear grid button");

		topPanel.add(clearButton);

		// Add start/stop with delay panel.
		JPanel startStopPanel = new JPanel();
		startStopPanel.setLayout(new GridLayout(1, 0));

		JLabel delaylabel = new JLabel("Delay (ms):");
		startStopPanel.add(delaylabel);

		changeDelayInput = new JTextField("10");
		changeDelayInput.setActionCommand("Change delay text field");
		startStopPanel.add(changeDelayInput);

		startStopButton = new JButton("Start");
		startStopButton.addActionListener(this);
		startStopButton.setActionCommand("Start/stop button");
		startStopPanel.add(startStopButton);

		topPanel.add(startStopPanel);

		// Add change grid size panel.
		JPanel changeGridSizePanel = new JPanel();
		changeGridSizePanel.setLayout(new GridLayout(1, 0));

		JLabel changeGridSizeLabel = new JLabel("Grid size:");
		changeGridSizePanel.add(changeGridSizeLabel);

		changeGridWidthInput = new JTextField("10");
		changeGridWidthInput.setActionCommand("Change grid width text field");
		changeGridSizePanel.add(changeGridWidthInput);

		JLabel xLabel = new JLabel("                x ");
		changeGridSizePanel.add(xLabel);

		changeGridHeightInput = new JTextField("10");
		changeGridHeightInput.setActionCommand("Change grid height text field");
		changeGridSizePanel.add(changeGridHeightInput);

		JButton changeGridSizeButton = new JButton("Change");
		changeGridSizeButton.addActionListener(this);
		changeGridSizeButton.setActionCommand("Change grid size button");
		changeGridSizePanel.add(changeGridSizeButton);

		bottomPanel.add(changeGridSizePanel);

		// Add change thresholds panel.
		JPanel changeThresholdsPanel = new JPanel();
		changeThresholdsPanel.setLayout(new GridLayout(1, 0));

		JLabel changeThresholdsLabel = new JLabel("Thresholds:");
		changeThresholdsPanel.add(changeThresholdsLabel);

		changeLowBirthInput = new JTextField("low birth here");
		changeLowBirthInput.setActionCommand("Change low birth text field");
		changeThresholdsPanel.add(changeLowBirthInput);

		changeHighBirthInput = new JTextField("high birth here");
		changeHighBirthInput.setActionCommand("Change high birth text field");
		changeThresholdsPanel.add(changeHighBirthInput);

		changeLowSurviveInput = new JTextField("low survive here");
		changeLowSurviveInput.setActionCommand("Change low survive text field");
		changeThresholdsPanel.add(changeLowSurviveInput);

		changeHighSurviveInput = new JTextField("high survive here");
		changeHighSurviveInput
				.setActionCommand("Change high survive text field");
		changeThresholdsPanel.add(changeHighSurviveInput);

		JButton changeThresholdsButton = new JButton("Change");
		changeThresholdsButton.addActionListener(this);
		changeThresholdsButton.setActionCommand("Change thresholds button");
		changeThresholdsPanel.add(changeThresholdsButton);

		bottomPanel.add(changeThresholdsPanel);

		// Put top, and bottom panels into the control panel.
		controlPanel.add(topPanel);
		controlPanel.add(bottomPanel);

		// Put the control panel in the View.
		add(controlPanel, BorderLayout.SOUTH);

		// Add the grid to the View.
		add(grid, BorderLayout.CENTER);

		// Instantiate List of listeners
		listeners = new ArrayList<GameOfLifeViewListener>();
	}

	/*
	 * Set the text for toggle buttons.
	 */

	public void setToggleTorusButtonTextTo(String msg) {
		if (msg == null) {
			throw new IllegalArgumentException("Null message");
		}

		toggleTorusButton.setText(msg);
	}

	public void setStartStopButtonTextTo(String msg) {
		if (msg == null) {
			throw new IllegalArgumentException("Null message");
		}

		startStopButton.setText(msg);
	}

	/*
	 * Handles button presses.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonPressed = (JButton) e.getSource();
		String actionCommand = buttonPressed.getActionCommand();
		switch (actionCommand) {
		case "Fill randomly button":
			double value1;
			if (isDouble(changeProbabilityInput.getText())
					&& (value1 = Double
							.parseDouble(changeProbabilityInput.getText())) >= 0
					&& value1 <= 1) {
				fireEvent(new FillGridRandomlyEvent(value1));
			}
			break;
		case "Advance game button":
			fireEvent(new AdvanceGameEvent());
			break;
		case "Toggle torus button":
			fireEvent(new ToggleTorusEvent(
					buttonPressed.getText().equals("Turn torus on")));
			break;
		case "Clear grid button":
			fireEvent(new ClearGridEvent());
			break;
		case "Start/stop button":
			if (buttonPressed.getText().equals("Start")) {
				long value2;
				if (isInteger(changeDelayInput.getText())
						&& (value2 = Long
								.parseLong(changeDelayInput.getText())) >= 10
						&& value2 <= 1000) {
					fireEvent(new StartEvent(value2));
				}
			} else {
				fireEvent(new StopEvent());
			}
			break;
		case "Change grid size button":
			int width;
			int height;
			if (isInteger(changeGridWidthInput.getText())
					&& (width = Integer
							.parseInt(changeGridWidthInput.getText())) >= 10
					&& width <= 500
					&& isInteger(changeGridHeightInput.getText())
					&& (height = Integer
							.parseInt(changeGridHeightInput.getText())) >= 10
					&& height <= 500) {
				fireEvent(new ChangeGridSizeEvent(width, height));
			}
			break;
		case "Change thresholds button":
			int value4;
			if (isInteger(changeLowBirthInput.getText())
					&& (value4 = Integer
							.parseInt(changeLowBirthInput.getText())) >= 0
					&& value4 <= 8) {
				fireEvent(new ChangeLowBirthThresholdEvent(value4));
			}
			if (isInteger(changeHighBirthInput.getText())
					&& (value4 = Integer
							.parseInt(changeHighBirthInput.getText())) >= 0
					&& value4 <= 8) {
				fireEvent(new ChangeHighBirthThresholdEvent(value4));
			}
			if (isInteger(changeLowSurviveInput.getText())
					&& (value4 = Integer
							.parseInt(changeLowSurviveInput.getText())) >= 0
					&& value4 <= 8) {
				fireEvent(new ChangeLowSurviveThresholdEvent(value4));
			}
			if (isInteger(changeHighSurviveInput.getText())
					&& (value4 = Integer
							.parseInt(changeHighSurviveInput.getText())) >= 0
					&& value4 <= 8) {
				fireEvent(new ChangeHighSurviveThresholdEvent(value4));
			}
			break;
		}
	}

	/*
	 * Checks whether a String is an integer.
	 */
	private boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/*
	 * Checks whether a String is a double.
	 */
	private boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void addGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.add(l);
	}

	@Override
	public void removeGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.remove(l);
	}

	private void fireEvent(GameOfLifeViewEvent e) {
		for (GameOfLifeViewListener l : listeners) {
			l.handleGameOfLifeViewEvent(e);
		}
	}
}
