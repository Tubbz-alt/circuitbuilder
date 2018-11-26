package com.fwcd.circuitbuilder.model.logic.expression;

/**
 * A logical NOT.
 */
public class Negation implements LogicExpression {
	private final LogicExpression value;
	
	public Negation(LogicExpression value) {
		this.value = value;
	}
	
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public boolean evaluate() {
		return !value.evaluate();
	}
}
