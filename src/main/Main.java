package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.FastGameOfLifeModel;
import viewAndController.GameOfLifeController;
import viewAndController.GameOfLifeView;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FastGameOfLifeModel model = new FastGameOfLifeModel();
				GameOfLifeView view = new GameOfLifeView(model);
				new GameOfLifeController(model, view);

				// Create top level window.
				JFrame main_frame = new JFrame();
				main_frame.setTitle("MVC Conway's Game of Life");
				main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				main_frame.setContentPane(view);

				main_frame.pack();
				main_frame.setVisible(true);
			}
		});
	}

}
