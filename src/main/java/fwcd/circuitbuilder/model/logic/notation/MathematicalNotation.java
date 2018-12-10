package fwcd.circuitbuilder.model.logic.notation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;

public class MathematicalNotation implements LogicNotation {
	private static final List<OperatorPattern> PATTERNS = Arrays.asList(
		new OperatorPattern(LogicExpressionType.NEGATION, "!"),
		new OperatorPattern(LogicExpressionType.CONJUNCTION, "^"),
		new OperatorPattern(LogicExpressionType.DISJUNCTION, "v"),
		new OperatorPattern(LogicExpressionType.IMPLICATION, "=>"),
		new OperatorPattern(LogicExpressionType.EQUIVALENCE, "<=>")
	);
	
	@Override
	public Collection<? extends OperatorPattern> getPatterns() { return PATTERNS; }
}
