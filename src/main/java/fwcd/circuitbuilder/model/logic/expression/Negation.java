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
	
	public LogicExpression getValue() {
		return value;
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
	
	@Override
	public int hashCode() {
		return value.hashCode() * 7;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		Negation other = (Negation) obj;
		return other.value.equals(value);
	}
}
