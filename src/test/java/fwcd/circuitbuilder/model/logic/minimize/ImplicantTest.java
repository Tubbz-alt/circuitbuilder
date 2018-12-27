package fwcd.circuitbuilder.model.logic.minimize;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class ImplicantTest {
	@Test
	public void testTernaryRepresentation() {
		assertEquals("--111", implicantOf(5,
			0b10111,
			0b11111,
			0b00111,
			0b01111
		).toTernaryRepresentation());
		assertEquals("0-110", implicantOf(5,
			0b00110,
			0b01110,
			0b00110,
			0b01110
		).toTernaryRepresentation());
	}
	
	private Implicant implicantOf(int bitCount, int... minterms) {
		return new Implicant(Arrays.stream(minterms)
			.mapToObj(it -> new Minterm(it, bitCount))
			.collect(Collectors.toCollection(TreeSet::new)));
	}
}
