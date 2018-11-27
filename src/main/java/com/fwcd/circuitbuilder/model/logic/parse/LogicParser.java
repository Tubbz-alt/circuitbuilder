package com.fwcd.circuitbuilder.model.logic.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import com.fwcd.fructose.exception.TodoException;
import com.fwcd.fructose.parsers.StringParser;

public class LogicParser implements StringParser<LogicExpression> {
	private final List<NotationPattern> precedencePatterns;
	
	public LogicParser(LogicNotation notation) {
		precedencePatterns = notation.getPatterns()
			.stream()
			.sorted((a, b) -> Integer.compare(a.getPrecedence(), b.getPrecedence()))
			.collect(Collectors.toCollection(ArrayList::new));
	}
	
	@Override
	public LogicExpression parse(String raw) {
		throw new TodoException();
	}
}
