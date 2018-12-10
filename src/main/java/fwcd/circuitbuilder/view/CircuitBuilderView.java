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
	
	public CircuitBuilderView(CircuitBuilderModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		JSplitPane contentWithEditor = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		JSplitPane content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		contentWithEditor.setTopComponent(content);
		
		gridEditor = new CircuitGridEditorView(model.getGrid(), new CircuitGridContext());
		content.setLeftComponent(gridEditor.getComponent());
		
		formulaEditor = new FormulaEditorView();
		contentWithEditor.setBottomComponent(formulaEditor.getComponent());
		
		component.add(contentWithEditor, BorderLayout.CENTER);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
