package fwcd.circuitbuilder.view.grid.timediagram;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.grid.timediagram.TimeDiagramModel;
import fwcd.circuitbuilder.model.utils.SignalFunctionPlotModel;
import fwcd.circuitbuilder.model.utils.SignalFunctionSegment;
import fwcd.circuitbuilder.view.utils.SignalFunctionPlotView;
import fwcd.fructose.Closer;
import fwcd.fructose.swing.View;

public class TimeDiagramView implements View, AutoCloseable {
	private static final int PLOT_HEIGHT = 40;
	private final TimeDiagramModel model;
	private final JPanel component;
	private final Closer closer = new Closer();
	
	public TimeDiagramView(TimeDiagramModel model) {
		this.model = model;
		
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		model.getSegments().subscribe(this::updateAll).to(closer);
	}
	
	private void updateAll(List<? extends SignalFunctionSegment> segments) {
		closer.disposeAll();
		component.removeAll();
		
		for (SignalFunctionSegment segment : segments) {
			SignalFunctionPlotModel plotModel = new SignalFunctionPlotModel(segment, model.getPhase());
			SignalFunctionPlotView plotView = new SignalFunctionPlotView(plotModel);
			
			plotView.setHeight(PLOT_HEIGHT);
			component.add(plotView.getComponent());
			closer.add(plotView);
		}
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	@Override
	public void close() {
		closer.close();
	}
}
