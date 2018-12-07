package fwcd.circuitbuilder.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import fwcd.circuitbuilder.model.CircuitBuilderModel;

/**
 * The Swing frame containing a {@link CircuitBuilderView}
 */
public class CircuitBuilderFrame {
	private final JFrame frame;
	
	public CircuitBuilderFrame(String title, int width, int height) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new CircuitBuilderView(
			new CircuitBuilderModel(),
			new CircuitBuilderContext()
		).getComponent(), BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public JFrame getFrame() { return frame; }
}
