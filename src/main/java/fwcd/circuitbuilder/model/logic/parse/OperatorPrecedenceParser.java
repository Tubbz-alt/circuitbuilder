package fwcd.circuitbuilder.model.logic.parse;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.fructose.Option;
import fwcd.fructose.exception.TodoException;
import fwcd.fructose.parsers.StringParser;
import fwcd.fructose.structs.ArrayStack;
import fwcd.fructose.structs.Stack;

public class OperatorPrecedenceParser implements StringParser<ParseTreeNode> {
	private final Map<String, Integer> precedenceTable;
	private final Pattern regex;
	
	public OperatorPrecedenceParser(Map<String, Integer> precedenceTable) {
		this.precedenceTable = precedenceTable;
		if (precedenceTable.isEmpty()) {
			regex = Pattern.compile(".+"); // Match entire string
		} else {
			regex = constructRegex();
		}
	}
	
	public Pattern constructRegex() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (String operator : precedenceTable.keySet()) {
			if (i != 0) {
				builder.append('|');
			}
			builder.append(Pattern.quote(operator));
			i++;
		}
		return Pattern.compile(builder.toString());
	}
	
	public List<ParseToken> tokenize(String raw) {
		Matcher matcher = regex.matcher(raw);
		Stream.Builder<ParseToken> tokens = Stream.builder();
		int matchedChars = 0;
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			int skipped = start - matchedChars;
			// TODO: This relies on the matcher evaluating in the correct order
			if (skipped > 0) {
				tokens.accept(new ParseToken(ParseTokenType.VALUE, raw.substring(matchedChars, start)));
			}
			// TODO: Deal with empty tokens
			// TODO: Parentheses
			tokens.accept(new ParseToken(ParseTokenType.OPERATOR, matcher.group()));
			matchedChars = end;
		}
		if (matchedChars < raw.length()) {
			tokens.accept(new ParseToken(ParseTokenType.VALUE, raw.substring(matchedChars)));
		}
		return tokens.build()
			.flatMap(it -> it.getType() == ParseTokenType.OPERATOR
				? Stream.of(it)
				: Stream.of(it.getValue().trim())
					.filter(str -> !str.isEmpty())
					.map(str -> new ParseToken(ParseTokenType.VALUE, str)))
			.collect(Collectors.toList());
	}
	
	@Override
	public ParseTreeNode parse(String raw) {
		throw new TodoException(); // TODO
	}
	
	private int precedenceOf(ParseToken token) {
		return Objects.requireNonNull(
			precedenceTable.get(token.getValue()),
			() -> token + " does not have a precedence"
		);
	}
	
	public List<ParseToken> shuntingYard(List<ParseToken> tokens) {
		// Source: https://en.wikipedia.org/wiki/Shunting-yard_algorithm
		Stack<ParseToken> output = new ArrayStack<>();
		Stack<ParseToken> operators = new ArrayStack<>();
		
		for (ParseToken token : tokens) {
			switch (token.getType()) {
				case VALUE:
					output.push(token);
					break;
				case OPERATOR:
					while (!operators.isEmpty() && greaterOrEqualPrecedence(operators.peek(), token)) {
						output.push(operators.pop());
					}
					operators.push(token);
					break;
				case LEFT_PARENTHESIS:
					operators.push(token);
					break;
				case RIGHT_PARENTHESIS:
					while (!operators.isEmpty() && operators.peek().getType() != ParseTokenType.LEFT_PARENTHESIS) {
						output.push(operators.pop());
					}
					if (operators.isEmpty()) {
						throw new ParseException("Mismatched parentheses");
					} else {
						// Push left parenthesis
						output.push(operators.pop());
					}
					break;
				default:
					throw new IllegalArgumentException("Found token of invalid type " + token.getType() + " while parsing: " + token);
			}
		}
		
		while (!operators.isEmpty()) {
			output.push(operators.pop());
		}
		
		return output.asBottomToTopList();
	}
	
	private boolean greaterOrEqualPrecedence(ParseToken a, ParseToken b) {
		return a.getType() == ParseTokenType.OPERATOR
			&& b.getType() == ParseTokenType.OPERATOR
			&& precedenceOf(a) >= precedenceOf(b);
	}
}
