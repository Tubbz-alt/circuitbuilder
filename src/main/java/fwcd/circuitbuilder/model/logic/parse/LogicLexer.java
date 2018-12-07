package fwcd.circuitbuilder.model.logic.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.logic.parse.token.LogicToken;
import fwcd.circuitbuilder.model.logic.parse.token.LogicTokenType;
import fwcd.fructose.parsers.StringParser;

/**
 * Splits a raw logic expression into a list
 * of tokens.
 */
public class LogicLexer implements StringParser<List<LogicToken>> {
	private final Set<? extends String> symbols;
	
	public LogicLexer(Collection<? extends String> symbols) {
		this.symbols = new HashSet<>(symbols);
	}
	
	@Override
	public List<LogicToken> parse(String raw) {
		if (raw.length() == 0) {
			return Collections.emptyList();
		}
		
		Stream<LogicToken> splitted = Stream.of(new LogicToken(LogicTokenType.IDENTIFIER, raw));
		
		for (String symbol : symbols) {
			splitted = splitted
				.flatMap(it -> it.getType() == LogicTokenType.SYMBOL
					? Stream.of(it)
					: Arrays.stream(ParseUtils.splitWithDelimiter(symbol, it.getValue()))
						.map(inner -> new LogicToken(inner.equals(symbol) ? LogicTokenType.SYMBOL : LogicTokenType.IDENTIFIER, inner)));
		}
		
		return splitted
			.flatMap(it -> it.getType() != LogicTokenType.IDENTIFIER
				? Stream.of(it)
				: Arrays.stream(it.getValue().split(" "))
					.map(inner -> new LogicToken(LogicTokenType.IDENTIFIER, inner)))
			.collect(Collectors.toCollection(ArrayList::new));
	}
}