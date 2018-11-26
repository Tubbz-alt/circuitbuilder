package com.fwcd.circuitbuilder.model.logic;

public interface LogicExpression {
	int getInputCount();
	
	boolean evaluate();
}
