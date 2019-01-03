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
		CircuitBuilderAppContext context = new CircuitBuilderAppContext();
		CircuitBuilderView view = new CircuitBuilderView(model, context);
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(new CircuitBuilderMenuBar(view, model, context).getComponent());
		frame.add(view.getComponent(), BorderLayout.CENTER);
		frame.setVisible(true);
		
		context.pollAndRunLaunchTasks();
	}
	
	public JFrame getFrame() { return frame; }
}
