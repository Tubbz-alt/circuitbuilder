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
}
