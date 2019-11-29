package gameOfLife;

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

public class GameOfLifeView extends JPanel
		implements SpotListener, ActionListener {

	private List<GameOfLifeViewListener> listeners;

	private JTextField changeGridSizeInput;

	private JTextField changeLowBirthInput;
	private JTextField changeHighBirthInput;
	private JTextField changeLowSurviveInput;
	private JTextField changeHighSurviveInput;

	private JTextField changeDelayInput;

	public GameOfLifeView(GameOfLifeModel grid) {
		/* Populate the view */
		setLayout(new BorderLayout());

		// Add the grid to the view
		add(grid, BorderLayout.CENTER);

		// Create the controlPanel which holds all the necessary ui elements
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 0));

		// Create top panel to hold user-Game action ui elements.
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 0));

		// Create bottom panel to hold ui elements that change game settings.
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 0, 10, 0));

		// Add fill randomly button.
		JButton fillRandomlyButton = new JButton("Fill randomly");
		fillRandomlyButton.addActionListener(this);
		fillRandomlyButton.setActionCommand("Fill randomly button");

		topPanel.add(fillRandomlyButton);

		// Add advance game button.
		JButton advanceGameButton = new JButton("Advance game");
		advanceGameButton.addActionListener(this);
		advanceGameButton.setActionCommand("Advance game button");

		topPanel.add(advanceGameButton);

		// Add toggle Torus button
		JButton toggleTorusButton = new JButton("Turn Torus on");
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

		JButton startStopButton = new JButton("Start");
		startStopButton.addActionListener(this);
		startStopButton.setActionCommand("Start/stop button");
		startStopPanel.add(startStopButton);

		topPanel.add(startStopPanel);

		// Add change grid size panel.
		JPanel changeGridSizePanel = new JPanel();
		changeGridSizePanel.setLayout(new GridLayout(1, 0));

		JLabel changeGridSizeLabel = new JLabel("Grid size:");
		changeGridSizePanel.add(changeGridSizeLabel);

		changeGridSizeInput = new JTextField("20");
		changeGridSizeInput.setActionCommand("Change grid size text field");
		changeGridSizePanel.add(changeGridSizeInput);

		JButton changeGridSizeButton = new JButton("Change");
		changeGridSizeButton.addActionListener(this);
		changeGridSizeButton.setActionCommand("Change grid size button");
		changeGridSizePanel.add(changeGridSizeButton);

		bottomPanel.add(changeGridSizePanel);

		// Add change thresholds panel
		JPanel changeThresholdsPanel = new JPanel();
		changeThresholdsPanel.setLayout(new GridLayout(1, 0));

		JLabel changeThresholdsLabel = new JLabel("Thresholds:");
		changeThresholdsPanel.add(changeThresholdsLabel);

		changeLowBirthInput = new JTextField("Low birth (3)");
		changeLowBirthInput.setActionCommand("Change low birth text field");
		changeThresholdsPanel.add(changeLowBirthInput);

		changeHighBirthInput = new JTextField("High birth (3)");
		changeHighBirthInput.setActionCommand("Change high birth text field");
		changeThresholdsPanel.add(changeHighBirthInput);

		changeLowSurviveInput = new JTextField("Low survive (2)");
		changeLowSurviveInput.setActionCommand("Change low survive text field");
		changeThresholdsPanel.add(changeLowSurviveInput);

		changeHighSurviveInput = new JTextField("High survive (3)");
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

		/* Add the view as a SpotListener of all the spots on the grid */
		grid.addSpotListener(this);

		// Instantiate List of listeners
		listeners = new ArrayList<GameOfLifeViewListener>();
	}

	@Override
	public void spotClicked(Spot spot) {
		fireEvent(new SpotClickedEvent(spot));
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
	}

	/*
	 * Handles button presses.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand();
		switch (actionCommand) {
		case "Fill randomly button":
			fireEvent(new FillGridRandomlyEvent());
			break;
		case "Advance game button":
			fireEvent(new AdvanceGameEvent());
			break;
		case "Change grid size button":
			int size = isInteger(changeGridSizeInput.getText());
			if (size >= 10 && size <= 500) {
				fireEvent(new ChangeGridSizeEvent(size));
			}
			break;
		case "Clear grid button":
			fireEvent(new ClearGridEvent());
			break;
		}

	}

	private void fireEvent(GameOfLifeViewEvent e) {
		for (GameOfLifeViewListener l : listeners) {
			l.handleGameOfLifeViewEvent(e);
		}
	}

	/*
	 * Checks whether a String is a valid size, namely whether it is an integer
	 * in the range 10 to 500 inclusive. Returns the int representation if valid
	 * or -1 if invalid.
	 */
	private int isInteger(String string) {
		int result;
		try {
			result = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return -1;
		}

		return result;
	}

	public void addGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.add(l);
	}

	public void removeGameOfLifeViewListener(GameOfLifeViewListener l) {
		listeners.remove(l);
	}
}
