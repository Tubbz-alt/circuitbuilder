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
	private final Set<Implicant> implicants;
	/** The previously found prime implicants. */
	private final Set<Implicant> primeImplicants;
	
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
			.mapToObj(it -> new Minterm(it, bitCount))
			.map(Implicant::ofMinterm)
			.collect(Collectors.toSet());
		primeImplicants = Collections.emptySet();
	}
	
	private McCluskeyColumn(int bitCount, int implicantSize, Set<Implicant> implicants, Set<Implicant> primeImplicants) {
		this.bitCount = bitCount;
		this.implicantSize = implicantSize;
		this.implicants = implicants;
		this.primeImplicants = primeImplicants;
	}
	
	public McCluskeyColumn next() {
		Set<Implicant> nextPrimeImplicants = Stream.concat(implicants.stream(), primeImplicants.stream())
			.collect(Collectors.toSet());
		Set<Implicant> nextImplicants = new HashSet<>();
		
		for (Implicant implicantA : implicants) {
			for (Implicant implicantB : implicants) {
				if (canBeSummarized(implicantA, implicantB)) {
					nextPrimeImplicants.remove(implicantA);
					nextPrimeImplicants.remove(implicantB);
					nextImplicants.add(implicantA.concat(implicantB));
				}
			}
		}
		
		return new McCluskeyColumn(bitCount, implicantSize * 2, nextImplicants, nextPrimeImplicants);
	}
	
	public McCluskeyColumn minimize() {
		return isCompletelySummarized() ? this : next().minimize();
	}
	
	boolean canBeSummarized(Implicant mintermsA, Implicant mintermsB) {
		return mintermsA.getMintermCount() == mintermsB.getMintermCount()
			&& mintermsA.differingBits(mintermsB).count() == 1;
	}
	
	public boolean isCompletelySummarized() {
		return implicants.isEmpty();
	}
	
	public int getImplicantSize() { return implicantSize; }
	
	public int getBitCount() { return bitCount; }
	
	public Set<Implicant> getImplicants() { return implicants; }
	
	public Set<Implicant> getPrimeImplicants() { return primeImplicants; }
	
	public Set<String> getTernaryImplicants() { return McCluskeyUtils.toTernaryRepresentations(implicants, bitCount); }
	
	public Set<String> getTernaryPrimeImplicants() { return McCluskeyUtils.toTernaryRepresentations(primeImplicants, bitCount); }
}
