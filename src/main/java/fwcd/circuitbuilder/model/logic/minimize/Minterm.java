package fwcd.circuitbuilder.model.logic.minimize;

import java.util.List;
import java.util.stream.IntStream;

import fwcd.circuitbuilder.model.logic.expression.Conjunction;
import fwcd.circuitbuilder.model.logic.expression.LogicBoolean;
import fwcd.circuitbuilder.model.logic.expression.LogicExpression;
import fwcd.circuitbuilder.model.logic.expression.LogicVariable;
import fwcd.circuitbuilder.model.logic.expression.Negation;
import fwcd.circuitbuilder.model.utils.BoolUtils;

/**
 * A conjunction of all logic variables that
 * occur in a boolean expression.
 */
public class Minterm implements Comparable<Minterm> {
	private final int binary;
	private final int bitCount;
	
	public Minterm(int binary, int bitCount) {
		this.binary = binary;
		this.bitCount = bitCount;
	}
	
	public int getBinary() { return binary; }
	
	public int getBitCount() { return bitCount; }
	
	public String toBinaryString() { return BoolUtils.toBinaryString(binary, bitCount); }
	
	public LogicExpression toExpression(List<String> variables) {
		return IntStream.range(0, bitCount)
			.mapToObj(i -> (BoolUtils.bitFromLeft(binary, i, bitCount) == 1)
				? new LogicVariable(variables.get(i))
				: new Negation(new LogicVariable(variables.get(i))))
			.reduce(Conjunction::new)
			.orElse(LogicBoolean.FALSE);
	}
	
	@Override
	public int compareTo(Minterm o) {
		return Integer.compare(binary, o.binary);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj.getClass() != getClass()) return false;
		Minterm other = (Minterm) obj;
		return other.binary == binary
			&& other.bitCount == bitCount;
	}
	
	@Override
	public int hashCode() {
		return binary * bitCount * 9;
	}
	
	@Override
	public String toString() {
		return BoolUtils.toBinaryString(binary, bitCount);
	}
}
