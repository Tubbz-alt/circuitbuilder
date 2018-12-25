package fwcd.circuitbuilder.model.logic.minimize;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class McCluskeyColumn {
	private final int bitCount;
	private final int implicantSize;
	private final Set<int[]> implicants;
	/** The previously found prime implicants. */
	private final Set<int[]> primeImplicants;
	
	public McCluskeyColumn(int bitCount, int... binaryMinterms) {
		this(bitCount, Arrays.stream(binaryMinterms));
	}
	
	/**
	 * Creates the first column in the McCluskey table
	 * using the minterms of the function.
	 * 
	 * @param bitCount
	 * @param binaryMinterms
	 */
	public McCluskeyColumn(int bitCount, IntStream binaryMinterms) {
		this.bitCount = bitCount;
		implicantSize = 1;
		implicants = binaryMinterms
			.mapToObj(mt -> new int[] {mt})
			.collect(Collectors.toSet());
		primeImplicants = Collections.emptySet();
	}
	
	private McCluskeyColumn(int bitCount, int implicantSize, Set<int[]> implicants, Set<int[]> primeImplicants) {
		this.bitCount = bitCount;
		this.implicantSize = implicantSize;
		this.implicants = implicants;
		this.primeImplicants = primeImplicants;
	}
	
	public McCluskeyColumn next() {
		Set<int[]> nextPrimeImplicants = Stream.concat(implicants.stream(), primeImplicants.stream())
			.collect(Collectors.toSet());
		Set<int[]> nextImplicants = new HashSet<>();
		
		for (int[] implicantA : implicants) {
			for (int[] implicantB : implicants) {
				if (implicantA != implicantB && canBeSummarized(implicantA, implicantB)) {
					nextPrimeImplicants.remove(implicantA);
					nextPrimeImplicants.remove(implicantB);
					nextImplicants.add(concat(implicantA, implicantB));
				}
			}
		}
		
		return new McCluskeyColumn(bitCount, implicantSize * 2, nextImplicants, nextPrimeImplicants);
	}
	
	private boolean canBeSummarized(int[] mintermsA, int[] mintermsB) {
		int[] differing = differingBits(mintermsA, mintermsB)
			.distinct()
			.toArray();
		return differing.length == implicantSize;
	}
	
	private int[] concat(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
	
	private IntStream differingBits(int[] a, int[] b) {
		if (a.length != b.length) {
			throw new IllegalArgumentException("Arrays have different lengths: " + Arrays.toString(a) + ", " + Arrays.toString(b));
		}
		return IntStream.range(0, a.length)
			.filter(i -> a[i] != b[i]);
	}
	
	public boolean isCompletelySummarized() {
		return implicants.isEmpty();
	}
	
	public int getImplicantSize() { return implicantSize; }
	
	public int getBitCount() { return bitCount; }
	
	public List<int[]> getImplicants() { return implicants; }
	
	public List<int[]> getPrimeImplicants() { return primeImplicants; }
}
