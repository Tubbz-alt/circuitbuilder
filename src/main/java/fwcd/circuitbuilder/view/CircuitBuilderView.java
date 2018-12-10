package fwcd.circuitbuilder.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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
	private final JPanel component;
	
	private final CircuitGridEditorView gridEditor;
	private final FormulaEditorView formulaEditor;
	
	// TODO: Serialization
	
	public CircuitBuilderView(CircuitBuilderModel model, CircuitBuilderAppContext context) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		JSplitPane content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		context.runAfterLaunch(() -> content.setDividerLocation(0.8));
		
		gridEditor = new CircuitGridEditorView(model.getGrid(), new CircuitGridContext());
		content.setLeftComponent(gridEditor.getComponent());
		
		formulaEditor = new FormulaEditorView();
		content.setRightComponent(formulaEditor.getComponent());
		
		component.add(content, BorderLayout.CENTER);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
