package fwcd.circuitbuilder.model.logic.expression;

import java.util.Arrays;
import java.util.List;

/**
 * A logical implication.
 */
public class Implication implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Implication(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public List<? extends LogicExpression> getOperands() {
		return Arrays.asList(left, right);
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitImplication(this);
	}
	
	@Override
	public boolean evaluate() {
		return !left.evaluate() || right.evaluate();
	}
	
	@Override
	public String toString() {
		return "=>";
	}
}
