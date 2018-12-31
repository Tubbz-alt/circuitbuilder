package fwcd.circuitbuilder.model.logic.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LogicVariable implements LogicExpression {
	private final String name;
	
	public LogicVariable(String name) {
		this.name = name;
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitVariable(this);
	}
	
	@Override
	public boolean evaluate(Map<String, Boolean> inputs) {
		if (inputs.containsKey(name)) {
			return inputs.get(name);
		} else {
			throw new EvaluationException("No input value for " + name);
		}
	}
	
	@Override
	public List<? extends LogicExpression> getOperands() {
		return Collections.emptyList();
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!getClass().equals(obj.getClass())) return false;
		LogicVariable other = (LogicVariable) obj;
		return other.name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() * 7;
	}
}
