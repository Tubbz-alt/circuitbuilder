package fwcd.circuitbuilder.model.logic.parse;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class OperatorPrecedenceParserTest {
	private final OperatorPrecedenceParser parser;
	
	public OperatorPrecedenceParserTest() {
		Map<String, Integer> precedenceTable = new HashMap<>();
		precedenceTable.put("=>", 3);
		precedenceTable.put("^", 4);
		precedenceTable.put("v", 3);
		parser = new OperatorPrecedenceParser(precedenceTable);
	}
	
	@Test
	public void testShuntingYard() {
		assertEquals(
			Arrays.asList("0", "1", "0", "^", "v", "0", "v", "0", "=>", "1", "=>", "0", "v"),
			parser.shuntingYard(parser.tokenize("0v 1^0v0=>0=>1v0")).stream()
				.map(ParseToken::getValue)
				.collect(Collectors.toList())
		);
	}
	
	@Test
	public void testParse() {
		assertEquals(
			op(op(op("0", "v", op("1", "^", "0")), "v", "0"), "=>", op("1", "^", "0")),
			parser.parse("0v1^0v0=>1^0")
		);
	}
	
	private ParseTreeNode lf(String leafValue) { return ParseTreeNode.ofLeaf(new ParseToken(ParseTokenType.VALUE, leafValue)); }
	
	private ParseTreeNode op(ParseTreeNode lhs, String value, ParseTreeNode rhs) { return ParseTreeNode.of(new ParseToken(ParseTokenType.OPERATOR, value), lhs, rhs); }
	
	private ParseTreeNode op(String lhs, String value, String rhs) { return op(lf(lhs), value, lf(rhs)); }
	
	private ParseTreeNode op(ParseTreeNode lhs, String value, String rhs) { return op(lhs, value, lf(rhs)); }
	
	private ParseTreeNode op(String lhs, String value, ParseTreeNode rhs) { return op(lf(lhs), value, rhs); }
}
