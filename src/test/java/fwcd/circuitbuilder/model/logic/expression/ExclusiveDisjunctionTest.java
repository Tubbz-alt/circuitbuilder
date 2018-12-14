package fwcd.circuitbuilder.model.logic.expression;

import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.FALSE;
import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.TRUE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExclusiveDisjunctionTest {
	@Test
	public void testExclusiveDisjunction() {
		assertEquals(false, new ExclusiveDisjunction(FALSE, FALSE).evaluate());
		assertEquals(true, new ExclusiveDisjunction(FALSE, TRUE).evaluate());
		assertEquals(true, new ExclusiveDisjunction(TRUE, FALSE).evaluate());
		assertEquals(false, new ExclusiveDisjunction(TRUE, TRUE).evaluate());
	}
}
