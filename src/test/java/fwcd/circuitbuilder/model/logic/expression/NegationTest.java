package fwcd.circuitbuilder.model.logic.expression;

import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.FALSE;
import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.TRUE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NegationTest {
	@Test
	public void testNegation() {
		assertEquals(true, new Negation(FALSE).evaluate());
		assertEquals(false, new Negation(TRUE).evaluate());
	}
}
