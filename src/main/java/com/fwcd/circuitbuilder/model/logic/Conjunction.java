package com.fwcd.circuitbuilder.model.logic;

/**
 * A logical AND.
 */
public class Conjunction implements LogicExpression {
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public boolean evaluate(boolean... inputs) {
		return inputs[0] && inputs[1];
	}
}
