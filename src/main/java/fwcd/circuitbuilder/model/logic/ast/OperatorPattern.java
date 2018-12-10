package fwcd.circuitbuilder.model.logic.ast;

import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;

public class OperatorPattern {
	private final LogicExpressionType expressionType;
	private final String value;
	private final OperatorType operatorType;
	
	public OperatorPattern(
		LogicExpressionType expressionType,
		String value,
		OperatorType operatorType
	) {
		this.expressionType = expressionType;
		this.value = value;
		this.operatorType = operatorType;
	}
	
	public OperatorType getOperatorType() { return operatorType; }
	
	public int getPrecedence() { return expressionType.getPrecedence(); }
	
	public boolean isUnary() { return operatorType == OperatorType.UNARY; }
	
	public boolean isBinary() { return operatorType == OperatorType.BINARY; }
	
	public String getValue() { return value; }
	
	public LogicExpressionType getExpressionType() { return expressionType; }
}
