package fwcd.circuitbuilder.model.logic.notation;

import java.util.Map;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;
import fwcd.circuitbuilder.model.logic.expression.LogicExpressionTypeProvider;
import fwcd.circuitbuilder.model.logic.expression.LogicExpressionVisitor;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;

public class ToNotationConverter implements LogicExpressionVisitor<String> {
	private final Map<LogicExpressionType, String> patterns;
	private final LogicExpressionTypeProvider typeProvider = new LogicExpressionTypeProvider();
	
	public ToNotationConverter(LogicNotation notation) {
		patterns = notation.getPatterns().stream()
			.collect(Collectors.toMap(
				OperatorPattern::getExpressionType,
				OperatorPattern::getValue
			));
	}
	
	@Override
	public String visitExpression(LogicExpression expression) {
		String op = operatorStringOf(expression);
		String str = expression.getOperands().stream()
			.map(it -> it.accept(this))
			.reduce((a, b) -> a + " " + op + " " + b)
			.orElse("");
		return "(" + str + ")";
	}
	
	@Override
	public String visitBoolean(LogicBoolean bool) {
		return Integer.toString(bool.getIntValue());
	}
	
	private String operatorStringOf(LogicExpression expression) {
		return patterns.get(expression.accept(typeProvider));
	}
	
	@Override
	public String visitNegation(Negation negation) {
		return operatorStringOf(negation) + negation.getValue().accept(this);
	}
	
	@Override
	public String visitVariable(LogicVariable variable) {
		return variable.getName();
	}
}
