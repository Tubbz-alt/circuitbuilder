package fwcd.circuitbuilder.view.logic;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import fwcd.circuitbuilder.model.logic.LogicEditorModel;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.karnaugh.KarnaughMapModel;
import fwcd.circuitbuilder.view.CircuitBuilderAppContext;
import fwcd.circuitbuilder.view.logic.formula.FormulaEditorView;
import fwcd.circuitbuilder.view.logic.karnaugh.KarnaughMapView;
import fwcd.circuitbuilder.view.logic.minimize.QuineMcCluskeyView;
import fwcd.fructose.swing.View;

public class LogicEditorView implements View {
	private final JSplitPane component;
	private final JScrollPane expressionViewsPane;
	
	public LogicEditorView(LogicEditorModel model, CircuitBuilderAppContext context) {
		component = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		component.setResizeWeight(1);
		context.runAfterLaunch(() -> component.setDividerLocation(0.9));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		
		LogicEvaluatorView evaluator = new LogicEvaluatorView(model);
		content.add(evaluator.getComponent(), BorderLayout.NORTH);
		
		// TreePlotter plotter = new TreePlotter();
		// model.getExpression().listenAndFire(expr -> plotter.setTree(expr.orElseNull()));
		// content.add(plotter.getComponent(), BorderLayout.CENTER);
		
		expressionViewsPane = new JScrollPane();
		content.add(expressionViewsPane, BorderLayout.CENTER);
		
		model.getExpression().listen(it -> it.ifPresent(this::updateView));
		
		component.setTopComponent(content);
		
		FormulaEditorView formulaEditor = new FormulaEditorView(model);
		component.setBottomComponent(formulaEditor.getComponent());
	}
	
	private void updateView(LogicExpression expression) {
		JPanel expressionViews = new JPanel();
		expressionViews.setLayout(new BoxLayout(expressionViews, BoxLayout.X_AXIS));
		
		KarnaughMapView karnaugh = new KarnaughMapView(new KarnaughMapModel(expression));
		expressionViews.add(karnaugh.getComponent());
		
		QuineMcCluskeyView qmc = new QuineMcCluskeyView(expression);
		expressionViews.add(qmc.getComponent());
		
		expressionViewsPane.setViewportView(expressionViews);
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
