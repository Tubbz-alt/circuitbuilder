package fwcd.circuitbuilder.model.logic.minimize;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fwcd.fructose.structs.IntList;

public class McCluskeyColumn {
	private final int bitCount;
	private final int implicantSize;
	private final Set<IntList> implicants;
	/** The previously found prime implicants. */
	private final Set<IntList> primeImplicants;
	
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
			.mapToObj(mt -> new IntList(new int[] {mt}))
			.collect(Collectors.toSet());
		primeImplicants = Collections.emptySet();
	}
	
	private McCluskeyColumn(int bitCount, int implicantSize, Set<IntList> implicants, Set<IntList> primeImplicants) {
		this.bitCount = bitCount;
		this.implicantSize = implicantSize;
		this.implicants = implicants;
		this.primeImplicants = primeImplicants;
	}
	
	public McCluskeyColumn next() {
		Set<IntList> nextPrimeImplicants = Stream.concat(implicants.stream(), primeImplicants.stream())
			.collect(Collectors.toSet());
		Set<IntList> nextImplicants = new HashSet<>();
		
		for (IntList implicantA : implicants) {
			for (IntList implicantB : implicants) {
				if (implicantA != implicantB && canBeSummarized(implicantA, implicantB)) {
					nextPrimeImplicants.remove(implicantA);
					nextPrimeImplicants.remove(implicantB);
					nextImplicants.add(new IntList(McCluskeyUtils.concat(implicantA.toArray(), implicantB.toArray())));
				}
			}
		}
		
		return new McCluskeyColumn(bitCount, implicantSize * 2, nextImplicants, nextPrimeImplicants);
	}
	
	boolean canBeSummarized(IntList mintermsA, IntList mintermsB) {
		int[] differing = McCluskeyUtils.differingBits(mintermsA.toArray(), mintermsB.toArray(), bitCount)
			.distinct()
			.toArray();
		return differing.length == implicantSize;
	}
	
	public boolean isCompletelySummarized() {
		return implicants.isEmpty();
	}
	
	public int getImplicantSize() { return implicantSize; }
	
	public int getBitCount() { return bitCount; }
	
	public Set<IntList> getImplicants() { return implicants; }
	
	public Set<IntList> getPrimeImplicants() { return primeImplicants; }
}
