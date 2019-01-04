package fwcd.circuitbuilder.view.grid;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.view.grid.timediagram.TimeDiagramView;
import fwcd.fructose.swing.View;

public class CircuitGridSidebarView implements View {
	private final JTabbedPane component;
	
	public CircuitGridSidebarView(CircuitGridModel model, CircuitEngineModel engine) {
		component = new JTabbedPane();
		component.addTab("Time Diagram", new TimeDiagramView().getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
