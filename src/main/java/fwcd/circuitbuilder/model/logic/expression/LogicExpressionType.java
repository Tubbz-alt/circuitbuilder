package fwcd.circuitbuilder.model.logic.expression;

import java.util.function.BiFunction;

import fwcd.circuitbuilder.model.logic.parse.ParseException;

public enum LogicExpressionType {
	TRUE(Integer.MAX_VALUE, 0, (name, in) -> LogicBoolean.of(true)),
	FALSE(Integer.MAX_VALUE, 0, (name, in) -> LogicBoolean.of(false)),
	VARIABLE(200, 0, (name, in) -> new LogicVariable(name)),
	NEGATION(100, 1, (name, in) -> new Negation(in[0])),
	CONJUNCTION(80, 2, (name, in) -> new Conjunction(in[0], in[1])),
	EXCLUSIVE_DISJUNCTION(60, 2, (name, in) -> new ExclusiveDisjunction(in[0], in[1])),
	DISJUNCTION(50, 2, (name, in) -> new Disjunction(in[0], in[1])),
	IMPLICATION(40, 2, (name, in) -> new Implication(in[0], in[1])),
	EQUIVALENCE(30, 2, (name, in) -> new Equivalence(in[0], in[1]));
	
	private final BiFunction<String, LogicExpression[], LogicExpression> constructor;
	private final int inputCount;
	private final int precedence;
	
	private LogicExpressionType(int precedence, int inputCount, BiFunction<String, LogicExpression[], LogicExpression> constructor) {
		this.precedence = precedence;
		this.constructor = constructor;
		this.inputCount = inputCount;
	}
	
	public LogicExpression create(String name, LogicExpression... inputs) {
		if (inputs.length != inputCount) {
			throw new ParseException(toString() + " expected " + inputCount + " parameters but got " + inputs.length);
		}
		return constructor.apply(name, inputs);
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public int getInputCount() {
		return inputCount;
	}
}
