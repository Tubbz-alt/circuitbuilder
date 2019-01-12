package fwcd.circuitbuilder.model.utils;

import fwcd.fructose.function.Subscription;

public class DemoFunctionSegment implements SignalFunctionSegment {
	private static final boolean[] VALUES = {true, false, false, true, false, true};	
	
	@Override
	public boolean[] getRawValues() { return VALUES; }
	
	@Override
	public Subscription subscribeToUpdates(Runnable listener) { return () -> {}; }
	
	@Override
	public String getName() { return "Demo"; }
}
