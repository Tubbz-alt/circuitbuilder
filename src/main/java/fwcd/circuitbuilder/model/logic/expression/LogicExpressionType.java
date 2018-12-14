package fwcd.circuitbuilder.model.logic.expression;

import java.util.function.Function;

import fwcd.circuitbuilder.model.logic.parse.ParseException;

public enum LogicExpressionType {
	NEGATION(100, 1, in -> new Negation(in[0])),
	CONJUNCTION(80, 2, in -> new Conjunction(in[0], in[1])),
	EXCLUSIVE_DISJUNCTION(60, 2, in -> new ExclusiveDisjunction(in[0], in[1])),
	DISJUNCTION(50, 2, in -> new Disjunction(in[0], in[1])),
	IMPLICATION(40, 2, in -> new Implication(in[0], in[1])),
	EQUIVALENCE(30, 2, in -> new Equivalence(in[0], in[1])),
	TRUE(Integer.MAX_VALUE, 0, in -> LogicBoolean.of(true)),
	FALSE(Integer.MAX_VALUE, 0, in -> LogicBoolean.of(false));
	
	private final Function<LogicExpression[], LogicExpression> constructor;
	private final int inputCount;
	private final int precedence;
	
	private LogicExpressionType(int precedence, int inputCount, Function<LogicExpression[], LogicExpression> constructor) {
		this.precedence = precedence;
		this.constructor = constructor;
		this.inputCount = inputCount;
	}
	
	public LogicExpression create(LogicExpression... inputs) {
		if (inputs.length != inputCount) {
			throw new ParseException(toString() + " expected " + inputCount + " parameters but got " + inputs.length);
		}
		return constructor.apply(inputs);
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public int getInputCount() {
		return inputCount;
	}
}
