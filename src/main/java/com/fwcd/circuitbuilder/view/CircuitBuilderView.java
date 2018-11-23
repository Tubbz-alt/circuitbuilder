package com.fwcd.circuitbuilder.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.circuitbuilder.model.CircuitBuilderModel;
import com.fwcd.fructose.swing.View;

/**
 * The main application component that contains
 * the circuit editor, sidebar and more.
 */
public class CircuitBuilderView implements View {
	private final JPanel component;
	
	private CircuitToolsPanel itemPanel;
	private CircuitGridView grid;
	
	// TODO: Serialization
	
	public CircuitBuilderView(CircuitBuilderModel model, CircuitBuilderContext context) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		itemPanel = new CircuitToolsPanel(model, context);
		component.add(itemPanel.getComponent(), BorderLayout.WEST);
		
		grid = new CircuitGridView(model.getGrid());
		component.add(grid.getComponent(), BorderLayout.CENTER);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
