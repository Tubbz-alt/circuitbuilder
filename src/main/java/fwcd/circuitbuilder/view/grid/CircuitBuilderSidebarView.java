package fwcd.circuitbuilder.view.grid;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import fwcd.circuitbuilder.model.CircuitBuilderModel;
import fwcd.circuitbuilder.view.grid.timediagram.TimeDiagramView;
import fwcd.fructose.swing.View;

public class CircuitBuilderSidebarView implements View {
	private final JTabbedPane component;
	
	public CircuitBuilderSidebarView(CircuitBuilderModel model) {
		component = new JTabbedPane();
		component.addTab("Time Diagram", new TimeDiagramView().getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
