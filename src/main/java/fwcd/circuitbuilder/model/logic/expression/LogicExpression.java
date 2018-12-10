package fwcd.circuitbuilder.model.logic.expression;

import java.util.List;

/**
 * A boolean expression that evaluates to either
 * true or false.
 */
public interface LogicExpression {
	List<LogicExpression> getOperands();
	
	<T> T accept(LogicExpressionVisitor<T> visitor);
	
	boolean evaluate();
}
