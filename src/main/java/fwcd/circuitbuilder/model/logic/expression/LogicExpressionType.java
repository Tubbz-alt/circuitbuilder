package fwcd.circuitbuilder.model.logic.expression;

import java.util.function.Function;

public enum LogicExpressionType {
	CONJUNCTION(20, 2, in -> new Conjunction(in[0], in[1])),
	DISJUNCTION(10, 2, in -> new Disjunction(in[0], in[1])),
	EQUIVALENCE(40, 2, in -> new Equivalence(in[0], in[1])),
	IMPLICATION(30, 2, in -> new Implication(in[0], in[1])),
	NEGATION(100, 1, in -> new Negation(in[0]));
	
	private final Function<LogicExpression[], LogicExpression> constructor;
	private final int inputCount;
	private final int precedence;
	
	private LogicExpressionType(int precedence, int inputCount, Function<LogicExpression[], LogicExpression> constructor) {
		this.precedence = precedence;
		this.constructor = constructor;
		this.inputCount = inputCount;
	}
	
	public LogicExpression create(LogicExpression... inputs) {
		return constructor.apply(inputs);
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public int getInputCount() {
		return inputCount;
	}
}
