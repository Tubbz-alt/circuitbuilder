package fwcd.circuitbuilder.model.logic.expression;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	public boolean evaluate(Map<String, Boolean> inputs) {
		return !left.evaluate(inputs) || right.evaluate(inputs);
	}
	
	@Override
	public String toString() {
		return "=>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		Implication other = (Implication) obj;
		return other.left.equals(left)
			&& other.right.equals(right);
	}
	
	@Override
	public int hashCode() {
		return left.hashCode() * right.hashCode() * 7;
	}
}
