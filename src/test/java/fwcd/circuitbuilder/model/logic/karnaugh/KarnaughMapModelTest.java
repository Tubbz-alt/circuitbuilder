package fwcd.circuitbuilder.model.logic.karnaugh;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;

public class KarnaughMapModelTest {
	@Test
	public void testKarnaughMap() {
		LogicExpression expression = new Disjunction(new LogicVariable("x1"), new LogicVariable("x0"));
		KarnaughMapModel map = new KarnaughMapModel(expression, Arrays.asList("x1", "x0"));
		
		assertArrayEquals(new boolean[][] {
			{false, true},
			{true, true}
		}, map.getCells());
	}
}
