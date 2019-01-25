package fwcd.circuitbuilder.utils;

public class Positioned<T> {
	private final T value;
	private final Directioned<RelativePos> pos;
	
	public Positioned(T value, Directioned<RelativePos> pos) {
		this.value = value;
		this.pos = pos;
	}
	
	public T getValue() { return value; }
	
	public Directioned<RelativePos> getPos() { return pos; }
}
