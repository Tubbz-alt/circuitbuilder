package fwcd.circuitbuilder.model.logic.expression;

import java.util.stream.Stream;

/**
 * Discovers all {@link LogicVariable}s inside a {@link LogicExpression}.
 */
public class LogicVariableFinder implements LogicExpressionVisitor<Stream<LogicVariable>> {
	@Override
	public Stream<LogicVariable> visitExpression(LogicExpression expression) {
		return expression.getChildren().stream()
			.flatMap(it -> it.accept(this));
	}
	
	@Override
	public Stream<LogicVariable> visitVariable(LogicVariable variable) {
		return Stream.of(variable);
	}
}
