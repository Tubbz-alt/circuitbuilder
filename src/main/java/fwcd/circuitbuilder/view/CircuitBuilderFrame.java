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
		CircuitBuilderModel model = new CircuitBuilderModel();
		CircuitBuilderView view = new CircuitBuilderView(model, new CircuitBuilderContext());
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(new CircuitBuilderMenuBar(view, model).getComponent());
		frame.add(view.getComponent(), BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public JFrame getFrame() { return frame; }
}
