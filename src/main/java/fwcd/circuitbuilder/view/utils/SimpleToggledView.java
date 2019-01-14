package fwcd.circuitbuilder.view.utils;

import javax.swing.JComponent;

import fwcd.fructose.swing.View;

public class SimpleToggledView implements ToggledView {
	private final JComponent component;
	
	public SimpleToggledView(View view) {
		this(view.getComponent());
	}
	
	public SimpleToggledView(JComponent component) {
		this.component = component;
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}
}
