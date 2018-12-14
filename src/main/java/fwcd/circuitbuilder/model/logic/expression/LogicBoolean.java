package fwcd.circuitbuilder.model.logic.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A constant logic expression.
 */
public class LogicBoolean implements LogicExpression {
	private static final LogicBoolean TRUE = new LogicBoolean(true);
	private static final LogicBoolean FALSE = new LogicBoolean(false);
	private final boolean value;
	
	private LogicBoolean(boolean value) {
		this.value = value;
	}
	
	public static LogicBoolean of(boolean value) {
		return value ? TRUE : FALSE;
	}
	
	public static LogicBoolean of(int value) {
		return of(value == 1);
	}
	
	public boolean getBoolValue() {
		return value;
	}
	
	public int getIntValue() {
		return value ? 1 : 0;
	}
	
	@Override
	public <T> T accept(LogicExpressionVisitor<T> visitor) {
		return visitor.visitBoolean(this);
	}
	
	@Override
	public boolean evaluate(Map<String, Boolean> inputs) {
		return value;
	}
	
	@Override
	public List<? extends LogicExpression> getOperands() {
		return Collections.emptyList();
	}
	
	@Override
	public String toString() {
		return value ? "1" : "0";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LogicBoolean other = (LogicBoolean) obj;
		return other.value == value;
	}
	
	@Override
	public int hashCode() {
		return value ? 1 : 0;
	}
}
