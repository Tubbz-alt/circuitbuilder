package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import com.fwcd.circuitbuilder.model.logic.parse.token.LogicToken;
import com.fwcd.fructose.exception.TodoException;
import com.fwcd.fructose.parsers.StringParser;

/**
 * Parses a raw logic expression into its
 * abstract representation.
 */
public class LogicParser implements StringParser<LogicExpression> {
	private final LogicLexer lexer;
	private final List<NotationPattern> precedencePatterns;
	
	public LogicParser(LogicNotation notation) {
		lexer = new LogicLexer(notation.getPatterns()
			.stream()
			.map(NotationPattern::getValue)
			.collect(Collectors.toCollection(ArrayList::new)));
		precedencePatterns = notation.getPatterns()
			.stream()
			.sorted((a, b) -> Integer.compare(a.getPrecedence(), b.getPrecedence()))
			.collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * Parses an expression using precedence climbing.
	 */
	private String parseExpression(String lhs, int minPrecedence) {
		// Source: https://en.wikipedia.org/wiki/Operator-precedence_parser (pseudocode)
		
		throw new TodoException();
	}
	
	@Override
	public LogicExpression parse(String raw) {
		List<LogicToken> tokens = lexer.parse(raw);
		
		throw new TodoException();
	}
}
