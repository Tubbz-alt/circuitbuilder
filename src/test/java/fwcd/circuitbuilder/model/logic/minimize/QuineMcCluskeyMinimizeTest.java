package fwcd.circuitbuilder.model.logic.minimize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.ExpressionTestUtils;
import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.transform.ExpressionTransformer;

public class QuineMcCluskeyMinimizeTest {
	@Test
	public void testQuineMcCluskey() {
		ExpressionTransformer minimizer = new QuineMcCluskeyMinimizer();
		assertEquals(LogicBoolean.TRUE, minimizer.transform(ExpressionTestUtils.fromDNF("a b", "1", "b")));
		assertEquals(LogicBoolean.FALSE, minimizer.transform(ExpressionTestUtils.fromDNF("0 b")));
	}
}
