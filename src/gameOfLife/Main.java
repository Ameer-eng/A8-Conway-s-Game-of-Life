package gameOfLife;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		GameOfLifeModel model = new GameOfLifeModel(20, 20);
		GameOfLifeView view = new GameOfLifeView(model);
		GameOfLifeController controller = new GameOfLifeController(model, view);

		/* Create top level window. */

		JFrame main_frame = new JFrame();
		main_frame.setTitle("MVC Conway's Game of Life");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main_frame.setContentPane(view);

		main_frame.pack();
		main_frame.setVisible(true);
	}

}
