package com.fwcd.circuitbuilder.view;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.circuitbuilder.model.CircuitGridModel;
import com.fwcd.fructose.swing.View;

public class CircuitGridView implements View {
	private final JPanel component;
	private final CircuitGridModel model;
	
	public CircuitGridView(CircuitGridModel model) {
		this.model = model;
		component = new JPanel();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
