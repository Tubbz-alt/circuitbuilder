package com.fwcd.circuitbuilder.model.logic;

/**
 * A logical implication.
 */
public class Implication implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Implication(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public boolean evaluate() {
		return !left.evaluate() || right.evaluate();
	}
}
