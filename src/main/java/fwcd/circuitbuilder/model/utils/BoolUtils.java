package fwcd.circuitbuilder.model.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A collection of useful methods for operations on
 * sequences of bits and booleans.
 */
public class BoolUtils {
	private BoolUtils() {}
	
	/**
	 * Converts an array of zeros and ones to booleans.
	 */
	public static boolean[] toBooleans(int... bits) {
		boolean[] result = new boolean[bits.length];
		for (int i = 0; i < bits.length; i++) {
			result[i] = toBoolean(bits[i]);
		}
		return result;
	}
	
	/**
	 * Converts an array to booleans to zeros and ones.
	 */
	public static int[] toBits(boolean... bools) {
		int[] result = new int[bools.length];
		for (int i = 0; i < bools.length; i++) {
			result[i] = toBit(bools[i]);
		}
		return result;
	}
	
	public static boolean[] toArray(Stream<Boolean> boolStream) {
		int[] bits = toBitStream(boolStream).toArray();
		boolean[] bools = new boolean[bits.length];
		for (int i = 0; i < bits.length; i++) {
			bools[i] = toBoolean(bits[i]);
		}
		return bools;
	}
	
	public static IntStream toBitStream(Stream<Boolean> boolStream) {
		return boolStream.mapToInt(BoolUtils::toBit);
	}
	
	public static Stream<Boolean> toBooleanStream(IntStream bitStream) {
		return bitStream.mapToObj(BoolUtils::toBoolean);
	}
	
	public static int toBinary(boolean... bools) {
		int result = 0;
		for (boolean bool : bools) {
			result <<= 1;
			result |= toBit(bool);
		}
		return result;
	}
	
	public static int toBinary(int... bits) {
		return toBinary(toBooleans(bits));
	}
	
	public static int[] binaryToBits(int binary, int bitCount) {
		int[] bits = new int[bitCount];
		for (int i = 0; i < bits.length; i++) {
			bits[(bits.length - 1) - i] = (binary >> i) & 1;
		}
		return bits;
	}
	
	public static int[] binaryToBits(int binary) {
		return binaryToBits(binary, Math.max(0, Integer.highestOneBit(binary) - 1));
	}
	
	public static boolean[] binaryToBooleans(int binary, int bitCount) {
		return toBooleans(binaryToBits(binary, bitCount));
	}
	
	public static boolean[] binaryToBooleans(int binary) {
		return toBooleans(binaryToBits(binary));
	}
	
	public static int concatBinary(int a, int b, int bBitCount) {
		return (a << bBitCount) | b;
	}
	
	public static int[] concat(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
	
	public static boolean[] concat(boolean[] a, boolean[] b) {
		boolean[] result = new boolean[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
	
	public static IntStream binaryToBitStream(int binary) {
		return Arrays.stream(binaryToBits(binary));
	}
	
	public static Stream<Boolean> binaryToBooleanStream(int binary) {
		return toBooleanStream(binaryToBitStream(binary));
	}
	
	public static boolean toBoolean(int bit) {
		if (bit == 1) {
			return true;
		} else if (bit == 0) {
			return false;
		} else {
			throw new IllegalArgumentException("Can not convert " + bit + " (which is neither 0 nor 1) to a boolean");
		}
	}
	
	public static int toBit(boolean value) {
		return value ? 1 : 0;
	}
	
	public static <T> Map<T, Boolean> toMap(List<? extends T> names, boolean... bools) {
		Map<T, Boolean> result = new HashMap<>();
		if (bools.length != names.size()) {
			throw new IllegalArgumentException("Length of names list (" + names.size() + ") does not match length of bools (" + bools.length + ")");
		}
		for (int i = 0; i < bools.length; i++) {
			result.put(names.get(i), bools[i]);
		}
		return result;
	}
}
