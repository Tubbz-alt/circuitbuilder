package fwcd.circuitbuilder.model.logic.expression;

import java.util.stream.Stream;

/**
 * Convenience methods for logic expressions.
 */
public class ExpressionUtils {
	private ExpressionUtils() {}
	
	public static LogicExpression or(Stream<LogicExpression> values) {
		return values.reduce(Disjunction::new).orElse(LogicBoolean.TRUE);
	}
	
	public static LogicExpression or(LogicExpression... expressions) {
		return or(Stream.of(expressions));
	}
	
	public static LogicExpression and(Stream<LogicExpression> values) {
		return values.reduce(Conjunction::new).orElse(LogicBoolean.FALSE);
	}
	
	public static LogicExpression and(LogicExpression... expressions) {
		return and(Stream.of(expressions));
	}
}
