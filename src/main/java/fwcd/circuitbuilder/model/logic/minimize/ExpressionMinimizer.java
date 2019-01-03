package fwcd.circuitbuilder.model.logic.minimize;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;

public interface ExpressionMinimizer {
	LogicExpression minimize(LogicExpression expression);
}
