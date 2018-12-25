package fwcd.circuitbuilder.model.logic.minimize;

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.TruthTable;
import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.fructose.structs.IntList;

public class McCluskeyColumnTest {
	@Test
	public void testMcCluskeyColumn() {
		// x2 v (x1 ^ x0)
		LogicExpression expr = new Disjunction(new LogicVariable("x2"), new Conjunction(new LogicVariable("x1"), new LogicVariable("x0")));
		TruthTable table = new TruthTable(expr);
		McCluskeyColumn minterms = new McCluskeyColumn(table.getInputCount(), table.getBinaryMinterms());
		
		assertEquals(Stream.of(
			// x2 - x1 - x0
			0b011,
			0b100,
			0b101,
			0b110,
			0b111
		).collect(Collectors.toSet()), minterms.getImplicants().stream()
			.map(it -> it.get(0))
			.collect(Collectors.toSet()));
		
		assertEquals(Stream.of(
			// x2 - x1 - x0
			new IntList(new int[] {0b011}),
			new IntList(new int[] {0b100}),
			new IntList(new int[] {0b101}),
			new IntList(new int[] {0b110}),
			new IntList(new int[] {0b111})
		).collect(Collectors.toSet()), minterms.getImplicants().stream()
			.collect(Collectors.toSet()));
	}
}
