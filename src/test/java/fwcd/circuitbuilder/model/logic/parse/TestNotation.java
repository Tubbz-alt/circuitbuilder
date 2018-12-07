package fwcd.circuitbuilder.model.logic.parse;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;
import fwcd.circuitbuilder.model.logic.parse.Associativity;
import fwcd.circuitbuilder.model.logic.parse.LogicNotation;
import fwcd.circuitbuilder.model.logic.parse.NotationPattern;
import fwcd.circuitbuilder.model.logic.parse.PatternPosition;

public class TestNotation implements LogicNotation {
	private static final List<NotationPattern> PATTERNS = Arrays.asList(
		new NotationPattern(LogicExpressionType.CONJUNCTION, "and", PatternPosition.INFIX, 1, Associativity.LEFT),
		new NotationPattern(LogicExpressionType.DISJUNCTION, "or", PatternPosition.INFIX, 1, Associativity.RIGHT)
	);
	
	public Collection<? extends NotationPattern> getPatterns() { return PATTERNS; }
}