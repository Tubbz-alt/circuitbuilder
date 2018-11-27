package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.ArrayList;
import java.util.List;

import com.fwcd.circuitbuilder.model.logic.parse.token.LogicToken;
import com.fwcd.circuitbuilder.model.logic.parse.token.LogicTokenType;
import com.fwcd.fructose.parsers.StringParser;

/**
 * Splits a raw logic expression into a list
 * of tokens.
 */
public class LogicLexer implements StringParser<List<LogicToken>> {
	@Override
	public List<LogicToken> parse(String raw) {
		List<LogicToken> tokens = new ArrayList<>();
		int charCount = raw.length();
		StringBuilder current = new StringBuilder();
		boolean isIdentifier = true;
		
		for (int i = 0; i < charCount; i++) {
			char c = raw.charAt(i);
			boolean wasIdentifier = isIdentifier;
			boolean skipToken = Character.isWhitespace(c);
			isIdentifier = Character.isLetterOrDigit(c);
			
			boolean isNextToken = !wasIdentifier || skipToken;
			if (isNextToken && current.length() > 0) {
				tokens.add(new LogicToken(toTokenType(wasIdentifier), current.toString()));
				current.delete(0, current.length());
			}
			
			if (!skipToken) {
				current.append(c);
			}
		}
		
		if (current.length() > 0) {
			tokens.add(new LogicToken(toTokenType(isIdentifier), current.toString()));
		}
		
		return tokens;
	}

	private LogicTokenType toTokenType(boolean isIdentifier) {
		return isIdentifier ? LogicTokenType.IDENTIFIER : LogicTokenType.SYMBOL;
	}
}