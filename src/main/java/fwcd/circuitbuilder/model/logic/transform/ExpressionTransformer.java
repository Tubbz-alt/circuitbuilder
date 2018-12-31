package fwcd.circuitbuilder.model.logic.transform;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;

public interface ExpressionTransformer {
	LogicExpression transform(LogicExpression expression);
}
