package fwcd.circuitbuilder.model.logic.minimize;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fwcd.circuitbuilder.model.utils.BoolUtils;

class McCluskeyUtils {
	/**
	 * Converts summarized, binary implicants to a "ternary"
	 * bit representation with the third symbol being a '-':
	 * 
	 * <p><pre>
	 * 0 1 - 1
	 * 
	 * representing
	 * 
	 * 0 1 1 1
	 * 0 1 0 1
	 * </pre></p>
	 */
	public static Set<String> toTernaryRepresentations(Set<Set<Integer>> implicants, int bitCount) {
		return implicants.stream()
			.map(it -> toTernaryRepresentation(it, bitCount))
			.collect(Collectors.toSet());
	}
	
	public static String toTernaryRepresentation(Set<Integer> implicants, int bitCount) {
		if (implicants.isEmpty()) {
			return "";
		}
		Iterator<Integer> it = implicants.iterator();
		StringBuilder str = new StringBuilder(BoolUtils.toBinaryString(it.next(), bitCount));
		
		while (it.hasNext()) {
			int binary = it.next();
			for (int i = 0; i < bitCount; i++) {
				if (BoolUtils.bitFromLeft(binary, i, bitCount) != Character.digit(str.charAt(i), 2)) {
					str.setCharAt(i, '-');
				}
			}
		}
		
		return str.toString();
	}
	
	public static IntStream differingBits(Set<Integer> a, Set<Integer> b, int bitCount) {
		return differingBits(
			a.stream().mapToInt(Integer::valueOf).toArray(),
			b.stream().mapToInt(Integer::valueOf).toArray(),
			bitCount
		);
	}
	
	public static IntStream differingBits(int[] a, int[] b, int bitCount) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Arrays have different lengths: " + Arrays.toString(a) + ", " + Arrays.toString(b));
		}
		return IntStream.range(0, a.length)
			.flatMap(i -> differingBits(a[i], b[i], bitCount))
			.distinct();
	}
	
	/**
	 * Fetches the indices (from right) of the differing
	 * bits between the two numbers interpreted using
	 * the given bit count.
	 */
	public static IntStream differingBits(int a, int b, int bitCount) {
		return IntStream.range(0, bitCount)
			.filter(i -> BoolUtils.bitFromRight(a, i) != BoolUtils.bitFromRight(b, i));
	}
	
	public static int[] concat(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
}
