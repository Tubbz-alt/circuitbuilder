package fwcd.circuitbuilder.model.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class BoolUtilsTest {
	@Test
	public void testBoolUtils() {
		assertEquals(true, BoolUtils.toBoolean(1));
		assertEquals(false, BoolUtils.toBoolean(0));
		assertEquals(1, BoolUtils.toBit(true));
		assertEquals(0, BoolUtils.toBit(false));
		assertArrayEquals(new boolean[] {true, false, true}, BoolUtils.toBooleans(1, 0, 1));
		assertArrayEquals(new int[] {1, 1, 0, 1}, BoolUtils.toBits(true, true, false, true));
		assertArrayEquals(new int[] {1, 0}, BoolUtils.toBitStream(Stream.of(true, false)).toArray());
		assertArrayEquals(new boolean[] {true, false, false}, BoolUtils.toArray(BoolUtils.toBooleanStream(IntStream.of(1, 0, 0))));
		assertEquals(0b10110, BoolUtils.toBinary(1, 0, 1, 1, 0));
		assertArrayEquals(new int[] {1, 0, 1}, BoolUtils.binaryToBits(0b101));
		assertArrayEquals(new int[] {1, 0, 1, 1}, BoolUtils.concat(new int[] {1, 0}, new int[] {1, 1}));
		assertEquals(0b110010, BoolUtils.concatBinary(0b11, 0b10, 4));
	}
}
