package fwcd.circuitbuilder.model.logic.expression;

import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.FALSE;
import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.TRUE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DisjunctionTest {
	@Test
	public void testDisjunction() {
		assertEquals(false, new Disjunction(FALSE, FALSE).evaluate());
		assertEquals(true, new Disjunction(FALSE, TRUE).evaluate());
		assertEquals(true, new Disjunction(TRUE, FALSE).evaluate());
		assertEquals(true, new Disjunction(TRUE, TRUE).evaluate());
	}
}
