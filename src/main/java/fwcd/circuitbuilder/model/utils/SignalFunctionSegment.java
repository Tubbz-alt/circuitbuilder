package fwcd.circuitbuilder.model.utils;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A section of a discrete, boolean-valued function.
 */
public interface SignalFunctionSegment {
	boolean[] getValues();
	
	default Stream<Boolean> streamValues() {
		boolean[] values = getValues();
		return IntStream.range(0, values.length).mapToObj(i -> values[i]);
	}
}
