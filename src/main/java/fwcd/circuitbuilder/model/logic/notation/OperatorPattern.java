package fwcd.circuitbuilder.model.logic.notation;

import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;

public class OperatorPattern {
	private final LogicExpressionType expressionType;
	private final String value;
	
	public OperatorPattern(
		LogicExpressionType expressionType,
		String value
	) {
		this.expressionType = expressionType;
		this.value = value;
	}
	
	public OperatorType getOperatorType() {
		if (isUnary()) {
			return OperatorType.UNARY;
		} else if (isBinary()) {
			return OperatorType.BINARY;
		} else {
			throw new IllegalStateException("Operator with " + expressionType.getInputCount() + " inputs is neither unary nor binary!");
		}
	}
	
	public int getPrecedence() { return expressionType.getPrecedence(); }
	
	public boolean isUnary() { return expressionType.getInputCount() == 1; }
	
	public boolean isBinary() { return expressionType.getInputCount() == 2; }
	
	public String getValue() { return value; }
	
	public LogicExpressionType getExpressionType() { return expressionType; }
}
