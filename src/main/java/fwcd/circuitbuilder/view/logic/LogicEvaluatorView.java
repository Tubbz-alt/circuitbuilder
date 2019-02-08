package fwcd.circuitbuilder.view.logic;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fwcd.circuitbuilder.model.logic.LogicEditorModel;
import fwcd.circuitbuilder.model.logic.expression.EvaluationException;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.notation.ToNotationConverter;
import fwcd.circuitbuilder.model.logic.transform.ExpressionTransformer;
import fwcd.circuitbuilder.model.logic.transform.QuineMcCluskeyMinimizer;
import fwcd.circuitbuilder.model.utils.BoolUtils;
import fwcd.fructose.swing.StatusBar;
import fwcd.fructose.swing.View;

public class LogicEvaluatorView implements View {
	private final JPanel component;
	private final ExpressionTransformer minimizer = new QuineMcCluskeyMinimizer();
	
	public LogicEvaluatorView(LogicEditorModel model) {
		component = new JPanel();
		component.setLayout(new BorderLayout());
		
		StatusBar statusBar = new StatusBar();
		component.add(statusBar.getComponent(), BorderLayout.NORTH);
		
		JLabel label = new JLabel("Enter a logic expression to evaluate.");
		label.setFont(label.getFont().deriveFont(18F));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		component.add(label, BorderLayout.CENTER);
		
		model.getExpression().listen(expr -> {
			try {
				label.setText(expr
					.map(LogicExpression::evaluate)
					.map(it -> "= " + BoolUtils.toBit(it))
					.orElse("= ?"));
				statusBar.reset();
			} catch (EvaluationException e) {
				ToNotationConverter converter = new ToNotationConverter(model.getNotation());
				label.setText(expr.map(minimizer::transform).map(it -> it.accept(converter)).orElse("= ?"));
				statusBar.display(e.getMessage(), Color.ORANGE);
			}
		});
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
