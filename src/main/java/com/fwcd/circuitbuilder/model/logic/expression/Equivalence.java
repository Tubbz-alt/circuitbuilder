package com.fwcd.circuitbuilder.model.logic.expression;

/**
 * The logical equivalence operator. Evaluates to
 * true if both operands have the same boolean value.
 */
public class Equivalence implements LogicExpression {
	private final LogicExpression left;
	private final LogicExpression right;
	
	public Equivalence(LogicExpression left, LogicExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int getInputCount() {
		return 2;
	}
	
	@Override
	public boolean evaluate() {
		return left.evaluate() == right.evaluate();
	}
}
