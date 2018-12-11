package fwcd.circuitbuilder.model.logic;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;
import fwcd.circuitbuilder.model.logic.notation.LogicNotation;
import fwcd.circuitbuilder.model.logic.notation.OperatorPattern;
import fwcd.circuitbuilder.model.logic.parse.OperatorPrecedenceParser;
import fwcd.circuitbuilder.model.logic.parse.ParseTreeNode;
import fwcd.fructose.exception.TodoException;
import fwcd.fructose.parsers.StringParser;

public class LogicExpressionParser implements StringParser<LogicExpression> {
	private final OperatorPrecedenceParser opParser;
	private final Map<String, LogicExpressionType> expressionTypes;
	
	public LogicExpressionParser(LogicNotation notation) {
		assertUnaryOperatorsPrecedence(notation);
		opParser = new OperatorPrecedenceParser(precedenceTableFrom(notation), unaryOperatorsFrom(notation));
		expressionTypes = typeMapFrom(notation);
	}

	@Override
	public LogicExpression parse(String raw) {
		return toExpression(opParser.parse(raw));
	}
	
	private LogicExpression toExpression(ParseTreeNode tree) {
		// switch (tree.getToken().getType()) {
		// 	case BINARY_OPERATOR:
				
		// }
		throw new TodoException(); // TODO
	}
	
	private Set<String> unaryOperatorsFrom(LogicNotation notation) {
		return notation.streamUnaryPatterns()
			.map(OperatorPattern::getValue)
			.collect(Collectors.toSet());
	}
	
	private Map<String, Integer> precedenceTableFrom(LogicNotation notation) {
		return notation.streamBinaryPatterns()
			.collect(Collectors.toMap(OperatorPattern::getValue, OperatorPattern::getPrecedence));
	}

	private Map<String, LogicExpressionType> typeMapFrom(LogicNotation notation) {
		return notation.getPatterns().stream()
			.collect(Collectors.toMap(OperatorPattern::getValue, OperatorPattern::getExpressionType));
	}
	
	private void assertUnaryOperatorsPrecedence(LogicNotation notation) {
		int maxBinaryPrecedence = notation.streamBinaryPatterns()
			.mapToInt(OperatorPattern::getPrecedence)
			.max().orElse(Integer.MIN_VALUE);
		int minUnaryPrecedence = notation.streamUnaryPatterns()
			.mapToInt(OperatorPattern::getPrecedence)
			.min().orElse(Integer.MAX_VALUE);
		if (minUnaryPrecedence <= maxBinaryPrecedence) {
			throw new IllegalArgumentException(
				"LogicNotation " + notation + " contains unary operators with a lower/equal precedence compared to some binary operators. This is not currently not supported."
			);
		}
	}
}
