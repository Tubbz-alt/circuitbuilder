package fwcd.circuitbuilder.model.logic.expression;

import static org.junit.Assert.assertEquals;
import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.TRUE;
import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.FALSE;

import org.junit.Test;

public class ConjunctionTest {
	@Test
	public void testConjunction() {
		assertEquals(false, new Conjunction(FALSE, FALSE).evaluate());
		assertEquals(false, new Conjunction(FALSE, TRUE).evaluate());
		assertEquals(false, new Conjunction(TRUE, FALSE).evaluate());
		assertEquals(true, new Conjunction(TRUE, TRUE).evaluate());
	}
}
