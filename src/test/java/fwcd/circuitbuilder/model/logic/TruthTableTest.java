package fwcd.circuitbuilder.model.logic;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;

public class TruthTableTest {
	@Test
	public void testTruthTable() {
		LogicExpression and = new Conjunction(new LogicVariable("x1"), new LogicVariable("x0"));
		TruthTable andTable = new TruthTable(and);
		assertArrayEquals(new boolean[] {
			/* f, f -> */ false,
			/* f, t -> */ false,
			/* t, f -> */ false,
			/* t, t -> */ true
		}, andTable.getOutputs());
		assertArrayEquals(new int[] {
			0b11
		}, andTable.getBinaryMinterms().toArray());
		
		LogicExpression or = new Disjunction(new LogicVariable("x1"), new LogicVariable("x0"));
		TruthTable orTable = new TruthTable(or);
		assertArrayEquals(new boolean[] {
			/* f, f -> */ false,
			/* f, t -> */ true,
			/* t, f -> */ true,
			/* t, t -> */ true
		}, orTable.getOutputs());
		assertArrayEquals(new int[] {
			0b01,
			0b10,
			0b11
		}, orTable.getBinaryMinterms().toArray());
	}
}
