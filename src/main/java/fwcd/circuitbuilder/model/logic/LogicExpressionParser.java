package fwcd.circuitbuilder.model.logic;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicExpressionType;
import fwcd.circuitbuilder.model.logic.notation.LogicNotation;
import fwcd.circuitbuilder.model.logic.notation.OperatorPattern;
import fwcd.circuitbuilder.model.logic.parse.OperatorPrecedenceParser;
import fwcd.circuitbuilder.model.logic.parse.ParseException;
import fwcd.circuitbuilder.model.logic.parse.ParseToken;
import fwcd.circuitbuilder.model.logic.parse.ParseTreeNode;
import fwcd.fructose.parsers.StringParser;

/**
 * A higher-level parser that additionally converts parse
 * trees (constructed by an operator precedence
 * parser) to logic expressions.
 */
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
		return parseExpression(opParser.parse(raw));
	}
	
	private LogicExpression parseExpression(ParseTreeNode tree) {
		ParseToken token = tree.getToken();
		switch (token.getType()) {
			case VALUE: return parseLogicConstant(token);
			case BINARY_OPERATOR: return parseBinaryOperator(tree);
			case UNARY_OPERATOR: return parseUnaryOperator(tree);
			default: throw new ParseException(token.getType().toString() + " is not a valid logic expression");
		}
	}
	
	private LogicBoolean parseLogicConstant(ParseToken token) {
		String raw = token.getValue();
		if (raw.equalsIgnoreCase("true")) {
			return LogicBoolean.of(true);
		} else if (raw.equalsIgnoreCase("false")) {
			return LogicBoolean.of(false);
		} else {
			try {
				return LogicBoolean.of(Integer.parseInt(raw));
			} catch (NumberFormatException e) {
				throw new ParseException("Invalid logic constant: " + raw);
			}
		}
	}
	
	private LogicExpression parseBinaryOperator(ParseTreeNode node) {
		String raw = node.getToken().getValue();
		LogicExpressionType expressionType = expressionTypes.get(raw);
		if (expressionType == null) {
			throw new ParseException("Invalid binary operator: " + node.getToken().getValue());
		}
		ParseTreeNode lhs = node.getLhs().orElseThrow(() -> new ParseException(raw + " has no left-hand side"));
		ParseTreeNode rhs = node.getRhs().orElseThrow(() -> new ParseException(raw + " has no right-hand side"));
		return expressionType.create(parseExpression(lhs), parseExpression(rhs));
	}
	
	private LogicExpression parseUnaryOperator(ParseTreeNode node) {
		String raw = node.getToken().getValue();
		LogicExpressionType expressionType = expressionTypes.get(raw);
		if (expressionType == null) {
			throw new ParseException("Invalid unary operator: " + node.getToken().getValue());
		}
		ParseTreeNode operand = node.getOperand().orElseThrow(() -> new ParseException(raw + " has no operand"));
		return expressionType.create(parseExpression(operand));
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
