package fwcd.circuitbuilder.model.utils;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Graycode {
	private Graycode() {}
	
	public static final int ofBinary(int input) {
		return input ^ (input >> 1);
	}
	
	public static final IntStream nBits(int bitCount) {
		return IntStream.range(0, 1 << bitCount)
			.map(Graycode::ofBinary);
	}
	
	public static final long ofBinaryLong(long input) {
		return input ^ (input >> 1L);
	}
	
	public static final LongStream nBitsLong(long bitCount) {
		return LongStream.range(0, 1L << bitCount)
			.map(Graycode::ofBinaryLong);
	}
}
