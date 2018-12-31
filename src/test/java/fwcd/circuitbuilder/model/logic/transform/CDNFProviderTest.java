package fwcd.circuitbuilder.model.logic.transform;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.ExpressionUtils;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;

public class CDNFProviderTest {
	@Test
	public void testCDNF() {
		LogicExpression expr = new Disjunction(new LogicVariable("x1"), new LogicVariable("x0"));
		ExpressionTransformer provider = new CDNFProvider();
		
		assertEquals(ExpressionUtils.or(
			ExpressionUtils.and(
				new Negation(new LogicVariable("x1")),
				new LogicVariable("x0")
			),
			ExpressionUtils.and(
				new LogicVariable("x1"),
				new Negation(new LogicVariable("x0"))
			),
			ExpressionUtils.and(
				new LogicVariable("x1"),
				new LogicVariable("x0")
			)
		), provider.transform(expr));
	}
}
