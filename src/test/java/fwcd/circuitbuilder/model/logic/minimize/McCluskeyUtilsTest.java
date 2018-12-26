package fwcd.circuitbuilder.model.logic.minimize;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class McCluskeyUtilsTest {
	@Test
	public void testConcat() {
		assertArrayEquals(new int[] {3, 2, 4, 5, 1}, McCluskeyUtils.concat(new int[] {3, 2}, new int[] {4, 5, 1}));
	}
	
	@Test
	public void testDifferingBits() {
		assertArrayEquals(new int[] {1}, McCluskeyUtils.differingBits(0b1101, 0b1111, 4).toArray());
		assertArrayEquals(new int[] {1, 2, 3}, McCluskeyUtils.differingBits(0b0001, 0b1111, 4).toArray());
		
		assertArrayEquals(new int[] {1, 2}, McCluskeyUtils.differingBits(new int[] {
			0b1011,
			0b1111
		}, new int[] {
			0b1101,
			0b1111
		}, 4).toArray());
		assertArrayEquals(new int[] {0}, McCluskeyUtils.differingBits(new int[] {
			//--110
			0b10110,
			0b11110,
			0b00110,
			0b01110
		}, new int[] {
			//--111
			0b10111,
			0b11111,
			0b00111,
			0b01111
		}, 4).toArray());
	}
	
	@Test
	public void testTernaryRepresentation() {
		assertEquals("--111", McCluskeyUtils.toTernaryRepresentation(Stream.of(
			0b10111,
			0b11111,
			0b00111,
			0b01111
		).collect(Collectors.toSet()), 5));
		assertEquals("0-110", McCluskeyUtils.toTernaryRepresentation(Stream.of(
			0b00110,
			0b01110,
			0b00110,
			0b01110
		).collect(Collectors.toSet()), 5));
	}
}
