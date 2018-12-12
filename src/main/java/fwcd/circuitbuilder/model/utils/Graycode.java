package fwcd.circuitbuilder.model.utils;

import java.util.stream.LongStream;

public class Graycode {
	private Graycode() {}
	
	public static final long ofBinary(long input) {
		return input ^ (input >> 1);
	}
	
	public static final LongStream nBits(long bitCount) {
		return LongStream.range(0, 1L << bitCount)
			.map(Graycode::ofBinary);
	}
}
