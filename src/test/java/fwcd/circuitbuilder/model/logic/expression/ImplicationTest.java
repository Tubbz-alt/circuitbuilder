package fwcd.circuitbuilder.model.logic.expression;

import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.FALSE;
import static fwcd.circuitbuilder.model.logic.expression.LogicBoolean.TRUE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImplicationTest {
	@Test
	public void testImplication() {
		assertEquals(true, new Implication(FALSE, FALSE).evaluate());
		assertEquals(true, new Implication(FALSE, TRUE).evaluate());
		assertEquals(false, new Implication(TRUE, FALSE).evaluate());
		assertEquals(true, new Implication(TRUE, TRUE).evaluate());
	}
}
