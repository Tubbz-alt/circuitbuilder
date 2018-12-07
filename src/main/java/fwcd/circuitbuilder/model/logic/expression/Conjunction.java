package fwcd.circuitbuilder.model.logic.expression;

import java.util.Arrays;
import java.util.List;

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
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public List<LogicExpression> getOperands() {
		return Arrays.asList(left, right);
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitConjunction(this);
	}
	
	@Override
	public boolean evaluate() {
		return left.evaluate() && right.evaluate();
	}
}
