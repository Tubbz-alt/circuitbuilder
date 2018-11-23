package com.fwcd.circuitbuilder.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.fwcd.circuitbuilder.model.CircuitBuilderModel;

/**
 * The Swing frame containing a {@link CircuitBuilderView}
 */
public class CircuitBuilderFrame {
	private final JFrame frame;
	
	public CircuitBuilderFrame() {
		frame = new JFrame("CircuitBuilder");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new CircuitBuilderView(new CircuitBuilderModel()).getComponent(), BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public JFrame getFrame() { return frame; }
}