package com.fwcd.circuitbuilder.model.logic.parse;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.fwcd.circuitbuilder.model.logic.parse.token.LogicToken;

import org.junit.Test;

public class LexerTest {
	@Test
	public void testLexer() {
		LogicLexer lexer = new LogicLexer(Arrays.asList("=>", "(", ")"));
		assertEquals(
			Arrays.asList("(", "an", "expression", "(", "=>", ")", ")"),
			lexer.parse("(an expression ( =>) )").stream().map(LogicToken::getValue).collect(Collectors.toList())
		);
	}
}