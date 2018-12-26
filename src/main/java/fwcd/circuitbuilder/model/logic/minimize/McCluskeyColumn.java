package fwcd.circuitbuilder.model.logic.minimize;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class McCluskeyColumn {
	private final int bitCount;
	private final int implicantSize;
	private final Set<Set<Integer>> implicants;
	/** The previously found prime implicants. */
	private final Set<Set<Integer>> primeImplicants;
	
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
			.mapToObj(Collections::singleton)
			.collect(Collectors.toSet());
		primeImplicants = Collections.emptySet();
	}
	
	private McCluskeyColumn(int bitCount, int implicantSize, Set<Set<Integer>> implicants, Set<Set<Integer>> primeImplicants) {
		this.bitCount = bitCount;
		this.implicantSize = implicantSize;
		this.implicants = implicants;
		this.primeImplicants = primeImplicants;
	}
	
	public McCluskeyColumn next() {
		Set<Set<Integer>> nextPrimeImplicants = Stream.concat(implicants.stream(), primeImplicants.stream())
			.collect(Collectors.toSet());
		Set<Set<Integer>> nextImplicants = new HashSet<>();
		
		for (Set<Integer> implicantA : implicants) {
			for (Set<Integer> implicantB : implicants) {
				if (Collections.disjoint(implicantA, implicantB) && canBeSummarized(implicantA, implicantB)) {
					nextPrimeImplicants.remove(implicantA);
					nextPrimeImplicants.remove(implicantB);
					
					Set<Integer> concat = new HashSet<>();
					concat.addAll(implicantA);
					concat.addAll(implicantB);
					
					nextImplicants.add(concat);
				}
			}
		}
		
		return new McCluskeyColumn(bitCount, implicantSize * 2, nextImplicants, nextPrimeImplicants);
	}
	
	public McCluskeyColumn minimize() {
		return isCompletelySummarized() ? this : next().minimize();
	}
	
	boolean canBeSummarized(Set<Integer> mintermsA, Set<Integer> mintermsB) {
		if (mintermsA.size() != mintermsB.size()) {
			return false;
		}
		int[] differing = McCluskeyUtils.differingBits(mintermsA, mintermsB, bitCount)
			.distinct()
			.toArray();
		return differing.length == 1;
	}
	
	public boolean isCompletelySummarized() {
		return implicants.isEmpty();
	}
	
	public int getImplicantSize() { return implicantSize; }
	
	public int getBitCount() { return bitCount; }
	
	public Set<Set<Integer>> getImplicants() { return implicants; }
	
	public Set<Set<Integer>> getPrimeImplicants() { return primeImplicants; }
	
	public Set<String> getTernaryImplicants() { return McCluskeyUtils.toTernaryRepresentations(implicants, bitCount); }
	
	public Set<String> getTernaryPrimeImplicants() { return McCluskeyUtils.toTernaryRepresentations(primeImplicants, bitCount); }
}
