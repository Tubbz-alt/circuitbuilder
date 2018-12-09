package fwcd.circuitbuilder.model.logic.parse;

import java.util.List;

import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.parse.token.LogicToken;
import fwcd.fructose.exception.TodoException;
import fwcd.fructose.parsers.StringParser;

/**
 * Parses a raw logic expression into its
 * abstract representation.
 */
public class LogicParser implements StringParser<LogicExpression> {
	public LogicParser(LogicNotation notation) {
		
	}
	
	/**
	 * Parses an expression using precedence climbing.
	 */
	private String parseExpression(List<LogicToken> lhs, int minPrecedence) {
		throw new TodoException();
	}
	
	@Override
	public LogicExpression parse(String raw) {
		throw new TodoException();
	}
}
