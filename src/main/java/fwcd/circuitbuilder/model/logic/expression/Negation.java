package fwcd.circuitbuilder.model.logic.expression;

import java.util.Collections;
import java.util.List;

/**
 * A logical NOT.
 */
public class Negation implements LogicExpression {
	private final LogicExpression value;
	
	public Negation(LogicExpression value) {
		this.value = value;
	}
	
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public List<LogicExpression> getOperands() {
		return Collections.singletonList(value);
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitNegation(this);
	}
	
	@Override
	public boolean evaluate() {
		return !value.evaluate();
	}
}
