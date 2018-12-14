package fwcd.circuitbuilder.model.logic.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A logical NOT.
 */
public class Negation implements LogicExpression {
	private final LogicExpression value;
	
	public Negation(LogicExpression value) {
		this.value = value;
	}
	
	@Override
	public List<? extends LogicExpression> getOperands() {
		return Collections.singletonList(value);
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitNegation(this);
	}
	
	@Override
	public boolean evaluate(Map<String, Boolean> inputs) {
		return !value.evaluate(inputs);
	}
	
	@Override
	public String toString() {
		return "NOT";
	}
}
