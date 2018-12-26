package fwcd.circuitbuilder.model.logic.minimize;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import org.junit.Test;

import fwcd.circuitbuilder.model.logic.TruthTable;
import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.Disjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;

public class McCluskeyColumnTest {
	@Test
	public void testMcCluskeyColumn() {
		// x2 v (x1 ^ x0)
		LogicExpression expr = new Disjunction(new LogicVariable("x2"), new Conjunction(new LogicVariable("x1"), new LogicVariable("x0")));
		TruthTable table = new TruthTable(expr);
		McCluskeyColumn minterms = new McCluskeyColumn(table.getInputCount(), table.getBinaryMinterms());

		assertEquals(Stream.of(
				// x2 - x1 - x0
				0b011, 0b100, 0b101, 0b110, 0b111).collect(toSet()),
				minterms.getImplicants().stream().flatMap(Collection::stream)
			.collect(toSet()));
		
		assertEquals(Stream.of(
			// x2 - x1 - x0
			Collections.singleton(0b011),
			Collections.singleton(0b100),
			Collections.singleton(0b101),
			Collections.singleton(0b110),
			Collections.singleton(0b111)
		).collect(toSet()), minterms.getImplicants().stream().collect(toSet()));
		
		LogicExpression kdnf = fromKDNF(
			"nx3 nx2 nx1 nx0",
			"nx3 nx2 nx1 x0",
			"nx3 x2 nx1 nx0",
			"nx3 x2 nx1 x0",
			"nx3 x2 x1 nx0",
			"nx3 x2 x1 x0",
			"x3 nx2 nx1 nx0",
			"x3 nx2 nx1 x0",
			"x3 nx2 x1 x0",
			"x3 x2 x1 x0"
		);
		int[] binaryMinterms = new TruthTable(kdnf).getBinaryMinterms()
			.toArray();
		assertArrayEquals(new int[] {
			0b0000,
			0b0001,
			0b0100,
			0b0101,
			0b0110,
			0b0111,
			0b1000,
			0b1001,
			0b1011,
			0b1111
		}, binaryMinterms);
		
		McCluskeyColumn first = new McCluskeyColumn(4, binaryMinterms);
		McCluskeyColumn second = first.next();
		McCluskeyColumn third = second.next();
		McCluskeyColumn fourth = third.next();
		McCluskeyColumn minimized = first.minimize();
		
		assertEquals(Stream.of(
			"0000",
			"0001",
			"0100",
			"1000",
			"0101",
			"0110",
			"1001",
			"0111",
			"1011",
			"1111"
		).collect(toSet()), first.getTernaryImplicants());
		assertEquals(Stream.of(
			"000-",
			"0-00",
			"-000",
			"0-01",
			"-001",
			"01-0",
			"010-",
			"100-",
			"01-1",
			"011-",
			"10-1",
			"-111",
			"1-11"
		).collect(toSet()), second.getTernaryImplicants());
		assertEquals(Stream.of(
			"0-0-",
			"-00-",
			"01--"
		).collect(toSet()), third.getTernaryImplicants());
		assertEquals(fourth.getPrimeImplicants(), minimized.getPrimeImplicants());
		
		assertEquals(Stream.of(
			Stream.of(9, 11).collect(toSet()),
			Stream.of(7, 15).collect(toSet()),
			Stream.of(11, 15).collect(toSet()),
			Stream.of(0, 1, 4, 5).collect(toSet()),
			Stream.of(0, 1, 8, 9).collect(toSet()),
			Stream.of(4, 6, 5, 7).collect(toSet())
		).collect(toSet()), minimized.getPrimeImplicants());
	}
	
	private LogicExpression fromKDNF(String... minterms) {
		return Arrays.stream(minterms)
			.<LogicExpression>map(conj -> Arrays.stream(conj.split(" "))
				.<LogicExpression>map(v -> {
					if (v.startsWith("n")) {
						return new Negation(new LogicVariable(v.substring(1)));
					} else {
						return new LogicVariable(v);
					}
				}).reduce(Conjunction::new)
				.orElse(LogicBoolean.FALSE))
			.reduce((LogicExpression a, LogicExpression b) -> new Disjunction(a, b))
			.orElse(LogicBoolean.TRUE);
	}
}