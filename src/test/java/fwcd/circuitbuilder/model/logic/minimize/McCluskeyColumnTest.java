package fwcd.circuitbuilder.model.logic.minimize;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
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
		
		assertSetEquals(implicantsOf(3,
			// x2 - x1 - x0
			0b011,
			0b100,
			0b101,
			0b110,
			0b111
		), minterms.getImplicants());
		
		assertSetEquals(implicantsOf(3,
			// x2 - x1 - x0
			0b011,
			0b100,
			0b101,
			0b110,
			0b111
		), minterms.getImplicants());
		
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
		
		assertSetEquals(setOf(
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
		), first.getTernaryImplicants());
		assertSetEquals(setOf(
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
		), second.getTernaryImplicants());
		assertSetEquals(setOf(
			"0-0-",
			"-00-",
			"01--"
		), third.getTernaryImplicants());
		assertEquals(fourth.getPrimeImplicants(), minimized.getPrimeImplicants());
		
		assertSetEquals(implicantsOf(4, new int[][] {
			{9, 11},
			{7, 15},
			{11, 15},
			{0, 1, 4, 5},
			{0, 1, 8, 9},
			{4, 6, 5, 7}
		}), minimized.getPrimeImplicants());
	}
	
	private Set<Implicant> implicantsOf(int bitCount, int... implicants) {
		return implicantsOf(bitCount, Arrays.stream(implicants)
			.mapToObj(it -> new int[] {it})
			.toArray(int[][]::new));
	}
	
	private Set<Implicant> implicantsOf(int bitCount, int[][] implicants) {
		return Arrays.stream(implicants)
			.map(minterms -> Arrays.stream(minterms)
				.mapToObj(minterm -> new Minterm(minterm, bitCount))
				.collect(Collectors.toCollection(TreeSet::new)))
			.map(Implicant::new)
			.collect(Collectors.toSet());
	}
	
	@SafeVarargs
	private final <T> Set<T> setOf(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}
	
	private <T> void assertSetEquals(Set<T> expected, Set<T> actual) {
		Set<T> additionallyExpected = new HashSet<>();
		Set<T> unexpectedlyActual = new HashSet<>();
		
		for (T item : expected) {
			if (!actual.contains(item)) {
				additionallyExpected.add(item);
			}
		}
		for (T item : actual) {
			if (!expected.contains(item)) {
				unexpectedlyActual.add(item);
			}
		}
		
		if (!additionallyExpected.isEmpty() || !unexpectedlyActual.isEmpty()) {
			fail("Expected: " + expected + " but was: " + actual + " - expected but not actual: " + additionallyExpected + ", actual but not expected: " + unexpectedlyActual);
		}
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
