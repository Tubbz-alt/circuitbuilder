package com.fwcd.circuitbuilder.model.logic.expression;

public interface LogicExpression {
	int getInputCount();
	
	boolean evaluate();
}
