package fwcd.circuitbuilder.model.logic.expression;

import java.util.Arrays;
import java.util.List;

/**
 * A logical OR.
 */
public class Disjunction implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Disjunction(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public List<? extends LogicExpression> getOperands() {
		return Arrays.asList(left, right);
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitDisjunction(this);
	}
	
	@Override
	public boolean evaluate() {
		return left.evaluate() || right.evaluate();
	}
	
	@Override
	public String toString() {
		return "OR";
	}
}
