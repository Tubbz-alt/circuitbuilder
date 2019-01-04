package fwcd.circuitbuilder.view.grid.timediagram;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.fructose.swing.View;

public class TimeDiagramView implements View {
	private final JPanel component;
	
	public TimeDiagramView() {
		component = new JPanel();
		
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
