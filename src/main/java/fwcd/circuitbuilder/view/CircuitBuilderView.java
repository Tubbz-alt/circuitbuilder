package fwcd.circuitbuilder.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import fwcd.circuitbuilder.model.CircuitBuilderModel;
import fwcd.circuitbuilder.view.formula.FormulaEditorView;
import fwcd.circuitbuilder.view.grid.CircuitGridContext;
import fwcd.circuitbuilder.view.grid.CircuitGridEditorView;
import fwcd.fructose.swing.View;

/**
 * The main application component that contains
 * the circuit editor, sidebar and more.
 */
public class CircuitBuilderView implements View {
	private final JSplitPane component;
	
	private final CircuitGridEditorView gridEditor;
	private final FormulaEditorView formulaEditor;
	
	// TODO: Serialization
	
	public CircuitBuilderView(CircuitBuilderModel model, CircuitBuilderAppContext context) {
		component = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		component.setResizeWeight(1);
		context.runAfterLaunch(() -> component.setDividerLocation(0.9));
		
		JTabbedPane content = new JTabbedPane();
		component.setTopComponent(content);
		
		gridEditor = new CircuitGridEditorView(model.getGrid(), new CircuitGridContext());
		content.addTab("Grid Editor", gridEditor.getComponent());
		
		formulaEditor = new FormulaEditorView();
		component.setBottomComponent(formulaEditor.getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
