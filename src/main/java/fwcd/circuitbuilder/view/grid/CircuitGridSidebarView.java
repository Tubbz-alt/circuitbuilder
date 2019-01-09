package fwcd.circuitbuilder.view.grid;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.model.grid.timediagram.TimeDiagramModel;
import fwcd.circuitbuilder.model.utils.DemoFunctionSegment;
import fwcd.circuitbuilder.view.grid.timediagram.TimeDiagramView;
import fwcd.fructose.swing.View;

public class CircuitGridSidebarView implements View {
	private final JTabbedPane component;
	private final TimeDiagramView timeDiagram = new TimeDiagramView(new TimeDiagramModel(new DemoFunctionSegment()));
	
	public CircuitGridSidebarView(CircuitGridModel model, CircuitEngineModel engine) {
		component = new JTabbedPane();
		
		component.addTab("Time Diagram", timeDiagram.getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
