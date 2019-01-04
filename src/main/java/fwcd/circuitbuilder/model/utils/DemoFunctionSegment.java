package fwcd.circuitbuilder.model.utils;

public class DemoFunctionSegment implements SignalFunctionSegment {
	private static final boolean[] VALUES = {true, false, false, true, false, true};
	
	@Override
	public boolean[] getValues() { return VALUES; }
}
