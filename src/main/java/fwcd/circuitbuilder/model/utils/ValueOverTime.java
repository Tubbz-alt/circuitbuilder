package fwcd.circuitbuilder.model.utils;

import fwcd.fructose.ListenerList;
import fwcd.fructose.OptionInt;
import fwcd.fructose.function.Subscription;

public class ValueOverTime implements SignalFunctionSegment {
	private final boolean[] values;
	private final ListenerList listeners = new ListenerList();
	private String name;
	private int valueCount;
	
	public ValueOverTime(String name) {
		this(name, 10);
	}
	
	public ValueOverTime(String name, int capacity) {
		this.name = name;
		values = new boolean[capacity];
		valueCount = 0;
	}
	
	public void add(boolean value) {
		if (valueCount < values.length) {
			valueCount++;
		}
		shift();
		values[0] = value;
		listeners.fire();
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
	
	@Override
	public Subscription subscribeToUpdates(Runnable listener) {
		return listeners.subscribe(listener);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public OptionInt getCapacity() {
		return OptionInt.of(values.length);
	}
}
