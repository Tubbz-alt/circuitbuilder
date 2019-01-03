package fwcd.circuitbuilder.view;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import fwcd.circuitbuilder.model.CircuitBuilderModel;
import fwcd.circuitbuilder.view.grid.CircuitGridContext;
import fwcd.circuitbuilder.view.grid.CircuitGridEditorView;
import fwcd.circuitbuilder.view.logic.LogicEditorView;
import fwcd.fructose.swing.View;

/**
 * The main application component that contains the circuit editor, sidebar and
 * more.
 */
public class CircuitBuilderView implements View {
	private final JTabbedPane component;
	
	private final CircuitGridEditorView gridEditor;
	private final LogicEditorView logicEditor;
	
	// TODO: Serialization
	
	public CircuitBuilderView(CircuitBuilderModel model, CircuitBuilderAppContext context) {
		component = new JTabbedPane();
		
		gridEditor = new CircuitGridEditorView(model.getGrid(), model.getEngine(), context.getGridContext());
		component.addTab("Grid Editor", gridEditor.getComponent());
		
		logicEditor = new LogicEditorView(model.getLogicEditor(), context);
		component.addTab("Logic Editor", logicEditor.getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
