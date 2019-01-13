package fwcd.circuitbuilder.model.utils;

import fwcd.fructose.function.Subscription;

public class DemoFunctionSegment implements SignalFunctionSegment {
	private static final boolean[] VALUES = {true, false, false, true, false, true};	
	private String name = "Demo";
	
	@Override
	public boolean[] getRawValues() { return VALUES; }
	
	@Override
	public Subscription subscribeToUpdates(Runnable listener) { return () -> {}; }
	
	@Override
	public String getName() { return name; }
	
	@Override
	public void setName(String name) { this.name = name; }
}
