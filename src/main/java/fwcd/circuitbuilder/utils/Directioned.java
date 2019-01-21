package fwcd.circuitbuilder.utils;

import fwcd.fructose.GridPos;
import fwcd.fructose.Option;

public class Directioned<P extends GridPos> {
	private final P pos;
	private final Option<Direction> direction;
	
	public Directioned(P pos) {
		this(pos, Option.empty());
	}
	
	public Directioned(P pos, Direction direction) {
		this(pos, Option.of(direction));
	}
	
	public Directioned(P pos, Option<Direction> direction) {
		this.pos = pos;
		this.direction = direction;
	}
	
	public Option<Direction> getDirection() { return direction; }
	
	public P getPos() { return pos; }
}
