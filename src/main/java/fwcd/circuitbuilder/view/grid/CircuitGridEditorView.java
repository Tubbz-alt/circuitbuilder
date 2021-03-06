package fwcd.circuitbuilder.view.grid;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitListenableGridModel;
import fwcd.circuitbuilder.utils.Direction;
import fwcd.circuitbuilder.view.utils.BackgroundLooper;
import fwcd.circuitbuilder.view.utils.CollapsibleView;
import fwcd.fructose.swing.Viewable;

public class CircuitGridEditorView implements Viewable, AutoCloseable {
	private static final int ENGINE_UPDATE_DELAY = 40; // ms
	private final JPanel component;
	private final BackgroundLooper engineLooper;
	
	public CircuitGridEditorView(CircuitListenableGridModel model, CircuitEngineModel engine, CircuitGridContext context) {
		engineLooper = new BackgroundLooper(ENGINE_UPDATE_DELAY, engine::update);
		
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		component.add(new CircuitGridView(model, engine, context).getComponent(), BorderLayout.CENTER);
		component.add(new CircuitToolsPanel(context).getComponent(), BorderLayout.WEST);
		
		CircuitGridSidebarView sideBar = new CircuitGridSidebarView(engine);
		sideBar.getComponent().setPreferredSize(new Dimension(350, 1));
		component.add(new CollapsibleView.Builder(sideBar)
			.expandSymbol("<")
			.collapseSymbol(">")
			.expandDirection(Direction.RIGHT)
			.build()
			.getComponent(), BorderLayout.EAST);
		
		engineLooper.start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
	
	@Override
	public void close() {
		engineLooper.stop();
	}
}
