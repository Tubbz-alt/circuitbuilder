package fwcd.circuitbuilder.model.utils;

import java.util.BitSet;

import fwcd.fructose.ListenerList;
import fwcd.fructose.OptionInt;
import fwcd.fructose.function.Subscription;

public class ValueOverTime implements SignalFunctionSegment {
	private final BitSet values;
	private final ListenerList listeners = new ListenerList();
	private final int capacity;
	private String name;
	private int valueCount;
	
	public ValueOverTime(String name) {
		this(name, 10);
	}
	
	public ValueOverTime(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
		
		values = new BitSet(capacity);
		valueCount = 0;
	}
	
	public void add(boolean value) {
		if (valueCount < capacity) {
			valueCount++;
		}
		shift();
		values.set(0, value);
		listeners.fire();
	}
	
	private void shift() {
		for (int i = valueCount - 1; i > 0; i--) {
			values.set(i, values.get(i - 1));
		}
	}
	
	@Override
	public boolean get(int index) {
		return values.get(index);
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
		return OptionInt.of(capacity);
	}
}
