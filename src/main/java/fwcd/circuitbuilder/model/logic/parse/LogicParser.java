package fwcd.circuitbuilder.model.logic.parse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.parse.token.LogicToken;
import fwcd.fructose.exception.TodoException;
import fwcd.fructose.parsers.StringParser;

/**
 * Parses a raw logic expression into its
 * abstract representation.
 */
public class LogicParser implements StringParser<LogicExpression> {
	private final LogicLexer lexer;
	private final List<NotationPattern> patterns;
	private final Map<String, Integer> precendences;
	
	public LogicParser(LogicNotation notation) {
		lexer = new LogicLexer(Stream.concat(
				Stream.of("(", ")"),
				notation.getPatterns().stream().map(NotationPattern::getValue))
			.collect(Collectors.toList()));
		patterns = notation.getPatterns()
			.stream()
			.collect(Collectors.toList());
		precendences = notation.getPatterns().stream()
			.collect(Collectors.toMap(NotationPattern::getValue, NotationPattern::getPrecedence));
	}
	
	/**
	 * Parses an expression using precedence climbing.
	 */
	private String parseExpression(List<LogicToken> lhs, int minPrecedence) {
		// Source: https://en.wikipedia.org/wiki/Operator-precedence_parser (pseudocode)
		
		throw new TodoException();
	}
	
	@Override
	public LogicExpression parse(String raw) {
		List<LogicToken> tokens = lexer.parse(raw);
		
		throw new TodoException();
	}
}
