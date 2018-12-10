package fwcd.circuitbuilder.model.logic.parse;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;
import fwcd.circuitbuilder.model.logic.parse.Associativity;
import fwcd.circuitbuilder.model.logic.parse.LogicNotation;
import fwcd.circuitbuilder.model.logic.parse.OperatorPattern;
import fwcd.circuitbuilder.model.logic.parse.OperatorType;

public class TestNotation implements LogicNotation {
	private static final List<OperatorPattern> PATTERNS = Arrays.asList(
		new OperatorPattern(LogicExpressionType.CONJUNCTION, "and", OperatorType.INFIX, 1, Associativity.LEFT),
		new OperatorPattern(LogicExpressionType.DISJUNCTION, "or", OperatorType.INFIX, 1, Associativity.RIGHT)
	);
	
	public Collection<? extends OperatorPattern> getPatterns() { return PATTERNS; }
}
