package fwcd.circuitbuilder.model.logic.transform;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.ExpressionUtils;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;

public class CCNFProviderTest {
	@Test
	public void testCCNF() {
		LogicExpression or = new Disjunction(new LogicVariable("x1"), new LogicVariable("x0"));
		ExpressionTransformer provider = new CCNFProvider();
		
		assertEquals(ExpressionUtils.and(
			ExpressionUtils.or(
				new LogicVariable("x1"),
				new LogicVariable("x0")
			)
		), provider.transform(or));
	}
}
