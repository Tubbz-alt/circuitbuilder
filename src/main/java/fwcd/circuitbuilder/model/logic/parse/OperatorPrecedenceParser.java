package fwcd.circuitbuilder.model.logic.parse;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.utils.ConcatIterable;
import fwcd.fructose.parsers.StringParser;
import fwcd.fructose.structs.ArrayStack;
import fwcd.fructose.structs.Stack;

/**
 * A parser for infix operator grammars.
 */
public class OperatorPrecedenceParser implements StringParser<ParseTreeNode> {
	private final Map<String, Integer> precedenceTable;
	private final Set<String> unaryOperators;
	private final Pattern regex;
	
	/**
	 * Constructs a new operator precedence table.
	 * 
	 * @param precedenceTable - The binary operators together with their precedences
	 * @param unaryOperators - The unary prefix operators (which have a higher precedence than all binary operators)
	 */
	public OperatorPrecedenceParser(Map<String, Integer> precedenceTable, Set<String> unaryOperators) {
		this.precedenceTable = precedenceTable;
		this.unaryOperators = unaryOperators;
		if (precedenceTable.isEmpty()) {
			regex = Pattern.compile(".+"); // Match entire string
		} else {
			regex = constructRegex();
		}
	}
	
	public Pattern constructRegex() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (String operator : new ConcatIterable<>(precedenceTable.keySet(), unaryOperators)) {
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
			String value = matcher.group();
			ParseTokenType type = unaryOperators.contains(value)
				? ParseTokenType.UNARY_OPERATOR
				: ParseTokenType.BINARY_OPERATOR;
			tokens.accept(new ParseToken(type, value));
			matchedChars = end;
		}
		if (matchedChars < raw.length()) {
			tokens.accept(new ParseToken(ParseTokenType.VALUE, raw.substring(matchedChars)));
		}
		return tokens.build()
			.flatMap(it -> it.isOperator()
				? Stream.of(it)
				: Stream.of(it.getValue().trim())
					.filter(str -> !str.isEmpty())
					.map(str -> new ParseToken(ParseTokenType.VALUE, str)))
			.collect(Collectors.toList());
	}
	
	@Override
	public ParseTreeNode parse(String raw) {
		return rpnToParseTree(shuntingYard(tokenize(raw)));
	}
	
	private int precedenceOf(ParseToken token) {
		return Objects.requireNonNull(
			precedenceTable.get(token.getValue()),
			() -> token + " does not have a precedence"
		);
	}
	
	/**
	 * Parses a list of tokens into reverse polish notation.
	 */
	public List<ParseToken> shuntingYard(List<ParseToken> tokens) {
		// Source: https://en.wikipedia.org/wiki/Shunting-yard_algorithm
		Stack<ParseToken> output = new ArrayStack<>();
		Stack<ParseToken> operators = new ArrayStack<>();
		Stack<ParseToken> unaryOperators = new ArrayStack<>();
		
		for (ParseToken token : tokens) {
			switch (token.getType()) {
				case VALUE:
					output.push(token);
					while (!unaryOperators.isEmpty()) {
						output.push(unaryOperators.pop());
					}
					break;
				case UNARY_OPERATOR:
					unaryOperators.push(token);
					break;
				case BINARY_OPERATOR:
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
	
	/**
	 * Converts a list of tokens in reverse polish notation to a
	 * parse tree.
	 */
	private ParseTreeNode rpnToParseTree(List<ParseToken> rpn) {
		Stack<ParseTreeNode> nodes = new ArrayStack<>();
		for (ParseToken token : rpn) {
			switch (token.getType()) {
				case BINARY_OPERATOR:
					if (nodes.size() < 2) {
						throw new ParseException("Binary operator '" + token.getValue() + "' does not have enough operands.");
					}
					ParseTreeNode rhs = nodes.pop();
					ParseTreeNode lhs = nodes.pop();
					nodes.push(ParseTreeNode.ofBinary(token, lhs, rhs));
					break;
				case UNARY_OPERATOR:
					if (nodes.size() < 1) {
						throw new ParseException("Unary operator '" + token.getValue() + "' does not have an operand.");
					}
					ParseTreeNode operand = nodes.pop();
					nodes.push(ParseTreeNode.ofUnary(token, operand));
					break;
				default:
					nodes.push(ParseTreeNode.ofLeaf(token));
					break;
			}
		}
		return nodes.pop();
	}
	
	private boolean greaterOrEqualPrecedence(ParseToken a, ParseToken b) {
		return a.getType() == ParseTokenType.BINARY_OPERATOR
			&& b.getType() == ParseTokenType.BINARY_OPERATOR
			&& precedenceOf(a) >= precedenceOf(b);
	}
}
