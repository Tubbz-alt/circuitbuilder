package fwcd.circuitbuilder.model.logic.minimize;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;
import fwcd.circuitbuilder.model.utils.BoolUtils;

/**
 * A grouping of minterms.
 */
public class Implicant {
	private final SortedSet<Minterm> minterms;
	private final int bitCount;
	
	public Implicant(SortedSet<Minterm> minterms) {
		this.minterms = minterms;
		bitCount = (minterms.size() > 0)
			? minterms.iterator().next().getBitCount()
			: 0;
	}
	
	public static Implicant ofMinterm(Minterm minterm) {
		return new Implicant(Stream.of(minterm).collect(Collectors.toCollection(TreeSet::new)));
	}
	
	public IntStream differingBits(Implicant other) {
		if (bitCount != other.bitCount) {
			throw new IllegalArgumentException("Implicants have different bit counts: " + minterms + ", " + other.minterms);
		}
		
		return McCluskeyUtils.differingBits(toArray(), other.toArray(), bitCount);
	}
	
	public int[] toArray() {
		return minterms.stream()
			.mapToInt(Minterm::getBinary)
			.toArray();
	}
	
	public Implicant concat(Implicant other) {
		SortedSet<Minterm> concatenated = new TreeSet<>(minterms);
		concatenated.addAll(other.minterms);
		return new Implicant(concatenated);
	}
	
	public LogicExpression toExpression(List<String> variables) {
		String ternary = toTernaryRepresentation();
		return IntStream.range(0, ternary.length())
			.filter(i -> ternary.charAt(i) != '-')
			.mapToObj(i -> (ternary.charAt(i) == '1')
				? new LogicVariable(variables.get(i))
				: new Negation(new LogicVariable(variables.get(i))))
			.reduce(Conjunction::new)
			.orElse(LogicBoolean.FALSE);
	}
	
	public int getMintermCount() {
		return minterms.size();
	}
	
	public String toTernaryRepresentation() {
		if (minterms.isEmpty()) {
			return "";
		}
		Iterator<Minterm> it = minterms.iterator();
		Minterm first = it.next();
		StringBuilder str = new StringBuilder(first.toBinaryString());
		
		while (it.hasNext()) {
			int binary = it.next().getBinary();
			for (int i = 0; i < bitCount; i++) {
				if (BoolUtils.bitFromLeft(binary, i, bitCount) != Character.digit(str.charAt(i), 2)) {
					str.setCharAt(i, '-');
				}
			}
		}
		
		return str.toString();
	}
	
	public SortedSet<Minterm> getMinterms() {
		return minterms;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj.getClass() != getClass()) return false;
		Implicant other = (Implicant) obj;
		return minterms.equals(other.minterms);
	}
	
	@Override
	public int hashCode() {
		return minterms.hashCode();
	}
	
	@Override
	public String toString() {
		return toTernaryRepresentation();
	}
}
