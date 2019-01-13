package fwcd.circuitbuilder.view.grid;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitGridModel;
import fwcd.circuitbuilder.view.utils.BackgroundLooper;
import fwcd.circuitbuilder.view.utils.CollapsibleView;
import fwcd.fructose.swing.View;

public class CircuitGridEditorView implements View, AutoCloseable {
	private static final int ENGINE_UPDATE_DELAY = 40; // ms
	private final JPanel component;
	private final BackgroundLooper engineLooper;
	
	public CircuitGridEditorView(CircuitGridModel model, CircuitEngineModel engine, CircuitGridContext context) {
		engineLooper = new BackgroundLooper(ENGINE_UPDATE_DELAY, engine::update);
		
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		component.add(new CircuitGridView(model, engine, context).getComponent(), BorderLayout.CENTER);
		component.add(new CircuitToolsPanel(model, context).getComponent(), BorderLayout.WEST);
		
		CircuitGridSidebarView sideBar = new CircuitGridSidebarView(model, engine);
		sideBar.getComponent().setPreferredSize(new Dimension(350, 1));
		component.add(new CollapsibleView(sideBar).getComponent(), BorderLayout.EAST);
		
		engineLooper.start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	@Override
	public void close() {
		engineLooper.stop();
	}
}
