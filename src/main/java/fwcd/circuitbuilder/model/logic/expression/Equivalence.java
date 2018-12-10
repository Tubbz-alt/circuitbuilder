package fwcd.circuitbuilder.model.logic.expression;

import java.util.Arrays;
import java.util.List;

/**
 * The logical equivalence operator. Evaluates to true if both operands have the
 * same boolean value.
 */
public class Equivalence implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Equivalence(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public List<LogicExpression> getOperands() {
		return Arrays.asList(left, right);
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitEquivalence(this);
	}
	
	@Override
	public boolean evaluate() {
		return left.evaluate() == right.evaluate();
	}
}
