package com.fwcd.circuitbuilder.model.logic;

/**
 * A logical OR.
 */
public class Disjunction implements LogicExpression {
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public boolean evaluate(boolean... inputs) {
		return inputs[0] || inputs[1];
	}
}
