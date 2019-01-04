package fwcd.circuitbuilder.view.grid.timediagram;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.utils.DemoFunctionSegment;
import fwcd.circuitbuilder.view.utils.SignalFunctionPlot;
import fwcd.fructose.swing.View;

public class TimeDiagramView implements View {
	private final JPanel component;
	
	public TimeDiagramView() {
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		SignalFunctionPlot signalFunctionPlot = new SignalFunctionPlot(new DemoFunctionSegment());
		signalFunctionPlot.setValueCount(20);
		signalFunctionPlot.setHeight(40);
		component.add(signalFunctionPlot.getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
