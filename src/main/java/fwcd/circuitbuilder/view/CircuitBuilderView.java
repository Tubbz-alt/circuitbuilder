package fwcd.circuitbuilder.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import fwcd.circuitbuilder.model.CircuitBuilderModel;
import fwcd.circuitbuilder.utils.BackgroundLooper;
import fwcd.circuitbuilder.view.formula.FormulaEditorView;
import fwcd.fructose.swing.View;

/**
 * The main application component that contains
 * the circuit editor, sidebar and more.
 */
public class CircuitBuilderView implements View {
	private final JPanel component;
	private final int tickDelay = 80; // ms tick delay
	
	private final CircuitToolsPanel itemPanel;
	private final CircuitGridView grid;
	private final FormulaEditorView formulaEditor;
	
	// TODO: Serialization
	
	public CircuitBuilderView(CircuitBuilderModel model, CircuitBuilderContext context) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		JSplitPane contentWithEditor = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		JSplitPane content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		contentWithEditor.setTopComponent(content);
		
		itemPanel = new CircuitToolsPanel(model, context);
		component.add(itemPanel.getComponent(), BorderLayout.WEST);
		
		grid = new CircuitGridView(model.getGrid(), context);
		content.setLeftComponent(grid.getComponent());
		
		formulaEditor = new FormulaEditorView();
		contentWithEditor.setBottomComponent(formulaEditor.getComponent());
		
		component.add(contentWithEditor, BorderLayout.CENTER);
		
		new BackgroundLooper("Circuit engine", tickDelay, model.getEngine()::tick).start();
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
