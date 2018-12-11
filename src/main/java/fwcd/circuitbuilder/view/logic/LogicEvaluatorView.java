package fwcd.circuitbuilder.view.logic;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fwcd.circuitbuilder.model.logic.LogicEditorModel;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.fructose.swing.View;

public class LogicEvaluatorView implements View {
	private final JLabel label;
	
	public LogicEvaluatorView(LogicEditorModel model) {
		label = new JLabel("Enter a logic expression to evaluate.");
		label.setFont(label.getFont().deriveFont(18F));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		model.getExpression().listen(expr -> label.setText(expr
			.map(LogicExpression::evaluate)
			.map(it -> "= " + (it ? 1 : 0))
			.orElse("= ?")));
	}
	
	@Override
	public JComponent getComponent() { return label; }
}
