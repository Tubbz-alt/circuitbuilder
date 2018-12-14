package fwcd.circuitbuilder.view.logic.karnaugh;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.fructose.swing.View;

public class KarnaughMapView implements View {
	private final JPanel view;
	
	public KarnaughMapView() {
		view = new JPanel();
	}
	
	@Override
	public JComponent getComponent() { return view; }
}
