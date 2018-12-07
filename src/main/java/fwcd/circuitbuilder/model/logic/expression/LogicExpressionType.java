package fwcd.circuitbuilder.model.logic.expression;

import java.util.function.Function;

public enum LogicExpressionType {
	CONJUNCTION(in -> new Conjunction(in[0], in[1])),
	DISJUNCTION(in -> new Disjunction(in[0], in[1])),
	EQUIVALENCE(in -> new Equivalence(in[0], in[1])),
	IMPLICATION(in -> new Implication(in[0], in[1])),
	NEGATION(in -> new Negation(in[0]));
	
	private final Function<LogicExpression[], LogicExpression> constructor;
	
	private LogicExpressionType(Function<LogicExpression[], LogicExpression> constructor) {
		this.constructor = constructor;
	}
	
	public LogicExpression create(LogicExpression... inputs) {
		return constructor.apply(inputs);
	}
}