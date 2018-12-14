package fwcd.circuitbuilder.model.logic.karnaugh;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;

public class KarnaughMapModelTest {
	@Test
	public void testKarnaughMap() {
		LogicExpression or = new Disjunction(new LogicVariable("x1"), new LogicVariable("x0"));
		KarnaughMapModel map1 = new KarnaughMapModel(or, Arrays.asList("x1", "x0"));
		
		assertArrayEquals(new boolean[][] {
			{false, true},
			{true,  true}
		}, map1.getCells());
		
		LogicExpression andThree = new Conjunction(new LogicVariable("x2"), new Conjunction(new LogicVariable("x1"), new LogicVariable("x0")));
		KarnaughMapModel map2 = new KarnaughMapModel(andThree, Arrays.asList("x2", "x1", "x0"));
		
		assertEquals(0b000, map2.getCode(0, 0));
		assertEquals(0b010, map2.getCode(1, 0));
		assertEquals(0b110, map2.getCode(2, 0));
		assertEquals(0b100, map2.getCode(3, 0));
		assertEquals(0b001, map2.getCode(0, 1));
		assertEquals(0b011, map2.getCode(1, 1));
		assertEquals(0b111, map2.getCode(2, 1));
		assertEquals(0b101, map2.getCode(3, 1));
		assertArrayEquals(new boolean[][] {
			{false, false, false, false},
			{false, false, true,  false}
		}, map2.getCells());
	}
}
