package fwcd.circuitbuilder.view.grid;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.view.grid.timediagram.TimeDiagramView;
import fwcd.circuitbuilder.view.utils.ToggledView;

public class CircuitGridSidebarView implements ToggledView {
	private final JTabbedPane component;
	private final TimeDiagramView timeDiagram;
	
	public CircuitGridSidebarView(CircuitEngineModel engine) {
		component = new JTabbedPane();
		
		timeDiagram = new TimeDiagramView(engine.getTimeDiagram());
		component.addTab("Time Diagram", timeDiagram.getComponent());
	}
	
	@Override
	public void onUpdateVisibility(boolean visible) {
		timeDiagram.onUpdateVisibility(visible);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
