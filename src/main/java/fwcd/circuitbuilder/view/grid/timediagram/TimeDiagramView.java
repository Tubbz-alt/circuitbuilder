package fwcd.circuitbuilder.view.grid.timediagram;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.grid.timediagram.TimeDiagramModel;
import fwcd.circuitbuilder.model.utils.SignalFunctionPlotModel;
import fwcd.circuitbuilder.view.utils.SignalFunctionPlotView;
import fwcd.fructose.Closer;
import fwcd.fructose.swing.View;

public class TimeDiagramView implements View, AutoCloseable {
	private final JPanel component;
	private final Closer closer = new Closer();
	
	public TimeDiagramView(TimeDiagramModel model) {
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		SignalFunctionPlotModel plotModel = new SignalFunctionPlotModel("Test", model.getFunctionSegment());
		plotModel.getPhase().set(0.5);
		
		SignalFunctionPlotView plotView = new SignalFunctionPlotView(plotModel);
		plotView.setValueCount(20);
		plotView.setHeight(40);
		
		closer.add(plotView);
		component.add(plotView.getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	@Override
	public void close() {
		closer.close();
	}
}
