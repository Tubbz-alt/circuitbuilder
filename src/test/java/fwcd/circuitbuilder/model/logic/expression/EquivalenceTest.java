package fwcd.circuitbuilder.model.logic.expression;

import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.FALSE;
import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.TRUE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EquivalenceTest {
	@Test
	public void testEquivalence() {
		assertEquals(true, new Equivalence(FALSE, FALSE).evaluate());
		assertEquals(false, new Equivalence(FALSE, TRUE).evaluate());
		assertEquals(false, new Equivalence(TRUE, FALSE).evaluate());
		assertEquals(true, new Equivalence(TRUE, TRUE).evaluate());
	}
}
