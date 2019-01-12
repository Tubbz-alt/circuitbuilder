package fwcd.circuitbuilder.model.utils;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

public class ValueOverTimeTest {
	@Test
	public void testValueOverTime() {
		ValueOverTime vot = new ValueOverTime("VOT", 4);
		vot.add(true);
		vot.add(true);
		vot.add(false);
		assertVOTEquals(new boolean[] {false, true, true}, vot);
		
		vot.add(false);
		vot.add(true);
		assertVOTEquals(new boolean[] {true, false, false, true}, vot);
	}
	
	private void assertVOTEquals(boolean[] bools, ValueOverTime vot) {
		assertArrayEquals(bools, Arrays.copyOf(vot.getRawValues(), vot.getValueCount()));
	}
}
