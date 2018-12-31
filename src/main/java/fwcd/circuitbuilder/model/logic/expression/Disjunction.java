package fwcd.circuitbuilder.model.logic.expression;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	public boolean evaluate(Map<String, Boolean> inputs) {
		return left.evaluate(inputs) || right.evaluate(inputs);
	}
	
	@Override
	public String toString() {
		return "OR";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		Disjunction other = (Disjunction) obj;
		return other.left.equals(left)
			&& other.right.equals(right);
	}
	
	@Override
	public int hashCode() {
		return left.hashCode() * right.hashCode() * 7;
	}
}
