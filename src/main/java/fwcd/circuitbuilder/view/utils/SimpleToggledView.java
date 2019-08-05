package fwcd.circuitbuilder.view.utils;

import javax.swing.JComponent;

import fwcd.fructose.swing.Viewable;

public class SimpleToggledView implements ToggledView {
	private final JComponent component;
	
	public SimpleToggledView(Viewable view) {
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
