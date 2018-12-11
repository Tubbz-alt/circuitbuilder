package fwcd.circuitbuilder.view.logic;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import fwcd.circuitbuilder.model.logic.LogicEditorModel;
import fwcd.circuitbuilder.view.CircuitBuilderAppContext;
import fwcd.circuitbuilder.view.logic.formula.FormulaEditorView;
import fwcd.fructose.swing.TreePlotter;
import fwcd.fructose.swing.View;

public class LogicEditorView implements View {
	private final JSplitPane component;
	
	public LogicEditorView(LogicEditorModel model, CircuitBuilderAppContext context) {
		component = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		component.setResizeWeight(1);
		context.runAfterLaunch(() -> component.setDividerLocation(0.9));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		
		LogicEvaluatorView evaluator = new LogicEvaluatorView(model);
		content.add(evaluator.getComponent(), BorderLayout.NORTH);
		
		TreePlotter plotter = new TreePlotter();
		model.getExpression().listenAndFire(expr -> plotter.setTree(expr.orElseNull()));
		content.add(plotter.getComponent(), BorderLayout.CENTER);
		
		component.setTopComponent(content);
		
		FormulaEditorView formulaEditor = new FormulaEditorView(model);
		component.setBottomComponent(formulaEditor.getComponent());
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
