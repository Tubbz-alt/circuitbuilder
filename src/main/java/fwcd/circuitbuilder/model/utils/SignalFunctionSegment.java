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
	
	void setName(String name);
	
	boolean get(int index);
	
	int getValueCount();
	
	Subscription subscribeToUpdates(Runnable listener);
	
	default OptionInt getCapacity() {
		return OptionInt.empty();
	}
	
	default Stream<Boolean> streamValues() {
		return IntStream.range(0, getValueCount()).mapToObj(this::get);
	}
}
