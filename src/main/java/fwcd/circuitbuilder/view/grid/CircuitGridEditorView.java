package fwcd.circuitbuilder.view.grid;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.view.utils.BackgroundLooper;
import fwcd.circuitbuilder.view.utils.CollapsibleView;
import fwcd.fructose.swing.View;

public class CircuitGridEditorView implements View, AutoCloseable {
	private static final int TICK_DELAY = 80; // ms tick delay
	private final JPanel component;
	private final BackgroundLooper engineLooper;
	
	public CircuitGridEditorView(CircuitGridModel model, CircuitEngineModel engine, CircuitGridContext context) {
		engineLooper = new BackgroundLooper(TICK_DELAY, engine::tick);
		
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		component.add(new CircuitGridView(model, engine, context).getComponent(), BorderLayout.CENTER);
		component.add(new CircuitToolsPanel(model, context).getComponent(), BorderLayout.WEST);
		component.add(new CollapsibleView(new CircuitGridSidebarView(model, engine)).getComponent(), BorderLayout.EAST);
		
		engineLooper.start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	@Override
	public void close() {
		engineLooper.stop();
	}
}
