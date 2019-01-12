package fwcd.circuitbuilder.model.utils;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import fwcd.fructose.OptionInt;
import fwcd.fructose.function.Subscription;

/**
 * A section of a discrete, boolean-valued function.
 */
public interface SignalFunctionSegment {
	String getName();
	
	boolean[] getRawValues();
	
	Subscription subscribeToUpdates(Runnable listener);
	
	default OptionInt getCapacity() {
		return OptionInt.empty();
	}
	
	default int getValueCount() {
		return getRawValues().length;
	}
	
	default Stream<Boolean> streamValues() {
		boolean[] values = getRawValues();
		return IntStream.range(0, getValueCount()).mapToObj(i -> values[i]);
	}
}
