package com.fwcd.circuitbuilder.model.logic.expression;

/**
 * A logical OR.
 */
public class Disjunction implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Disjunction(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public boolean evaluate() {
		return left.evaluate() || right.evaluate();
	}
}
