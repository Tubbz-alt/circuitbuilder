package fwcd.circuitbuilder.model.logic.expression;

import java.util.function.Function;

public enum LogicExpressionType {
	CONJUNCTION(20, in -> new Conjunction(in[0], in[1])),
	DISJUNCTION(10, in -> new Disjunction(in[0], in[1])),
	EQUIVALENCE(40, in -> new Equivalence(in[0], in[1])),
	IMPLICATION(30, in -> new Implication(in[0], in[1])),
	NEGATION(100, in -> new Negation(in[0]));
	
	private final Function<LogicExpression[], LogicExpression> constructor;
	private final int precedence;
	
	private LogicExpressionType(int precedence, Function<LogicExpression[], LogicExpression> constructor) {
		this.precedence = precedence;
		this.constructor = constructor;
	}
	
	public LogicExpression create(LogicExpression... inputs) {
		return constructor.apply(inputs);
	}
	
	public int getPrecedence() {
		return precedence;
	}
}
