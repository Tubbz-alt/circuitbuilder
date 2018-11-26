package com.fwcd.circuitbuilder.model.logic.expression;

/**
 * A logical AND.
 */
public class Conjunction implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Conjunction(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public boolean evaluate() {
		return left.evaluate() && right.evaluate();
	}
}
