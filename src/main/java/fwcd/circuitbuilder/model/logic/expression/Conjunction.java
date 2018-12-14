package fwcd.circuitbuilder.model.logic.expression;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A logical AND.
 */
public class Conjunction implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Conjunction(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public List<? extends LogicExpression> getOperands() {
		return Arrays.asList(left, right);
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitConjunction(this);
	}
	
	@Override
	public boolean evaluate(Map<String, Boolean> inputs) {
		return left.evaluate(inputs) && right.evaluate(inputs);
	}
	
	@Override
	public String toString() {
		return "AND";
	}
}
