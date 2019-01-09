package fwcd.circuitbuilder.model.utils;

public class ValueOverTime implements SignalFunctionSegment {
	private final boolean[] values;
	private int valueCount;
	
	public ValueOverTime() {
		this(10);
	}
	
	public ValueOverTime(int capacity) {
		values = new boolean[capacity];
		valueCount = 0;
	}
	
	public void add(boolean value) {
		if (valueCount < values.length) {
			valueCount++;
		}
		shift();
		values[0] = value;
	}
	
	private void shift() {
		for (int i = valueCount - 1; i > 0; i--) {
			values[i] = values[i - 1];
		}
	}
	
	@Override
	public boolean[] getRawValues() {
		return values;
	}
	
	@Override
	public int getValueCount() {
		return valueCount;
	}
}
