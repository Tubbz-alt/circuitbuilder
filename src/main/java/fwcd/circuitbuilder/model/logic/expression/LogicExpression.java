package fwcd.circuitbuilder.model.logic.expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import fwcd.fructose.structs.TreeNode;

/**
 * A boolean expression that evaluates to either
 * true or false.
 */
public interface LogicExpression extends TreeNode {
	List<? extends LogicExpression> getOperands();
	
	<T> T accept(LogicExpressionVisitor<T> visitor);
	
	boolean evaluate(Map<String, Boolean> inputs);
	
	default boolean evaluate() { return evaluate(Collections.emptyMap()); }
	
	@Override
	default List<? extends LogicExpression> getChildren() { return getOperands(); }
}
