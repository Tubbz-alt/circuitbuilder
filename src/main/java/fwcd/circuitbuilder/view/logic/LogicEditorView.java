package fwcd.circuitbuilder.view.logic;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import fwcd.circuitbuilder.model.logic.LogicEditorModel;
import fwcd.circuitbuilder.view.CircuitBuilderAppContext;
import fwcd.circuitbuilder.view.logic.formula.FormulaEditorView;
import fwcd.fructose.swing.View;

public class LogicEditorView implements View {
	private final JSplitPane component;
	
	public LogicEditorView(LogicEditorModel model, CircuitBuilderAppContext context) {
		component = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		component.setResizeWeight(1);
		context.runAfterLaunch(() -> component.setDividerLocation(0.9));
		
		FormulaEditorView formulaEditor = new FormulaEditorView();
		component.setBottomComponent(formulaEditor.getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
