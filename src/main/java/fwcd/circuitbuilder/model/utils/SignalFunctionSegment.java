package fwcd.circuitbuilder.model.utils;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A section of a discrete, boolean-valued function.
 */
public interface SignalFunctionSegment {
	boolean[] getRawValues();
	
	default int getValueCount() {
		return getRawValues().length;
	}
	
	default Stream<Boolean> streamValues() {
		boolean[] values = getRawValues();
		return IntStream.range(0, getValueCount()).mapToObj(i -> values[i]);
	}
}
