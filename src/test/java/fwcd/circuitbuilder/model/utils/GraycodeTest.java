package fwcd.circuitbuilder.model.utils;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class GraycodeTest {
	@Test
	public void testGraycode() {
		assertArrayEquals(new long[] {
			0b0,
			0b1,
			0b11,
			0b10,
			0b110,
			0b111,
			0b101,
			0b100,
			0b1100,
			0b1101,
			0b1111,
			0b1110,
			0b1010,
			0b1011,
			0b1001,
			0b1000
		}, Graycode.nBits(4).toArray());
	}
}
