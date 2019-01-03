package fwcd.circuitbuilder.model.logic.minimize;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MinimizeTestUtils {
	public static Implicant implicantOf(int bitCount, int... minterms) {
		return new Implicant(Arrays.stream(minterms)
			.mapToObj(it -> new Minterm(it, bitCount))
			.collect(Collectors.toCollection(TreeSet::new)));
	}
	
	public static Set<Implicant> implicantsOf(int bitCount, int... implicants) {
		return implicantsOf(bitCount, Arrays.stream(implicants)
			.mapToObj(it -> new int[] {it})
			.toArray(int[][]::new));
	}
	
	public static Set<Implicant> implicantsOf(int bitCount, int[][] implicants) {
		return Arrays.stream(implicants)
			.map(minterms -> mintermsOf(bitCount, minterms))
			.map(Implicant::new)
			.collect(Collectors.toSet());
	}
	
	public static SortedSet<Minterm> mintermsOf(int bitCount, int... minterms) {
		return Arrays.stream(minterms)
				.mapToObj(minterm -> new Minterm(minterm, bitCount))
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	@SafeVarargs
	public static <T> Set<T> setOf(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}
	
	public static <T> void assertSetEquals(Set<T> expected, Set<T> actual) {
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
	
	public static <T> void assertEitherEquals(T expectedA, T expectedB, T actual) {
		if (!actual.equals(expectedA) && !actual.equals(expectedB)) {
			fail("Expected either " + expectedA + " or " + expectedB + " but was " + actual);
		}
	}
}
