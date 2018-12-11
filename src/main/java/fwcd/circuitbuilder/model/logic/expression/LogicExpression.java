package fwcd.circuitbuilder.model.logic.expression;

import java.util.List;

import fwcd.fructose.structs.TreeNode;

/**
 * A boolean expression that evaluates to either
 * true or false.
 */
public interface LogicExpression extends TreeNode {
	List<? extends LogicExpression> getOperands();
	
	<T> T accept(LogicExpressionVisitor<T> visitor);
	
	boolean evaluate();
	
	@Override
	default List<? extends LogicExpression> getChildren() { return getOperands(); }
}
