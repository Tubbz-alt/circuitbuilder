package fwcd.circuitbuilder.view.grid.timediagram;

import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fwcd.circuitbuilder.model.grid.timediagram.TimeDiagramModel;
import fwcd.circuitbuilder.model.utils.Debouncer;
import fwcd.circuitbuilder.model.utils.SignalFunctionPlotModel;
import fwcd.circuitbuilder.model.utils.SignalFunctionSegment;
import fwcd.circuitbuilder.view.utils.SignalFunctionPlotView;
import fwcd.circuitbuilder.view.utils.ToggledView;
import fwcd.fructose.Closer;

public class TimeDiagramView implements ToggledView, AutoCloseable {
	private static final int PLOT_HEIGHT = 40;
	
	private final Debouncer debouncer = new Debouncer(100); // Plot refresh rate in ms
	private final TimeDiagramModel model;
	private final JPanel component;
	private boolean visible = true;
	
	private final Closer modelListenerCloser = new Closer();
	private final Closer plotCloser = new Closer();
	
	public TimeDiagramView(TimeDiagramModel model) {
		this.model = model;
		
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		model.getSegmentListeners().subscribe(this::updateAll).to(modelListenerCloser);
		model.getPartialTickListeners().subscribe(this::repaintMaybe).to(modelListenerCloser);
	}
	
	private void updateAll(Collection<? extends SignalFunctionSegment> segments) {
		plotCloser.disposeAll();
		component.removeAll();
		
		for (SignalFunctionSegment segment : segments) {
			SignalFunctionPlotModel plotModel = new SignalFunctionPlotModel(segment, model.getPhase());
			SignalFunctionPlotView plotView = new SignalFunctionPlotView(plotModel);
			
			plotView.setHeight(PLOT_HEIGHT);
			component.add(plotView.getComponent());
			plotCloser.add(plotView);
		}
		
		repaint();
	}
	
	private void repaintMaybe() {
		debouncer.runMaybe(this::repaint);
	}
	
	private void repaint() {
		if (visible) {
			SwingUtilities.invokeLater(() -> {
				if (visible) {
					component.revalidate();
					component.repaint();
				}
			});
		}
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	@Override
	public void onUpdateVisibility(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public void close() {
		modelListenerCloser.close();
		plotCloser.close();
	}
}
